package structure;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.jdt.core.dom.ASTNode;
import variableflow.GeneratorVariableDeclaration;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 12/27/17
 * Time: 3:44 PM
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        String fileName = "/Addclass.java";
        String outputDir = "";
        URL url = new Main().getClass().getResource(fileName);
        String filePath = url.getFile();
        File f = new File(filePath);
        MyASTGenerator astGenerator = new MyASTGenerator(f);

        ASTNode root = astGenerator.getRoot();

        GeneratorVariableDeclaration generatorVarDecl = new GeneratorVariableDeclaration();
        generatorVarDecl.findVariableDeclaration(root);


        List<MyMethodNode> methodNodeList = astGenerator.getMethodNodeList();
        for (MyMethodNode myMethodNode : methodNodeList) {
            System.out.println(myMethodNode.toString());
            String parsrString = ASTToDot.ASTtoDotParser(myMethodNode);
            System.out.println(parsrString);
            break;
        }
    }
}
