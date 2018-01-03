package gumtreetest;

import com.github.gumtreediff.gen.jdt.JdtTreeGenerator;
import com.github.gumtreediff.tree.ITree;
import util.FileUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 12/26/17
 * Time: 9:50 PM
 * Description:
 */
public class TestJdt {

    public static void main(String[] args) {
        TestJdt testJdt = new TestJdt();
        String fileName = "Addclass.java";

        File file = FileUtil.getFile(fileName);
//        testJdt.getJDT(fileName);
        try {
            ITree iTree = new JdtTreeGenerator().generateFromFile(file).getRoot();
            List<ITree> list = new ArrayList<>();
            List<ITree> listCopy = new ArrayList<ITree>();
            list.add(iTree);
            for (ITree tree : list) {
                System.out.println(tree.toShortString());
                listCopy.addAll(tree.getChildren());
            }
            list = listCopy;
            listCopy = new ArrayList<ITree>();
            for (ITree tree : list) {
                System.out.println(tree.toShortString());
                listCopy.addAll(tree.getChildren());
            }
            /*while (!list.isEmpty()) {

                for (ITree tree : list) {
                    System.out.println(tree.toShortString());
                    listCopy.addAll(tree.getChildren());
                }
                list = listCopy;
                listCopy = new ArrayList<ITree>();
            }
*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getJDT(String fileName) {
        String path = this.getClass().getResource(fileName).getFile();
        File file = new File(path);
        System.out.println(this.getClass().getResource(File.separator));
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            int line = 0;
            String str = null;
            while ((str = reader.readLine()) != null) {
                System.out.println("The line number is :" + line + "; The context is :" + str);
                line++;
            }
            System.out.println(line);
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
