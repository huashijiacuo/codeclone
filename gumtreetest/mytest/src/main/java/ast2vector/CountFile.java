package ast2vector;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import structure.ASTRequestor;
import structure.JavaASTParser;
import structure.MyASTGenerator;
import structure.astnode.MyASTNode;
import structure.astnode.MyMethodNode;
import util.FileUtil;
import variableflow.GeneratorVariableDeclaration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 3/13/18
 * Time: 3:02 PM
 * Description:
 */
public class CountFile {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        System.out.println("begin");
        String dir = "/home/shi/Desktop/codeclone/dataset/selected";
        String suffix = ".java";
        String[] allFilesName = FileUtil.getAllFiles(dir, suffix, false);

        long readFileTime = System.currentTimeMillis();
        System.out.println("读取文件路径程序运行时间： "+(readFileTime-startTime)+"ms");

        for (int i = 0; i < 1000; i++) {
            String fileName = allFilesName[i];

            System.out.println("The id is :" + i + ", file is: " + allFilesName[i]);
            long parserTime1 = System.currentTimeMillis();
            JavaASTParser parser = new JavaASTParser(fileName, null, false);
            List<CompilationUnit> list = parser.getCompilationUnits();
            ASTRequestor requestor = parser.getAstRequestor();


            long parserTime2 = System.currentTimeMillis();
            System.out.println("-----------------------\nCompilationUnit numbers: " + list.size() + "\n解析时间： " + +(parserTime2-parserTime1)+"ms");
        }
        System.out.println("文件数量： " + allFilesName.length);
        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
    }
}
