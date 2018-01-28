package structure.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 1/11/18
 * Time: 9:41 PM
 * Description:
 */
public class MyDeclarationVisitor extends ASTVisitor {

    public List<VariableDeclarationFragment> getVarList() {
        return varList;
    }

    public List<SimpleName> getSimpleNames() {
        return simpleNames;
    }

    public List<SimpleName> simpleNames = new ArrayList<>();

    List<VariableDeclarationFragment> varList = new ArrayList<VariableDeclarationFragment>();

    @Override
    public boolean visit(VariableDeclarationFragment node) {
        varList.add(node);
        return true;
    }

    @Override
    public boolean visit(SimpleName node) {
        simpleNames.add(node);
        return true;
    }
}
