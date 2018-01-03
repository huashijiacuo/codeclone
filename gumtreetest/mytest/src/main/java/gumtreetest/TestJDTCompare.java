package gumtreetest;

import structure.ASTToDot;
import structure.Main;
import structure.MyASTGenerator;
import structure.MyMethodNode;
import type2.TreeCompare;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 1/2/18
 * Time: 5:16 PM
 * Description:
 */
public class TestJDTCompare {
    public static void main(String[] args) {
        String fileName = "/Addclass.java";
        String outputDir = "";
        URL url = new Main().getClass().getResource(fileName);
        String filePath = url.getFile();
        File f = new File(filePath);
        MyASTGenerator astGenerator = new MyASTGenerator(f);
        List<MyMethodNode> methodNodeList = astGenerator.getMethodNodeList();
        for (MyMethodNode myMethodNode : methodNodeList) {
            System.out.println(myMethodNode.toString());
            String parsrString = ASTToDot.ASTtoDotParser(myMethodNode);
            System.out.println(parsrString);
            break;
//            TreeCompare.compare(myMethodNode, )
        }
    }
}
