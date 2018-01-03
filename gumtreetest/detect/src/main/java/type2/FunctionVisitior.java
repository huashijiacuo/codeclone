package type2;

import org.eclipse.jdt.core.dom.*;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 1/3/18
 * Time: 10:35 AM
 * Description:
 */
public class FunctionVisitior extends ASTVisitor {

/*    @Override
    public boolean visit(ExpressionStatement node) {
        Expression expression = node.getExpression();
        if (expression instanceof MethodInvocation) {
            Name name = ((MethodInvocation) expression).getName();
            if (name.toString().equals("println") || name.toString().equals("print")) {
                node.delete();
                return false;
            }
        }
        return true;
    }*/
}
