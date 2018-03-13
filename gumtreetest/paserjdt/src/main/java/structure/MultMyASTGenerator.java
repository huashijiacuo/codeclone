package structure;


import org.eclipse.jdt.core.dom.ASTNode;
import util.FileAndMyASTGenerator;

import java.io.File;
import java.util.Currency;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 3/13/18
 * Time: 5:11 PM
 * Description:
 */
public class MultMyASTGenerator implements Runnable  {

    private ConcurrentLinkedQueue<String> queue;

    private AtomicInteger count = new AtomicInteger(0);

    private ConcurrentHashMap<String, ASTNode> map;

    private ConcurrentLinkedQueue<FileAndMyASTGenerator> fileAndMyASTGeneratorsQueue;

    public MultMyASTGenerator(ConcurrentLinkedQueue<String> queue, ConcurrentLinkedQueue<FileAndMyASTGenerator> fileAndMyASTGeneratorsQueue) {
        this.queue = queue;
        this.fileAndMyASTGeneratorsQueue = fileAndMyASTGeneratorsQueue;
    }

    @Override
    public void run() {
        String name;
            while ((name = queue.poll()) != null) {
//                if (count.get() > 10000) {
////                    Thread.currentThread().interrupt();
//                    break;
//                }
                File f = new File(name);
                MyASTGenerator astGenerator = new MyASTGenerator(f);
                FileAndMyASTGenerator fileAndMyASTGenerator = new FileAndMyASTGenerator(name, astGenerator);
                fileAndMyASTGeneratorsQueue.offer(fileAndMyASTGenerator);
                int num = count.incrementAndGet();
                if (num % 100 == 0) {
                    System.out.println(Thread.currentThread().getName() + ": " + num + " file is " + name);
                }
//                map.put(name, astGenerator.getRoot());
            }
    }

}
