package structure;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 12/27/17
 * Time: 3:38 PM
 * Description:
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;

public class MyASTGenerator {

    public List<MyMethodNode> methodNodeList = new ArrayList<MyMethodNode>();

    private ASTNode root;

    public ASTNode getRoot() {
        return root;
    }

    public void setRoot(ASTNode root) {
        this.root = root;
    }


    public MyASTGenerator(File f) {
        // TODO Auto-generated constructor stub
        ParseFile(f);
    }

    public List<MyMethodNode> getMethodNodeList() {
        return methodNodeList;
    }

    // read file content into a string
    public String readFileToString(String filePath) throws IOException {
        StringBuilder fileData = new StringBuilder(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        char[] buf = new char[10];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            // System.out.println(numRead);
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }

        reader.close();

        return fileData.toString();
    }

    // use ASTParse to parse string
    public void parse(String str) {
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        Map pOptions = JavaCore.getOptions();
        pOptions.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_8);
        pOptions.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_8);
        pOptions.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
        pOptions.put(JavaCore.COMPILER_DOC_COMMENT_SUPPORT, JavaCore.ENABLED);
        parser.setCompilerOptions(pOptions);   // ASTParse转换也需要一些参数，慢慢研究 shun
        parser.setSource(str.toCharArray());


        parser.setResolveBindings(true);
        parser.setEnvironment( // apply classpath
                new String[] { "//home//shi//" }, //
                null, null, true);
        parser.setUnitName("any_name");

        final CompilationUnit cu = (CompilationUnit)parser.createAST(null);

        root = cu;


//        System.out.println(cu);
        MethodNodeVisitor methodNodeVisitor = new MethodNodeVisitor();
        cu.accept(methodNodeVisitor);


/*        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(str.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        final CompilationUnit cu = (CompilationUnit) parser.createAST(null);*/

        // find the MethodDeclaration node, MethodNodeVisitor
/*        MethodNodeVisitor methodNodeVisitor = new MethodNodeVisitor();
        cu.accept(methodNodeVisitor);*/
        // traverse all child nodes, NodeVisitor
        for (MethodDeclaration m : methodNodeVisitor.getMethodDecs()) {
            MyMethodNode mNode = new MyMethodNode();
            mNode.setMethodNode(m);
            NodeVisitor nv = new NodeVisitor();
            m.accept(nv);
            List<ASTNode> astnodes = nv.getASTNodes();
            for (ASTNode node : astnodes) {
                MyASTNode myNode = new MyASTNode();
                myNode.astNode = node;
                myNode.lineNum = cu.getLineNumber(node.getStartPosition());
                // add to nodeList
                List<MyASTNode> nodeList = mNode.getNodeList();
                nodeList.add(myNode);
                // add to mapping
                // in case, I need to exclude root node
                if (node.equals(m)) {
                    continue;
                }
                int pHashcode = node.getParent().hashCode();
                int hashcode = node.hashCode();
                int[] link = { pHashcode, hashcode };
                mNode.getMapping().add(link);
            }
            methodNodeList.add(mNode);
        }
        // System.out.print(ast);
    }

    // loop directory to get file list
    public void ParseFilesInDir() throws IOException {
        File dirs = new File(".");
        String dirPath = dirs.getCanonicalPath() + File.separator + "src" + File.separator;

        File root = new File(dirPath);
        // System.out.println(rootDir.listFiles());
        File[] files = root.listFiles();
        String filePath = null;

        for (File f : files) {
            filePath = f.getAbsolutePath();
            if (f.isFile()) {
                parse(readFileToString(filePath));
            }
        }
    }

    // loop directory to get file list
    public void ParseFile(File f) {
        String filePath = f.getAbsolutePath();
        if (f.isFile()) {
            try {
                parse(readFileToString(filePath));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            System.out.println("Not a File!");
        }
    }
}