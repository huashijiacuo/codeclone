package variableflow;

import javafx.scene.chart.ValueAxis;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import structure.VariableNodeVisitor;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 1/3/18
 * Time: 9:11 PM
 * Description:
 */
public class GeneratorVariableDeclaration {
    private Set<ASTNode> variableDeclarationSet;

    private Map<ASTNode, List<ASTNode>> variableUsedMap;

    public Set<ASTNode> getVariableDeclarationSet() {
        return variableDeclarationSet;
    }

    public void setVariableDeclarationSet(Set<ASTNode> variableDeclarationSet) {
        this.variableDeclarationSet = variableDeclarationSet;
    }

    public Map<ASTNode, List<ASTNode>> getVariableUsedMap() {
        return variableUsedMap;
    }

    public void setVariableUsedMap(Map<ASTNode, List<ASTNode>> variableUsedMap) {
        this.variableUsedMap = variableUsedMap;
    }

    public GeneratorVariableDeclaration() {
        variableDeclarationSet = new HashSet<ASTNode>();
        variableUsedMap = new HashMap<>();
    }

    public Set<SimpleName> findVariableDeclaration(ASTNode root) {
        VariableNodeVisitor varibleVisitor = new VariableNodeVisitor();
        root.accept(varibleVisitor);
        Set set = varibleVisitor.getSimpleNames();
        return set;
    }
}
