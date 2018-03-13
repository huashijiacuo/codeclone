import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import structure.MyASTGenerator;
import structure.astnode.MyMethodNode;
import util.FileAndMyASTGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 3/13/18
 * Time: 10:02 PM
 * Description:
 */
public class WriteVectorToFile implements Runnable {
    public static AtomicInteger totleFile = new AtomicInteger(0);

    private AtomicInteger count = new AtomicInteger(0);
    public static BufferedWriter bufferedWriter = null;
/*    static {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(new File("")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
//    ConcurrentHashMap<String, ASTNode> map;
    ConcurrentLinkedQueue<FileAndMyASTGenerator> queue = new ConcurrentLinkedQueue<FileAndMyASTGenerator>();

    public WriteVectorToFile(ConcurrentLinkedQueue<FileAndMyASTGenerator> queue, String filePath ) {
//        this.map = map;
        this.queue = queue;
        try {
            this.bufferedWriter = new BufferedWriter(new FileWriter(new File(filePath + "/vector.txt")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        FileAndMyASTGenerator fileAndMyASTGenerator;
        while (true) {
            if(totleFile.get() == 0) {
                break ;
            }
            while ((fileAndMyASTGenerator = queue.poll()) != null) {

                Map<MyMethodNode,Map<String,Integer>> methodMap = new AstVector().astToVector(fileAndMyASTGenerator.myASTGenerator);
                String content = "##fileName##  " + fileAndMyASTGenerator.fileName + "\r\n";

                for (Map.Entry<MyMethodNode, Map<String, Integer>> entry : methodMap.entrySet()) {
                    MyMethodNode myMethodNode = entry.getKey();
                    Map<String, Integer> map = entry.getValue();
//                    System.out.println("The method is: " + myMethodNode.getMethodNode().getName());
                    content = "@@functionName@@  " + content + myMethodNode.getMethodNode().getName() + "  start line: " + ((CompilationUnit)(myMethodNode.getMethodNode().getRoot())).getLineNumber(myMethodNode.getMethodNode().getStartPosition()) +  "\r\n";
                    for (Map.Entry<String, Integer> entry1 : map.entrySet()) {
                        String nodeName = entry1.getKey();
                        int freq = entry1.getValue();
//                        System.out.print(nodeName + ":" + freq + ";");
                        content = content + nodeName + ":" + freq + ";  ";
                    }
                    content = content + "\r\n";
                }

                synchronized (bufferedWriter) {
                    try {
                        bufferedWriter.write(content);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                totleFile.decrementAndGet();

                int num = count.get();
                if (num % 100 == 0) {
                    System.out.println(num + " " +Thread.currentThread().getName() + ": " + " file " + fileAndMyASTGenerator.fileName + " has been parser to vector!");
                }
                count.incrementAndGet();
            }
        }
    }
}
