package util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 12/27/17
 * Time: 5:29 PM
 * Description:
 */
public class FileUtil {

    public static File getFile(String fileName) {
        return getFile(fileName, true);
    }

    public static File getFile(String fileName, boolean isRelative) {
        if (isRelative) {
            fileName = "/" + fileName;
            URL url = FileUtil.class.getResource(fileName);
            String filePath = url.getFile();
            File f = new File(filePath);
            return f;
        } else {
            File f = new File(fileName);
            return f;
        }
    }


    public static String[] getAllLibFiles(List<String> projectPathList, String suffix) {
        List<String> total = new LinkedList<>();
        if (projectPathList == null) {
            String [] rs = new String[0];
            return rs;
        }
        for (String projectPath : projectPathList) {
            if (projectPath == null) {
                return null;
            }
            File file = new File(projectPath);
            if (!file.exists()) {
                return null;
            }
            ArrayList<String> filesList = new ArrayList<String>();
            readFiles(file, suffix, filesList);
            total.addAll(filesList);
        }
        String[] array = total.toArray(new String[total.size()]);
        return array;
    }

    public static String[] getAllFiles(String projectPath, String suffix, boolean isRelative) {
        if (projectPath == null) {
            return null;
        }
//        File file = new File(projectPath);
        File file = getFile(projectPath, isRelative);
        if (!file.exists()) {
            return null;
        }
        ArrayList<String> filesList = new ArrayList<String>();
        readFiles(file, suffix, filesList);
        int size = filesList.size();
        String[] array = filesList.toArray(new String[size]);
        return array;
    }

    private static void readFiles(File file, String suffix, ArrayList<String> filesList) {
        if (file == null) {
            return;
        }
        if (file.isDirectory()) {
            File f[] = file.listFiles();
            if (f != null) {
                for (int i = 0; i < f.length; i++) {
                    readFiles(f[i], suffix, filesList);
                }
            }
        } else if (file.getName().endsWith(suffix)) {
            filesList.add(file.toString());
        }

    }
}
