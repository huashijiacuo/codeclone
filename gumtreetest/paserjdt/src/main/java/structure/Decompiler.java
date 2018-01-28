package structure;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.java.decompiler.main.Fernflower;
import org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler;
import org.jetbrains.java.decompiler.main.decompiler.PrintStreamLogger;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import sun.rmi.runtime.Log;
import util.ICommonResultCode;
import util.ParameterException;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 1/28/18
 * Time: 8:46 PM
 * Description:
 */
public class Decompiler extends ConsoleDecompiler {
    public static void decompilerClass(String filePath, String desPath) {
        String[] opt = {filePath, desPath};
        implMain(opt);
    }

    public static void decompilerJar(String filePath, String desPath) {
        String[] opt = {filePath, desPath};
        implMain(opt);
        try {
            unzip(desPath+"/junit-4.12.jar", desPath+"/source");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        callShell("unzip " + desPath+"/junit-4.12.jar " + desPath+"/source");

    }

    /**
     * 必传参数，需要反编译的文件，可以使jar，可以使.calss文件； 还需要反编译后解压的位置。
     * 详细说明：https://the.bytecode.club/fernflower.txt
     * github: https://github.com/fesh0r/fernflower
     * @param args
     */
    private static void implMain(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java -jar fernflower.jar [-<option>=<value>]* [<source>]+ <destination>\nExample: java -jar fernflower.jar -dgs=true c:\\my\\source\\ c:\\my.jar d:\\decompiled\\");
        } else {
            Map<String, Object> mapOptions = new HashMap();
            List<File> lstSources = new ArrayList();
            List<File> lstLibraries = new ArrayList();
            boolean isOption = true;

            for(int i = 0; i < args.length - 1; ++i) {
                String arg = args[i];
                if (isOption && arg.length() > 5 && arg.charAt(0) == '-' && arg.charAt(4) == '=') {
                    String value = arg.substring(5);
                    if ("true".equalsIgnoreCase(value)) {
                        value = "1";
                    } else if ("false".equalsIgnoreCase(value)) {
                        value = "0";
                    }

                    mapOptions.put(arg.substring(1, 4), value);
                } else {
                    isOption = false;
                    if (arg.startsWith("-e=")) {
                        addPath(lstLibraries, arg.substring(3));
                    } else {
                        addPath(lstSources, arg);
                    }
                }
            }

            if (lstSources.isEmpty()) {
                System.out.println("error: no sources given");
            } else {
                File destination = new File(args[args.length - 1]);
                if (!destination.isDirectory()) {
                    System.out.println("error: destination '" + destination + "' is not a directory");
                } else {
                    PrintStreamLogger logger = new PrintStreamLogger(System.out);
                    ConsoleDecompiler decompiler = new Decompiler(destination, mapOptions, logger);
                    Iterator var8 = lstSources.iterator();

                    File library;
                    while(var8.hasNext()) {
                        library = (File)var8.next();
                        decompiler.addSpace(library, true);
                    }

                    var8 = lstLibraries.iterator();

                    while(var8.hasNext()) {
                        library = (File)var8.next();
                        decompiler.addSpace(library, false);
                    }

                    decompiler.decompileContext();
                }
            }
        }
    }

    private static void addPath(List<File> list, String path) {
        File file = new File(path);
        if (file.exists()) {
            list.add(file);
        } else {
            System.out.println("warn: missing '" + path + "', ignored");
        }

    }

    protected Decompiler(File destination, Map<String, Object> options, IFernflowerLogger logger) {
        super(destination, options, logger);
    }

    /**
     * 解压缩zip包
     * @param zipFilePath zip文件的全路径
     * @param unzipFilePath 解压后的文件保存的路径
     * @param includeZipFileName 解压后的文件保存的路径是否包含压缩文件的文件名。true-包含；false-不包含
     */
    @SuppressWarnings("unchecked")
    public static void unzip(String zipFilePath, String unzipFilePath, boolean includeZipFileName) throws Exception
    {
        if (StringUtils.isEmpty(zipFilePath) || StringUtils.isEmpty(unzipFilePath))
        {
            throw new ParameterException(ICommonResultCode.PARAMETER_IS_NULL);
        }
        File zipFile = new File(zipFilePath);
        //如果解压后的文件保存路径包含压缩文件的文件名，则追加该文件名到解压路径
        if (includeZipFileName)
        {
            String fileName = zipFile.getName();
            if (StringUtils.isNotEmpty(fileName))
            {
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
            }
            unzipFilePath = unzipFilePath + File.separator + fileName;
        }
        //创建解压缩文件保存的路径
        File unzipFileDir = new File(unzipFilePath);
        if (!unzipFileDir.exists() || !unzipFileDir.isDirectory())
        {
            unzipFileDir.mkdirs();
        }

        //开始解压
        ZipEntry entry = null;
        String entryFilePath = null, entryDirPath = null;
        File entryFile = null, entryDir = null;
        int index = 0, count = 0, bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        ZipFile zip = new ZipFile(zipFile);
        Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>)zip.entries();
        //循环对压缩包里的每一个文件进行解压
        while(entries.hasMoreElements())
        {
            entry = entries.nextElement();
            //构建压缩包中一个文件解压后保存的文件全路径
            entryFilePath = unzipFilePath + File.separator + entry.getName();
            //构建解压后保存的文件夹路径
            index = entryFilePath.lastIndexOf(File.separator);
            if (index != -1)
            {
                entryDirPath = entryFilePath.substring(0, index);
            }
            else
            {
                entryDirPath = "";
            }
            entryDir = new File(entryDirPath);
            //如果文件夹路径不存在，则创建文件夹
            if (!entryDir.exists() || !entryDir.isDirectory())
            {
                entryDir.mkdirs();
            }

            //创建解压文件
            entryFile = new File(entryFilePath);
            if (entryFile.exists())
            {
                //检测文件是否允许删除，如果不允许删除，将会抛出SecurityException
                SecurityManager securityManager = new SecurityManager();
                securityManager.checkDelete(entryFilePath);
                //删除已存在的目标文件
                entryFile.delete();
            }

            //写入文件
            bos = new BufferedOutputStream(new FileOutputStream(entryFile));
            bis = new BufferedInputStream(zip.getInputStream(entry));
            while ((count = bis.read(buffer, 0, bufferSize)) != -1)
            {
                bos.write(buffer, 0, count);
            }
            bos.flush();
            bos.close();
        }
    }

    public static void unzip(String fileZip, String fileDesPath) throws IOException {
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry zipEntry = zis.getNextEntry();
        int i = 0;
        while(zipEntry != null){
            System.out.println("compress! " + i++);
            if (zipEntry.isDirectory()) {
                new File(fileDesPath + File.separator + zipEntry.getName()).mkdirs();
                zipEntry = zis.getNextEntry();
                continue;
            }
            String fileName = zipEntry.getName();
            File newFile = new File(fileDesPath + File.separator + fileName);
            while(!new File(newFile.getParent()).exists()) {
                new File(newFile.getParent()).mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }

    public static void callShell(String shellString) {
        try {
            Process process = Runtime.getRuntime().exec(shellString);
            int exitValue = process.waitFor();
            if (0 != exitValue) {
//                log.error("call shell failed. error code is :" + exitValue);
            }
        } catch (Throwable e) {
//            log.error("call shell failed. " + e);
        }
    }
}
