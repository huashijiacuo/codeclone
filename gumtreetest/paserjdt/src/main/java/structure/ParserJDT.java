package structure;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.dom.*;
import structure.astnode.MyMethodNode;

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
    private ASTNode root;
    public List<MyMethodNode> parserFileToFunc(File file) throws CoreException {
        MyASTGenerator astGenerator = new MyASTGenerator(file);
        root = astGenerator.getRoot();
        List<MyMethodNode> methodNodeList = astGenerator.getMethodNodeList();
        for (MyMethodNode myMethodNode : methodNodeList) {
            System.out.println(myMethodNode.toString());
        }


/*        IJavaSearchScope scope = SearchEngine.createJavaSearchScope(new IJavaElement[] { project });
        //IJavaSearchScope scope = SearchEngine.createWorkspaceScope(); // Use this if you dont have the IProject in hand
        SearchPattern searchPattern = SearchPattern.createPattern(field, IJavaSearchConstants.REFERENCES);
        SearchRequestor requestor = new SearchRequestor() {
            @Override
            public void acceptSearchMatch(SearchMatch match) {
                System.out.println(match.getElement());
            }
        };
        SearchEngine searchEngine = new SearchEngine();
        searchEngine.search(searchPattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope,
                requestor, new NullProgressMonitor());


        VariableDeclaration variableDeclarationNode = new VariableDeclaration() {
            @Override
            public SimpleName getName() {
                return super.getName();
            }
        };
        IVariableBinding binding = variableDeclarationNode.resolveBinding();
        IJavaElement variableElement = binding.getJavaElement();*/

        return methodNodeList;
    }

    public ASTNode getRoot() {
        return root;
    }

    public void setRoot(ASTNode root) {
        this.root = root;
    }


}
