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
        this(projectPath, libraryPath, true);
    }
    public JavaASTParser(String projectPath, List<String> libraryPath, boolean isRelative) {
//        Document x = new Document("asdjasf");
        this.projectPath = projectPath;
        this.libraryPath = libraryPath;
        setAstRequestor(new ASTRequestor());
        try {
            this.generateASTs(isRelative);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateASTs(boolean isRelative) {
//        File filex = new File(projectPath);
        File filex = FileUtil.getFile(projectPath, isRelative);
        filex.exists();
        String[] sourceFilePaths = FileUtil.getAllFiles(projectPath, ".java", isRelative);
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        String[] classpathEntries = FileUtil.getAllLibFiles(libraryPath, ".jar");
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



}