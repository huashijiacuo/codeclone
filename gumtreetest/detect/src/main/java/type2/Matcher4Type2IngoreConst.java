package type2;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTMatcher;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 1/17/18
 * Time: 7:43 PM
 * Description:
 */
public class Matcher4Type2IngoreConst extends ASTMatcher {

    /**
     * Returns whether the given node and the other object match.
     * <p>
     * The default implementation provided by this class tests whether the
     * other object is a node of the same type with structurally isomorphic
     * child subtrees. Subclasses may override this method as needed.
     * </p>
     *
     * @param node the node
     * @param other the other object, or <code>null</code>
     * @return <code>true</code> if the subtree matches, or
     *   <code>false</code> if they do not match or the other object has a
     *   different node type or is <code>null</code>
     *   String类型的常量不关心
     */
    @Override
    public boolean match(StringLiteral node, Object other) {
        if (!(other instanceof StringLiteral)) {
            return false;
        }
       /* StringLiteral o = (StringLiteral) other;
        return safeEquals(node.getEscapedValue(), o.getEscapedValue());*/
        return true;
    }


    /**
     * Returns whether the given node and the other object match.
     * <p>
     * The default implementation provided by this class tests whether the
     * other object is a node of the same type with structurally isomorphic
     * child subtrees. Subclasses may override this method as needed.
     * </p>
     *
     * @param node the node
     * @param other the other object, or <code>null</code>
     * @return <code>true</code> if the subtree matches, or
     *   <code>false</code> if they do not match or the other object has a
     *   different node type or is <code>null</code>
     */
    public boolean match(TypeDeclaration node, Object other) {
        if (!(other instanceof TypeDeclaration)) {
            return false;
        }
        TypeDeclaration o = (TypeDeclaration) other;
        int level = node.getAST().apiLevel();
        if (level == AST.JLS2) {
            if (node.getModifiers() != o.getModifiers()) {
                return false;
            }
            if (!safeSubtreeMatch(node.getSuperclassType(), o.getSuperclassType())) {
                return false;
            }
            if (!safeSubtreeListMatch(node.superInterfaces(), o.superInterfaces())) {
                return false;
            }
        }
        if (level >= AST.JLS3) {
            if (!safeSubtreeListMatch(node.modifiers(), o.modifiers())) {
                return false;
            }
            if (!safeSubtreeListMatch(node.typeParameters(), o.typeParameters())) {
                return false;
            }
            if (!safeSubtreeMatch(node.getSuperclassType(), o.getSuperclassType())) {
                return false;
            }
            if (!safeSubtreeListMatch(node.superInterfaceTypes(), o.superInterfaceTypes())) {
                return false;
            }
        }
        return (
                (node.isInterface() == o.isInterface())
                        && safeSubtreeMatch(node.getJavadoc(), o.getJavadoc())
//                        && safeSubtreeMatch(node.getName(), o.getName())
                        && safeSubtreeListMatch(node.bodyDeclarations(), o.bodyDeclarations()));
    }
}
