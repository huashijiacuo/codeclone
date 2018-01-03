package structure;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 12/27/17
 * Time: 3:39 PM
 * Description:
 */

import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;


public class MethodNodeVisitor extends ASTVisitor {

    List<MethodDeclaration> methodNodeList = new ArrayList<>();

    public List<MethodDeclaration> getMethodDecs() {
        return methodNodeList;
    }

    @Override
    public boolean visit(MethodDeclaration node) {
        methodNodeList.add(node);
        return true;
    }

    @Override
    public boolean visit(ExpressionStatement node) {
        Expression expression = node.getExpression();
        if (expression instanceof MethodInvocation) {
            SimpleName name = ((MethodInvocation) expression).getName();
            if (name.getIdentifier().equals("println") || name.getIdentifier().equals("print")
                    || name.getIdentifier().equals("debug") || name.getIdentifier().equals("error")
                    || name.getIdentifier().equals("info")) {
                node.delete();
                return false;
            }
        }
        return true;
    }

}