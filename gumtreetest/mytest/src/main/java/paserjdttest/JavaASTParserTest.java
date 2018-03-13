package paserjdttest;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.*;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchPattern;
import structure.*;
import structure.ASTRequestor;
import structure.visitor.VariableNodeVisitor;
import type2.TreeCompare;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 1/11/18
 * Time: 9:21 PM
 * Description:
 */
public class JavaASTParserTest {

    public static void main(String[] args) {
        String fileName1 = "/home/shi/Desktop/codeclone/dataset/selected"; //
        JavaASTParser parser = new JavaASTParser(fileName1, null, false);
        List<CompilationUnit> list = parser.getCompilationUnits();
        ASTRequestor requestor = parser.getAstRequestor();
        System.out.println("-----------------------\nCompilationUnit numbers: " + list.size());
       /* int i = 0;
        CompilationUnit cu1 = list.get(0);
        CompilationUnit cu2 = list.get(1);
        VariableNodeVisitor varVisitor1 = new VariableNodeVisitor();
        cu1.accept(varVisitor1);
        VariableNodeVisitor varVisitor2 = new VariableNodeVisitor();
        cu2.accept(varVisitor2);
        Map<IBinding, List<ASTNode>> map1 = varVisitor1.getBindMap();
        Set<SimpleName> names1 = varVisitor1.getSimpleNames();
        Map<IBinding, List<ASTNode>> map2 = varVisitor2.getBindMap();
        Set<SimpleName> names2 = varVisitor2.getSimpleNames();
        int len1 = names1.size();
        int len2 = names2.size();
        if (len1 == len2) {
            Iterator<SimpleName> it1 = names1.iterator();
            Iterator<SimpleName> it2 = names2.iterator();
            while(it1.hasNext() && it2.hasNext()) {
                SimpleName name1 = it1.next();
                SimpleName name2 = it2.next();
                if (name1 == null || name2 == null) {
                    continue;
                }
                IBinding binding1 = name1.resolveBinding();
                IBinding binding2 = name2.resolveBinding();
                List<ASTNode> list1 = map1.get(binding1);
                List<ASTNode> list2 = map2.get(binding2);
                for (int j = 0; j < list1.size(); j++) {
                    ASTNode node1 = list1.get(j);
                    ASTNode node2 = list2.get(j);
                    if (node1 instanceof SimpleName && node2 instanceof SimpleName) {
                        String identifier = ((SimpleName) node2).getIdentifier();
                        ((SimpleName) node1).setIdentifier(identifier);
                    }
                }
            }
        }
        System.out.println("-----------------------\ncu1");
        System.out.println(cu1.toString());
        System.out.println("-----------------------\ncu2");
        System.out.println(cu2.toString());
        boolean result = TreeCompare.compareIgnoreConstanStr(cu1, cu2);
        System.out.println("-----------------------\nThe result of comparation is : " + result);*/
        /*for (CompilationUnit compilationUnit : list) {
            System.out.println("-----------------------\n" + "CompilationUnit  " + i++ + "\n-----------------------");
            VariableNodeVisitor varVisitor = new VariableNodeVisitor();
            compilationUnit.accept(varVisitor);
            Map<IBinding, List<ASTNode>> map = varVisitor.getBindMap();
            System.out.println("-----------------------\n" + "binding nums  " + map.size() + "\n-----------------------");
            Iterator<IBinding> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                IBinding binding = iterator.next();
                if (binding == null) {
                    continue;
                }
                List<ASTNode> nodeList = map.get(binding);

                System.out.println(binding.getKey() + ": " + nodeList.size());
                int hash = binding.hashCode();
                System.out.println(hash);
            }
//            compilationUnit.findDeclaringNode()
//            MyDeclarationVisitor declVisitor = new MyDeclarationVisitor();
//            compilationUnit.accept(declVisitor);

            Set<SimpleName> names = varVisitor.getSimpleNames();
            for (SimpleName name : names) {
                IBinding binding = name.resolveBinding();
                ASTNode node = compilationUnit.findDeclaringNode(binding);
                if (node instanceof VariableDeclarationFragment)
                    System.out.println("VariableDeclarationFragment name: " + name + "; node:" + ((VariableDeclarationFragment) node).getName());
                else if(node instanceof MethodDeclaration) {
                    System.out.println("MethodDeclaration name: " + ((MethodDeclaration) node).getName());
                } else if(node instanceof VariableDeclaration) {
                    IJavaElement variableElement = binding.getJavaElement();
                    System.out.println("VariableDeclaration name: " + ((VariableDeclaration) node).getName());
                }
            }
            Map bindMap = requestor.getBindingMaps();
            if (bindMap.size() == 0) {
                System.out.println("empty, this method is failed");
            }
        }*/
    }

    public void getScope() {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IProject[] projects = root.getProjects();

        for (IProject project : projects) {
            IJavaProject javaProject = JavaCore.create(project);
            IPackageFragment[] packages = null;
            try {
                packages = javaProject.getPackageFragments();
            } catch (JavaModelException e) {
                e.printStackTrace();
            }

            // step 1: Create a search pattern
            SearchPattern pattern = SearchPattern.createPattern("abcde",
                    IJavaSearchConstants.METHOD,
                    IJavaSearchConstants.DECLARATIONS,
                    SearchPattern.R_EXACT_MATCH);
            // step 2: Create search scope
            IJavaSearchScope scope = SearchEngine.createJavaSearchScope(packages);
        }
    }

}
