package structure;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 1/11/18
 * Time: 8:43 PM
 * Description:
 */
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.Document;
import util.FileUtil;


/**
 *
 * @author Chen Zhe
 *
 */
public class JavaASTParser {
    // 以后再去官网查找如何使用库的classPath
    private List<String> libraryPath;

    private ASTRequestor astRequestor;

    private String projectPath;

    public List<CompilationUnit> getCompilationUnits() {
        return this.astRequestor.getCompilationUnits();
    }

    public ASTRequestor getAstRequestor() {
        return this.astRequestor;
    }

    public void setAstRequestor(ASTRequestor astRequestor) {
        this.astRequestor = astRequestor;
    }

    public JavaASTParser(String projectPath, List<String> libraryPath) {
//        Document x = new Document("asdjasf");
        this.projectPath = projectPath;
        this.libraryPath = libraryPath;
        setAstRequestor(new ASTRequestor());
        try {
            this.generateASTs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateASTs() {
//        File filex = new File(projectPath);
        File filex = FileUtil.getFile(projectPath);
        filex.exists();
        String[] sourceFilePaths = this.getAllFiles(projectPath, ".java");
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        String[] classpathEntries = this.getAllLibFiles(libraryPath, ".jar");
        if (filex.isDirectory()) {
            String[] sourcePathEntries = { projectPath };
            parser.setEnvironment(classpathEntries, sourcePathEntries, null, true);
        } else {
            String[] sourcePathEntries = {  };
            parser.setEnvironment(classpathEntries, sourcePathEntries, null, true);
        }
        parser.setResolveBindings(true);
        parser.setBindingsRecovery(true);
        parser.setStatementsRecovery(true);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        Map<String, String> complierOptions = JavaCore.getOptions();
        JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, complierOptions);
        parser.setCompilerOptions(complierOptions);
        String[] encodings = null;
        String[] bindingKeys = new String[] {};
        parser.createASTs(sourceFilePaths, encodings, bindingKeys, astRequestor, null);
    }


    private String[] getAllLibFiles(List<String> projectPathList, String suffix) {
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

    private String[] getAllFiles(String projectPath, String suffix) {
        if (projectPath == null) {
            return null;
        }
//        File file = new File(projectPath);
        File file = FileUtil.getFile(projectPath);
        if (!file.exists()) {
            return null;
        }
        ArrayList<String> filesList = new ArrayList<String>();
        readFiles(file, suffix, filesList);
        int size = filesList.size();
        String[] array = filesList.toArray(new String[size]);
        return array;
    }

    private void readFiles(File file, String suffix, ArrayList<String> filesList) {
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