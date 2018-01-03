package filter;

import com.github.gumtreediff.gen.jdt.AbstractJdtVisitor;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import structure.MethodNodeVisitor;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 1/2/18
 * Time: 10:48 AM
 * Description:
 */
public class DeletePrint implements DeleteNode{


    @Override
    public void deleteNode(AbstractJdtVisitor visitor, ASTNode root) {
        root.accept(visitor);
    }
}
