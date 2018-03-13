package structure.visitor;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 12/27/17
 * Time: 3:39 PM
 * Description:
 */

import org.eclipse.jdt.core.IJavaElement;
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
    public boolean visit(VariableDeclarationFragment node) {
//        IVariableBinding binding = node.resolveBinding();
//        ITypeBinding typeBinding = node.getName().resolveTypeBinding();
//        System.out.println(((CompilationUnit) node.getRoot()).findDeclaringNode(binding));
//        System.out.println(((CompilationUnit) node.getRoot()).findDeclaringNode(typeBinding));
//        IJavaElement element = binding.getVariableDeclaration().getJavaElement();
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
//                node.delete();  //此处应该删除打印信息的节点，因为该节点不影响程序的执行过程，只是一个信息的展示，然而删除后导致解析出错，有待研究
                return false;
            }

        }
        return true;
    }

}