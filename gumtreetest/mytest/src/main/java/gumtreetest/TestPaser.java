package gumtreetest; /**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 12/27/17
 * Time: 9:14 PM
 * Description:
 */
import com.github.gumtreediff.actions.ActionGenerator;
import com.github.gumtreediff.actions.model.Action;
import com.github.gumtreediff.client.Run;
import com.github.gumtreediff.gen.jdt.JdtTreeGenerator;
import com.github.gumtreediff.gen.jdt.JdtVisitor;
import com.github.gumtreediff.matchers.Matcher;
import com.github.gumtreediff.matchers.Matchers;
import com.github.gumtreediff.tree.ITree;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import structure.MyMethodNode;
import structure.ParserJDT;
import type2.TreeCompare;
import util.FileUtil;
import util.GeneratorClass;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestPaser {
    public static void main(String[] args) throws IOException {
        Run.initGenerators();  // 初始化Generators  注册继承自com.github.gumtreediff.gen.TreeGenerator的类来解析源码
        String fileName1 = "Addclass.java";
        String fileName2 = "Multclass.java";
        File file1 = FileUtil.getFile(fileName1);
        File file2 = FileUtil.getFile(fileName2);
//        ASTParser parser = ASTParser.newParser(AST.JLS8);
        ParserJDT parserJDT = new ParserJDT();
        List<MyMethodNode> listFuncRoot1 = parserJDT.parserFileToFunc(file1);
        List<MyMethodNode> listFuncRoot2 = parserJDT.parserFileToFunc(file2);
        for (MyMethodNode myMethodNode1 : listFuncRoot1) {
            MethodDeclaration node1 = myMethodNode1.getMethodNode();
            ASTNode newNode1 = GeneratorClass.generator(node1);
            String str1 = newNode1.toString();
            JdtVisitor jdtVisitor1 = new JdtVisitor();
//            newNode1.accept(jdtVisitor1);
//            TreeContext treeContext1 = jdtVisitor1.getTreeContext();
            for (MyMethodNode myMethodNode2 : listFuncRoot2) {
                MethodDeclaration node2 = myMethodNode2.getMethodNode();
                ASTNode newNode2 = GeneratorClass.generator(node2);
                String str2  = newNode2.toString();
//                JdtVisitor jdtVisitor2 = new JdtVisitor();
//                newNode2.accept(jdtVisitor2);
//                TreeContext treeContext2 = jdtVisitor2.getTreeContext();
//                ITree src = treeContext1.getRoot();
//                ITree dst = treeContext2.getRoot();
                ITree src = new JdtTreeGenerator().generateFromString(str1).getRoot();
                ITree dst = new JdtTreeGenerator().generateFromString(str2).getRoot();
                Matcher m = Matchers.getInstance().getMatcher(src, dst); // retrieve the default matcher
                m.match();
                ActionGenerator g = new ActionGenerator(src, dst, m.getMappings());
                g.generate();
                List<Action> actions = g.getActions(); // return the actions
                System.out.println("func1： " + newNode1.toString() + "  func2: " + newNode2.toString());
                for (Action action : actions) {
                    System.out.println(action.toString());
                }
                boolean isSame = TreeCompare.compare(node1, node2);
                System.out.println(isSame);
                break;
            }

        }
    }
}
