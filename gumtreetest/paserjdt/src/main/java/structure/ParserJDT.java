package structure;

import org.eclipse.jdt.core.dom.ASTNode;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 12/27/17
 * Time: 9:07 PM
 * Description:
 */
public class ParserJDT {
    public List<MyMethodNode> parserFileToFunc(File file) {
        MyASTGenerator astGenerator = new MyASTGenerator(file);
        List<MyMethodNode> methodNodeList = astGenerator.getMethodNodeList();
        for (MyMethodNode myMethodNode : methodNodeList) {
            System.out.println(myMethodNode.toString());
        }
        return methodNodeList;
    }
}
