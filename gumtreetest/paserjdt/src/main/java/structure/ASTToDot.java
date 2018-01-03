package structure;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 1/2/18
 * Time: 11:14 AM
 * Description:
 */
import org.eclipse.jdt.core.dom.ASTNode;


public class ASTToDot {

    public static String ASTtoDotParser(MyMethodNode m) {
        String str = "digraph \"DirectedGraph\" {\n";
        // name
        str += ("graph [label = \"" + m.getMethodNode().getName() + "\", labelloc=t, concentrate = true];\n");
        for (MyASTNode mn : m.getNodeList()) {
            ASTNode astNode = mn.astNode;
            int hashcode = astNode.hashCode();
            int nodeType = astNode.getNodeType();
            int lineNum = mn.lineNum;
            str += ("\"" + String.valueOf(hashcode) + "\" [ label=\"" + astNode.getClass().getSimpleName() + "\" type=" + String.valueOf(nodeType) + " line="
                    + String.valueOf(lineNum) + " ]\n");
        }
        for (int[] k : m.getMapping()) {
            int pHashcode = k[0];
            int hashcode = k[1];
            str += ("\"" + String.valueOf(pHashcode) + "\" -> \"" + String.valueOf(hashcode) + "\"\n");
        }
        str += "}\n";
        return str;
    }
}
