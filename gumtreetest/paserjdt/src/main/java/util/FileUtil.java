package util;

import java.io.File;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 12/27/17
 * Time: 5:29 PM
 * Description:
 */
public class FileUtil {

    public static File getFile(String fileName) {
        fileName = "/" + fileName;
        URL url = FileUtil.class.getResource(fileName);
        String filePath = url.getFile();
        File f = new File(filePath);
        return f;
    }
}
