import org.eclipse.jdt.core.dom.ASTNode;
import structure.MyASTGenerator;
import structure.astnode.MyASTNode;
import structure.astnode.MyMethodNode;
import util.FileUtil;
import variableflow.GeneratorVariableDeclaration;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 3/13/18
 * Time: 11:21 AM
 * Description:
 */
public class AstVector {

    private  Map<String, Integer> nodeMap = new HashMap<String, Integer>();

    public Map<String, Integer> getNodeMap() {
        return nodeMap;
    }

    private void addNodeToMap(ASTNode node) {
        String nodeName = node.getClass().getSimpleName();
        if (nodeMap.containsKey(nodeName)) {
            int freq = nodeMap.get(nodeName);
            nodeMap.replace(nodeName,freq, freq+1);
        } else {
            nodeMap.put(nodeName, 1);
        }
    }

    public void generatorVector(String dir) {
        long startTime = System.currentTimeMillis();
        System.out.println("begin");
//        String dir = "/home/shi/Desktop/codeclone/dataset/selected";
        String suffix = ".java";
        String[] allFilesName = FileUtil.getAllFiles(dir, suffix, false);

        long readFileTime = System.currentTimeMillis();
        System.out.println("读取文件路径程序运行时间： "+(readFileTime-startTime)+"ms");

        int functionNum = 0;
        for (int i = 0; i < 10000; i++) {
            String fileName = allFilesName[i];
            if (i % 500 == 0) {
                System.out.println("The id is :" + i + ", file is: " + allFilesName[i]);
            }
//            JavaASTParser parser = new JavaASTParser(fileName, null, false);
//            List<CompilationUnit> list = parser.getCompilationUnits();
//            ASTRequestor requestor = parser.getAstRequestor();
            File f = FileUtil.getFile(fileName, false);
            MyASTGenerator astGenerator;
            try {
                astGenerator = new MyASTGenerator(f);
            } catch (Exception e) {
                continue;
            }


            ASTNode root = astGenerator.getRoot();

            GeneratorVariableDeclaration generatorVarDecl = new GeneratorVariableDeclaration();
            generatorVarDecl.findVariableDeclaration(root);

            List<MyMethodNode> methodNodeList = astGenerator.getMethodNodeList();

            Map<MyMethodNode, Map<String, Integer>> methodMap = new HashMap<>();
            for (MyMethodNode myMethodNode : methodNodeList) {
                List<MyASTNode> list = myMethodNode.getNodeList();
                AstVector astVector = new AstVector();
                for (MyASTNode myASTNode : list) {
                    ASTNode node = myASTNode.astNode;
                    astVector.addNodeToMap(node);
                }
                Map<String , Integer> map = astVector.getNodeMap();
                methodMap.put(myMethodNode, map);
                functionNum++;
            }

            for (Map.Entry<MyMethodNode, Map<String, Integer>> entry : methodMap.entrySet()) {
                MyMethodNode myMethodNode = entry.getKey();
                Map<String, Integer> map = entry.getValue();
                if (i % 500 == 0) {
                    System.out.println("The method is: " + myMethodNode.getMethodNode().getName());
                }
                for (Map.Entry<String, Integer> entry1 : map.entrySet()) {
                    String nodeName = entry1.getKey();
                    int freq = entry1.getValue();
                    if (i % 500 == 0) {
                        System.out.print(nodeName + ":" + freq + ";");
                    }
                }
                if (i % 500 == 0) {
                    System.out.println();
                }
            }

            if (i % 500 == 0) {
                long parserTime1 = System.currentTimeMillis();
                System.out.println("-----------------------\nfunctionNum numbers: " + functionNum + "\n解析时间： " + +(parserTime1 - readFileTime) + "ms");
                readFileTime = parserTime1;
            }
        }
        System.out.println("文件数量： " + allFilesName.length);
        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
    }

    public Map<MyMethodNode, Map<String , Integer>> astToVector(MyASTGenerator astGenerator) {
        List<MyMethodNode> methodNodeList = astGenerator.getMethodNodeList();

        Map<MyMethodNode, Map<String, Integer>> methodMap = new HashMap<>();
        for (MyMethodNode myMethodNode : methodNodeList) {
            List<MyASTNode> list = myMethodNode.getNodeList();
            AstVector astVector = new AstVector();
            for (MyASTNode myASTNode : list) {
                ASTNode node = myASTNode.astNode;
                astVector.addNodeToMap(node);
            }
            Map<String , Integer> map = astVector.getNodeMap();
            methodMap.put(myMethodNode, map);
        }

/*        for (Map.Entry<MyMethodNode, Map<String, Integer>> entry : methodMap.entrySet()) {
            MyMethodNode myMethodNode = entry.getKey();
            Map<String, Integer> map = entry.getValue();
            System.out.println("The method is: " + myMethodNode.getMethodNode().getName());
            for (Map.Entry<String, Integer> entry1 : map.entrySet()) {
                String nodeName = entry1.getKey();
                int freq = entry1.getValue();
                    System.out.print(nodeName + ":" + freq + ";");
            }
            System.out.println();
        }*/
        return methodMap;
    }

}
