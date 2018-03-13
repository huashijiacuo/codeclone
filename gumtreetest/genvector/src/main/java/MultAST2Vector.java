import org.eclipse.jdt.core.dom.ASTNode;
import structure.MultMyASTGenerator;
import structure.MyASTGenerator;
import util.FileAndMyASTGenerator;
import util.FileUtil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class MultAST2Vector {

    public void multAst2Vector(String dir, String outPutPath) {
        long start = System.currentTimeMillis();
        System.out.println("主线程开始执行");
//        String dir = "/home/shi/Desktop/codeclone/dataset/selected";
        String suffix = ".java";
        String[] allFilesName = FileUtil.getAllFiles(dir, suffix, false);
        System.out.println("提取文件路径花费时间：" + (System.currentTimeMillis() - start) + "ms") ;
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>(Arrays.asList(allFilesName));
//        ConcurrentHashMap<String, ASTNode> map = new ConcurrentHashMap<String, ASTNode>();
        ConcurrentLinkedQueue<FileAndMyASTGenerator> fileAndMyASTGeneratorsQueue = new ConcurrentLinkedQueue<FileAndMyASTGenerator>();

//        ConcurrentLinkedQueue<MyASTGenerator> generatorsQueue = new ConcurrentLinkedQueue<MyASTGenerator>();

        WriteVectorToFile writeVectorToFile = new WriteVectorToFile(fileAndMyASTGeneratorsQueue, outPutPath);
        WriteVectorToFile.totleFile.set(allFilesName.length); //allFilesName.length

        MultMyASTGenerator multMyASTGenerator = new MultMyASTGenerator(queue, fileAndMyASTGeneratorsQueue);

        Thread thread1 = new Thread(multMyASTGenerator, "Thread1");
        Thread thread2 = new Thread(multMyASTGenerator, "Thread2");
        Thread thread3 = new Thread(multMyASTGenerator, "Thread3");
        Thread thread4 = new Thread(multMyASTGenerator, "Thread4");
        Thread thread5 = new Thread(multMyASTGenerator, "Thread5");
        Thread thread6 = new Thread(multMyASTGenerator, "Thread6");
        Thread writeThread = new Thread(writeVectorToFile, "writeThread");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        writeThread.start();
        try
        {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            thread5.join();
            thread6.join();
            writeThread.join();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = WriteVectorToFile.bufferedWriter;
        try {
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("The number of file is: " + map.size());
        long end = System.currentTimeMillis();
        System.out.println("程序执行完毕！花费时间为： " + (end - start) + "ms");

    }

}