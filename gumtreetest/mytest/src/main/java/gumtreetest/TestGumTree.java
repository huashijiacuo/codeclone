package gumtreetest;

import com.github.gumtreediff.actions.ActionGenerator;
import com.github.gumtreediff.actions.model.Action;
import com.github.gumtreediff.client.Run;
import com.github.gumtreediff.gen.Generators;
import com.github.gumtreediff.gen.jdt.JdtTreeGenerator;
import com.github.gumtreediff.matchers.Matcher;
import com.github.gumtreediff.matchers.Matchers;
import com.github.gumtreediff.tree.ITree;
import util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 12/11/17
 * Time: 9:37 AM
 * Description:
 */
public class TestGumTree {
    public static void main(String[] args) throws IOException {
        Run.initGenerators();  // 初始化Generators  注册继承自com.github.gumtreediff.gen.TreeGenerator的类来解析源码
        /*String file1 = "package shi.com;\n" +
                "\n" +
                "\n" +
                "import shi.com.common.MyLog;\n" +
                "\n" +
                "public class Addcla
                ss {\n" +
                "\n" +
                "    public int addInt(int a, int b) {\n" +
                "        System.out.println(\"this is add function between integer\");\n" +
                "        System.out.println(\"a = \" + a);\n" +
                "        System.out.println(\"b = \" + b);\n" +
                "        int c = a + b;\n" +
                "        System.out.println(\"The result is c!\");\n" +
                "        System.out.println(\"c = \" + c);\n" +
                "        String str1 = \"a = \" + a;\n" +
                "        String str2 = \"b = \" + b;\n" +
                "        MyLog myLog = new MyLog();\n" +
                "        myLog.setStr1(str1);\n" +
                "        myLog.setStr2(str2);\n" +
                "        myLog.log();\n" +
                "        return c;\n" +
                "    }\n" +
                "\n" +
                "    public double addDouble(double a, double b) {\n" +
                "        System.out.println(\"this is add function between integer\");\n" +
                "        System.out.println(\"a = \" + a);\n" +
                "        System.out.println(\"b = \" + b);\n" +
                "        double c = a + b;\n" +
                "        System.out.println(\"The result is c!\");\n" +
                "        System.out.println(\"c = \" + c);\n" +
                "        String str1 = \"a = \" + a;\n" +
                "        String str2 = \"b = \" + b;\n" +
                "        MyLog myLog = new MyLog();\n" +
                "        myLog.setStr1(str1);\n" +
                "        myLog.setStr2(str2);\n" +
                "        myLog.log();\n" +
                "        return  c;\n" +
                "    }\n" +
                "\n" +
                "    public long addLong(long a, long b) {\n" +
                "        System.out.println(\"this is add function between integer\");\n" +
                "        System.out.println(\"a = \" + a);\n" +
                "        System.out.println(\"b = \" + b);\n" +
                "        long c = a + b;\n" +
                "        System.out.println(\"The result is c!\");\n" +
                "        System.out.println(\"c = \" + c);\n" +
                "        String str1 = \"a = \" + a;\n" +
                "        String str2 = \"b = \" + b;\n" +
                "        MyLog myLog = new MyLog();\n" +
                "        myLog.setStr1(str1);\n" +
                "        myLog.setStr2(str2);\n" +
                "        myLog.log();\n" +
                "        return  c;\n" +
                "    }\n" +
                "}";
        String file2 = "package shi.com;\n" +
                "\n" +
                "import shi.com.common.MyLog;\n" +
                "\n" +
                "public class Multclass {\n" +
                "    public int multInt(int a, int b) {\n" +
                "        System.out.println(\"this is add function between integer\");\n" +
                "        System.out.println(\"a = \" + a);\n" +
                "        System.out.println(\"b = \" + b);\n" +
                "        int c = a * b;\n" +
                "        System.out.println(\"The result is c!\");\n" +
                "        System.out.println(\"c = \" + c);\n" +
                "        String str1 = \"a = \" + a;\n" +
                "        String str2 = \"b = \" + b;\n" +
                "        MyLog myLog = new MyLog();\n" +
                "        myLog.setStr1(str1);\n" +
                "        myLog.setStr2(str2);\n" +
                "        myLog.log();\n" +
                "        return c;\n" +
                "    }\n" +
                "\n" +
                "    public double multDouble(double a, double b) {\n" +
                "        System.out.println(\"this is add function between integer\");\n" +
                "        System.out.println(\"a = \" + a);\n" +
                "        System.out.println(\"b = \" + b);\n" +
                "        double c = a * b;\n" +
                "        System.out.println(\"The result is c!\");\n" +
                "        System.out.println(\"c = \" + c);\n" +
                "        String str1 = \"a = \" + a;\n" +
                "        String str2 = \"b = \" + b;\n" +
                "        MyLog myLog = new MyLog();\n" +
                "        myLog.setStr1(str1);\n" +
                "        myLog.setStr2(str2);\n" +
                "        myLog.log();\n" +
                "        return  c;\n" +
                "    }\n" +
                "\n" +
                "    public long multLong(long a, long b) {\n" +
                "        System.out.println(\"this is add function between integer\");\n" +
                "        System.out.println(\"a = \" + a);\n" +
                "        System.out.println(\"b = \" + b);\n" +
                "        long c = a * b;\n" +
                "        System.out.println(\"The result is c!\");\n" +
                "        System.out.println(\"c = \" + c);\n" +
                "        String str1 = \"a = \" + a;\n" +
                "        String str2 = \"b = \" + b;\n" +
                "        MyLog myLog = new MyLog();\n" +
                "        myLog.setStr1(str1);\n" +
                "        myLog.setStr2(str2);\n" +
                "        myLog.log();\n" +
                "        return  c;\n" +
                "    }\n" +
                "}\n";*/
        String fileName1 = "Addclass.java";
        String fileName2 = "Multclass.java";
        File file1 = FileUtil.getFile(fileName1);
        File file2 = FileUtil.getFile(fileName2);
        ITree src = new JdtTreeGenerator().generateFromFile(file1).getRoot();
        ITree dst = new JdtTreeGenerator().generateFromFile(file2).getRoot();
//        ITree src = Generators.getInstance().getTree(file1).getRoot();
//        ITree dst = Generators.getInstance().getTree(file2).getRoot();
/*        ITree src = new JdtTreeGenerator().generateFromString(file1).getRoot();
        ITree dst = new JdtTreeGenerator().generateFromString(file2).getRoot();*/
        Matcher m = Matchers.getInstance().getMatcher(src, dst); // retrieve the default matcher
        m.match();
        ActionGenerator g = new ActionGenerator(src, dst, m.getMappings());
        g.generate();
        List<Action> actions = g.getActions(); // return the actions
        for (Action action : actions) {
            System.out.println(action.toString());
        }
    }
}
