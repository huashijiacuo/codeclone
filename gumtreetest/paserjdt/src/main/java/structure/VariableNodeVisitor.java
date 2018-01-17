package structure;

import org.eclipse.jdt.core.dom.*;
import util.JavaCheckerUtility;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 1/3/18
 * Time: 9:18 PM
 * Description:
 */
public class VariableNodeVisitor extends ASTVisitor {

    List<VariableDeclarationStatement> variableDeclarationStatements = new ArrayList<>();

    Set<SimpleName> simpleNames = new LinkedHashSet<>();

    Map<IBinding, List<ASTNode>> bindMap = new LinkedHashMap<IBinding, List<ASTNode>>();



    @Override
    public boolean visit(VariableDeclarationFragment variableDeclarationFragment) {
        SimpleName simpleName = variableDeclarationFragment.getName();
        simpleNames.add(simpleName);
        return true;
    }

    @Override
    public boolean visit(SingleVariableDeclaration singleVariableDeclaration) {
        SimpleName simpleName = singleVariableDeclaration.getName();
        simpleNames.add(simpleName);
        return true;
    }

    @Override
    public boolean visit(SimpleName node) {
//        simpleNames.add(node);

        IBinding binding = node.resolveBinding();
        if (bindMap.containsKey(binding)) {
            bindMap.get(binding).add(node);
        } else {
            List<ASTNode> list = new ArrayList<>();
            list.add(node);
            bindMap.put(binding, list);
        }
//        JavaCheckerUtility.getDeclaration(node);
        return true;
    }

    public Set<SimpleName> getSimpleNames() {
        return simpleNames;
    }

    public List<VariableDeclarationStatement> getVariableDeclarationStatements() {
        return variableDeclarationStatements;
    }

    public Map<IBinding, List<ASTNode>> getBindMap() {
        return bindMap;
    }

}
