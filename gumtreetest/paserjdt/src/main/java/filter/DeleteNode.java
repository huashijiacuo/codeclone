package filter;

import com.github.gumtreediff.gen.jdt.AbstractJdtVisitor;
import org.eclipse.jdt.core.dom.ASTNode;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 1/2/18
 * Time: 10:49 AM
 * Description:
 */
interface DeleteNode {
    void deleteNode(AbstractJdtVisitor visitor, ASTNode root);
}
