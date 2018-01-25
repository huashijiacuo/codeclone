package detect;

import com.github.gumtreediff.client.Run;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import structure.MyMethodNode;
import structure.ParserJDT;
import type3.Similarity;
import util.FileUtil;
import util.GeneratorClass;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 1/24/18
 * Time: 3:03 PM
 * Description:
 */
public class SimilarityTest {
    public static void main(String[] args) throws CoreException, IOException {
        Run.initGenerators();  // 初始化Generators  注册继承自com.github.gumtreediff.gen.TreeGenerator的类来解析源码
        Similarity similarityClass = new Similarity();
        String fileName1 = "Addclass.java";
        String fileName2 = "AddclassCopy.java";
        File file1 = FileUtil.getFile(fileName1);
        File file2 = FileUtil.getFile(fileName2);
//        ASTParser parser = ASTParser.newParser(AST.JLS8);
        ParserJDT parserJDT = new ParserJDT();
        List<MyMethodNode> listFuncRoot1 = parserJDT.parserFileToFunc(file1);
        ASTNode root1 = parserJDT.getRoot();
        List<MyMethodNode> listFuncRoot2 = parserJDT.parserFileToFunc(file2);
        ASTNode root2 = parserJDT.getRoot();
        for (MyMethodNode myMethodNode1 : listFuncRoot1) {
            MethodDeclaration node1 = myMethodNode1.getMethodNode();

            for (MyMethodNode myMethodNode2 : listFuncRoot2) {
                MethodDeclaration node2 = myMethodNode2.getMethodNode();

                double similarity = similarityClass.getSimilarity(node1, node2);
                System.out.println(similarity);
            }
        }

    }
}
