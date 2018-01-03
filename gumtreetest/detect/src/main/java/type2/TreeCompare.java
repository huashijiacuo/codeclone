package type2;

import com.github.gumtreediff.matchers.heuristic.gt.AbstractSubtreeMatcher;
import com.github.gumtreediff.tree.ITree;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTMatcher;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import structure.ASTToDot;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 1/2/18
 * Time: 4:49 PM
 * Description:
 */
public class TreeCompare extends ASTMatcher {
    public static boolean compare(ASTNode root1, ASTNode root2) {
/*        if (root1 instanceof MethodDeclaration) {
            return new TreeCompare().match((MethodDeclaration)root1, root2);
        }*/
        ASTMatcher matcher = new Matcher4Type2();
//        return false;
        return root1.subtreeMatch(matcher, root2);
    }

    public boolean match(MethodDeclaration node, Object other) {
        if (!(other instanceof MethodDeclaration)) {
            return false;
        }
        MethodDeclaration o = (MethodDeclaration) other;
        int level = node.getAST().apiLevel();
        return node.isConstructor() == o.isConstructor()
                && safeSubtreeMatch(node.getJavadoc(), o.getJavadoc())
                && (level >= AST.JLS3
                ? safeSubtreeListMatch(node.modifiers(), o.modifiers())
                && safeSubtreeListMatch(node.typeParameters(), o.typeParameters())
                // n.b. compare return type even for constructors
                && safeSubtreeMatch(node.getReturnType2(), o.getReturnType2())
                : node.getModifiers() == o.getModifiers()
                // n.b. compare return type even for constructors
                && safeSubtreeMatch(node.getReturnType2(), o.getReturnType2()))
//                && safeSubtreeMatch(node.getName(), o.getName())
                && (level >= AST.JLS8
                ? safeSubtreeMatch(node.getReceiverType(), o.getReceiverType())
                && safeSubtreeMatch(node.getReceiverQualifier(), o.getReceiverQualifier())
                : true)
                && safeSubtreeListMatch(node.parameters(), o.parameters())
                && (level >= AST.JLS8
                ? safeSubtreeListMatch(node.extraDimensions(), o.extraDimensions())
                && safeSubtreeListMatch(node.thrownExceptionTypes(), o.thrownExceptionTypes())
                : node.getExtraDimensions() == o.getExtraDimensions()
                && safeSubtreeListMatch(node.thrownExceptionTypes(), o.thrownExceptionTypes()))
                && safeSubtreeMatch(node.getBody(), o.getBody());
    }
}
