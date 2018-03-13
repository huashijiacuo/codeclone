import util.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 3/13/18
 * Time: 8:42 PM
 * Description:
 */
public class GetFilePath {

    public static void writePath(String dir) {
        String suffix = ".java";
        String[] allFilesPath = FileUtil.getAllFiles(dir, suffix, false);
        if (dir.endsWith("/")) {
            dir = dir.substring(0, dir.length() - 2);
        }
        String path = dir.substring(0, dir.lastIndexOf("/"));
        path = path + "/javaSourceCodePath.txt";
        File desFile = new File(path);
        if (desFile.exists()) {
            desFile.delete();
        }
        try {
            desFile.createNewFile();  // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(desFile));
            for (String sourceFilePath : allFilesPath) {
                out.write(sourceFilePath + "\r\n"); // \r\n即为换行
            }
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
