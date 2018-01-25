package type3;

import com.github.gumtreediff.actions.ActionGenerator;
import com.github.gumtreediff.actions.model.*;
import com.github.gumtreediff.gen.jdt.JdtTreeGenerator;
import com.github.gumtreediff.matchers.MappingStore;
import com.github.gumtreediff.matchers.Matcher;
import com.github.gumtreediff.matchers.Matchers;
import com.github.gumtreediff.tree.ITree;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import structure.NodeVisitor;
import util.GeneratorClass;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 1/24/18
 * Time: 11:31 AM
 * Description:
 */
public class Similarity {

    public double getSimilarity(ASTNode node1, ASTNode node2) throws IOException {
        if (!(node1 instanceof MethodDeclaration && node2 instanceof MethodDeclaration)) {
            throw new ClassCastException();
        }
        NodeVisitor nv = new NodeVisitor();
        node1.accept(nv);
        int nodeNum1 = nv.getASTNodes().size();
        nv = new NodeVisitor();
        node2.accept(nv);
        int nodeNum2 = nv.getASTNodes().size();
        System.out.println("nodeNum1 = " + nodeNum1 + "\nnodeNum2 = " + nodeNum2);
        ASTNode newNode1 = GeneratorClass.generator((MethodDeclaration) node1);
        ASTNode newNode2 = GeneratorClass.generator((MethodDeclaration) node2);
        ITree src = new JdtTreeGenerator().generateFromString(newNode1.toString()).getRoot();
        ITree dst = new JdtTreeGenerator().generateFromString(newNode2.toString()).getRoot();
        Matcher m = Matchers.getInstance().getMatcher(src, dst); // retrieve the default matcher
        m.match();
        MappingStore mappings = m.getMappings();
        ActionGenerator g = new ActionGenerator(src, dst, m.getMappings());
        g.generate();
        List<Action> actions = g.getActions(); // return the actions
        int add = 0, delet = 0, update = 0, mov = 0, insert = 0;
        for (Action action : actions) {
            if (action instanceof Addition) {
                add++;
            } else if (action instanceof Delete) {
                delet++;
            } else if (action instanceof Update) {
                update++;
            } else if (action instanceof Move) {
                mov++;
            } else {
                insert++;
            }
        }
        int s1 = nodeNum1 - update - delet;
        int s2 = nodeNum2 - update - add;
        System.out.println("s1  = " + s1 +  "   s2  = " + s2 );
        System.out.println("func1： " + newNode1.structuralPropertiesForType().get(0) + "\nfunc2: " + newNode2.toString());
        for (Action action : actions) {
            System.out.println(action.toShortString());
        }
        double rs = 2.0 * s1 / (nodeNum1 + nodeNum2);
        return rs;
    }

}
