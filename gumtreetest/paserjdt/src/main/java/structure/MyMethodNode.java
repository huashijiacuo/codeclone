package structure;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 12/27/17
 * Time: 3:42 PM
 * Description:
 */

import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.ArrayList;
import java.util.List;

public class MyMethodNode {

    private MethodDeclaration methodNode = null;
    private List<MyASTNode> nodeList = null;

    private List<int[]> mapping = null;

    public List<MyASTNode> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<MyASTNode> nodeList) {
        this.nodeList = nodeList;
    }

    public List<int[]> getMapping() {
        return mapping;
    }

    public void setMapping(List<int[]> mapping) {
        this.mapping = mapping;
    }

    public MethodDeclaration getMethodNode() {
        return methodNode;
    }

    public void setMethodNode(MethodDeclaration methodNode) {
        this.methodNode = methodNode;
    }

    public MyMethodNode() {
        this.methodNode = null;

        this.nodeList = new ArrayList<MyASTNode>();
        this.mapping = new ArrayList<int[]>();
    }

    @Override
    public String toString(){
        String name = methodNode.getName().toString();
        String type = methodNode.getReturnType2().toString();
        return type + "  " +  name ;
    }

}