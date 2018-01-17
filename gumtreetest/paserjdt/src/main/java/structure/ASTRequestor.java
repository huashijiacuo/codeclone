package structure;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 1/11/18
 * Time: 8:44 PM
 * Description:
 */

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;
import org.eclipse.jdt.core.dom.IBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 接受CompilationUnit和bindingMap信息
 * @author Chen Zhe
 *
 */
public class ASTRequestor extends FileASTRequestor {
    private List<CompilationUnit> correctUnits;
    private Map<String, IBinding> correctBindings;
    public ASTRequestor() {
        this.correctUnits = new ArrayList<>();
        this.correctBindings = new HashMap<>();
    }

    @Override
    public void acceptAST(String sourceFilePath, CompilationUnit node) {
//        node.setFileName(sourceFilePath);
        correctUnits.add(node);
    }

    @Override
    public void acceptBinding(String bindingKey, IBinding binding) {
        correctBindings.put(bindingKey, binding);
    }

    public List<CompilationUnit> getCompilationUnits() {
        return correctUnits;
    }

    /**
     * 获取键值对？？？
     * @return
     */
    public Map<String, IBinding> getBindingMaps() {
        return correctBindings;
    }
}