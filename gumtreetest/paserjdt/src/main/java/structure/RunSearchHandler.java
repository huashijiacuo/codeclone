package structure;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 1/10/18
 * Time: 11:04 PM
 * Description:
 */



public class RunSearchHandler extends AbstractHandler {
    static boolean DEBUG = false;

    public void log(String msg) {
        if (DEBUG) {
            log("LOG: " + msg);
        }
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        Job search = new Job("Search") {

            @Override
            protected IStatus run(IProgressMonitor monitor) {
                monitor.beginTask("Search Contexts", IProgressMonitor.UNKNOWN);
                final Set<String> classNames = new TreeSet<String>();
                final ArrayList<IMethod> methods = new ArrayList<IMethod>();

                IMethod methodInQuestion = findMethods(
                        monitor,
                        "org.eclipse.e4.core.contexts.IEclipseContext.set(Class<T>, T)",
                        methods);
                parseTypeLiterals(methodInQuestion, classNames, methods);

                methods.clear();
                methodInQuestion = findMethods(
                        monitor,
                        "org.eclipse.e4.core.contexts.IEclipseContext.set(String, Object)",
                        methods);
                parseStringLiterals(methodInQuestion, classNames, methods);

                methods.clear();
                methodInQuestion = findMethods(
                        monitor,
                        "org.osgi.framework.BundleContext.registerService(Class<S>, S, Dictionary<String, ?>)",
                        methods);
                parseTypeLiterals(methodInQuestion, classNames, methods);

                for (String name : classNames) {
                    System.out.println("Available services: " + name);
                }
                monitor.done();
                return Status.OK_STATUS;
            }
        };
        search.schedule();

        return null;
    }

    private IMethod findMethods(IProgressMonitor monitor, String searchString,
                                final ArrayList<IMethod> methods) {
        final Map<String, Object> values = new HashMap<String, Object>();
        SearchRequestor findMethod = new SearchRequestor() {

            @Override
            public void acceptSearchMatch(SearchMatch match)
                    throws CoreException {
                if (match.getAccuracy() == SearchMatch.A_ACCURATE) {
                    values.put("method", match.getElement());
                } else {
                    log("Close, but no: " + match.getAccuracy() + ": for "
                            + match.getElement());
                }
            }
        };
        IJavaSearchScope workspaceScope = SearchEngine.createWorkspaceScope();
        SearchPattern pattern = SearchPattern.createPattern(searchString,
                IJavaSearchConstants.METHOD, IJavaSearchConstants.DECLARATIONS,
                SearchPattern.R_EXACT_MATCH);
        SearchEngine engine = new SearchEngine();
        SearchParticipant[] participant = new SearchParticipant[] { SearchEngine
                .getDefaultSearchParticipant() };
        try {
            engine.search(pattern, participant, workspaceScope, findMethod,
                    monitor);
        } catch (CoreException e) {
            e.printStackTrace();
        }

        final IMethod set = (IMethod) values.get("method");
        if (set == null) {
            log("Failed to find " + searchString);
            return null;
        }
        pattern = SearchPattern.createPattern(set,
                IJavaSearchConstants.REFERENCES);
        SearchRequestor findRefs = new SearchRequestor() {

            @Override
            public void acceptSearchMatch(SearchMatch match)
                    throws CoreException {
                if (match.getAccuracy() == SearchMatch.A_ACCURATE) {
                    if (match.getElement() instanceof IMethod) {
                        methods.add((IMethod) match.getElement());
                    } else {
                        log("Dunno what: " + match.getElement());
                    }
                }
            }
        };
        try {
            engine.search(pattern, participant, workspaceScope, findRefs,
                    monitor);
        } catch (CoreException e) {
            e.printStackTrace();
        }
        return set;
    }

    private void parseTypeLiterals(final IMethod methodInQuestion,
                                   final Set<String> classNames, final ArrayList<IMethod> methods) {
        if (methodInQuestion == null) {
            return;
        }
        for (final IMethod methodToSearch : methods) {

            ASTParser parser = ASTParser.newParser(AST.JLS3);
            parser.setResolveBindings(true);
            if (methodToSearch.isBinary()) {
                final IClassFile file = methodToSearch.getClassFile();
                if (file != null) {
                    parser.setSource(file);
                } else {
                    log("Failed to find binary for " + methodToSearch);
                    return;
                }
            } else {
                final ICompilationUnit file = methodToSearch
                        .getCompilationUnit();
                if (file != null) {
                    parser.setSource(file);
                } else {
                    log("Failed to find source for " + methodToSearch);
                    return;
                }
            }
            CompilationUnit cu = (CompilationUnit) parser.createAST(null);
            ASTVisitor visitor = new ASTVisitor() {
                @Override
                public boolean visit(MethodInvocation node) {
                    IMethodBinding methodBinding = node.resolveMethodBinding();
                    if (methodBinding == null) {
                        log("Failed to find method binding for " + node);
                        return false;
                    }
                    if (methodInQuestion.equals(methodBinding.getJavaElement())) {
                        final List arguments = node.arguments();
                        Object obj = arguments.get(0);
                        if (obj instanceof TypeLiteral) {
                            ITypeBinding tb = ((TypeLiteral) obj).getType()
                                    .resolveBinding();
                            classNames.add(tb.getQualifiedName());
                        } else {
                            log(methodToSearch + ": unexpect first parameter "
                                    + obj);
                        }
                    }
                    return true;
                }
            };
            cu.accept(visitor);
        }
    }

    private void parseStringLiterals(final IMethod methodInQuestion,
                                     final Set<String> classNames, final ArrayList<IMethod> methods) {
        for (final IMethod methodToSearch : methods) {

            ASTParser parser = ASTParser.newParser(AST.JLS3);
            parser.setResolveBindings(true);
            if (methodToSearch.isBinary()) {
                final IClassFile file = methodToSearch.getClassFile();
                if (file != null) {
                    parser.setSource(file);
                } else {
                    log("Failed to find binary for " + methodToSearch);
                    return;
                }
            } else {
                final ICompilationUnit file = methodToSearch
                        .getCompilationUnit();
                if (file != null) {
                    parser.setSource(file);
                } else {
                    log("Failed to find source for " + methodToSearch);
                    return;
                }
            }
            CompilationUnit cu = (CompilationUnit) parser.createAST(null);
            ASTVisitor visitor = new ASTVisitor() {
                @Override
                public boolean visit(MethodInvocation node) {
                    IMethodBinding methodBinding = node.resolveMethodBinding();
                    if (methodBinding == null) {
                        log("Failed to find method binding for " + node);
                        return false;
                    }
                    if (methodInQuestion.equals(methodBinding.getJavaElement())) {
                        final List arguments = node.arguments();
                        Object obj = arguments.get(0);
                        if (obj instanceof TypeLiteral) {
                            ITypeBinding tb = ((TypeLiteral) obj).getType()
                                    .resolveBinding();
                            classNames.add(tb.getQualifiedName());
                        } else {
                            log(methodToSearch + ": unexpect first parameter "
                                    + obj);
                        }
                    }
                    return true;
                }
            };
            cu.accept(visitor);
        }
    }

}