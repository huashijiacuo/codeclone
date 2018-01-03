package structure;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 12/27/17
 * Time: 3:40 PM
 * Description:
 */

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

public class NodeVisitor extends ASTVisitor {

    public List<ASTNode> nodeList = new ArrayList<ASTNode>();

    @Override
    public void preVisit(ASTNode node) {
        nodeList.add(node);
    }

    public List<ASTNode> getASTNodes() {
        return nodeList;
    }
}