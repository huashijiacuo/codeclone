package util;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.internal.compiler.DefaultErrorHandlingPolicies;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.problem.DefaultProblemFactory;
import structure.JavaASTParser;

import java.io.File;
import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 12/29/17
 * Time: 3:58 PM
 * Description:
 */
public class GeneratorClass {
    public static ASTNode generator(MethodDeclaration methodDec) {
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource("".toCharArray());

        CompilationUnit comp = (CompilationUnit) parser.createAST(null);
        comp.recordModifications();

        AST ast = comp.getAST();

        /*
        *public class HelloWorld {
        *
        *}
        */
        TypeDeclaration classDec = ast.newTypeDeclaration();
        classDec.setInterface(false);//设置为非接口

        SimpleName className = ast.newSimpleName("HelloWorld");
        Modifier classModifier = ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD);

        //设置类节点
        classDec.setName(className);//类名
        classDec.modifiers().add(classModifier);//类可见性

        //将类节点连接为编译单元的子节点
        comp.types().add(classDec);

        //将方法节点连接为类节点的子节点

        MethodDeclaration newMethod = (MethodDeclaration)ASTNode.copySubtree(ast, methodDec);
        classDec.bodyDeclarations().add(newMethod);
        return comp;

    }


    public static void main(String[] args) {
        new GeneratorClass().build();
    }

    private void build() {
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource("".toCharArray());

        CompilationUnit comp = (CompilationUnit) parser.createAST(null);
        comp.recordModifications();

        AST ast = comp.getAST();

        /*
        *public class HelloWorld {
        *
        *}
        */
        TypeDeclaration classDec = ast.newTypeDeclaration();
        classDec.setInterface(false);//设置为非接口

        SimpleName className = ast.newSimpleName("HelloWorld");
        Modifier classModifier = ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD);

        //设置类节点
        classDec.setName(className);//类名
        classDec.modifiers().add(classModifier);//类可见性

        //将类节点连接为编译单元的子节点
        comp.types().add(classDec);

        /*
        *public class HelloWorld {
        *  public HelloWorld(){
        *
        *  }
        *}
        */
        MethodDeclaration methodDec = ast.newMethodDeclaration();
        methodDec.setConstructor(true);//设置为构造函数

        SimpleName methodName = ast.newSimpleName("HelloWorld");
        Modifier methodModifier = ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD);
        Block methodBody = ast.newBlock();

        //设置方法节点
        methodDec.setName(methodName);// 方法名
        methodDec.modifiers().add(methodModifier);//方法可见性
        methodDec.setBody(methodBody); //方法体

        //将方法节点连接为类节点的子节点
        classDec.bodyDeclarations().add(methodDec);


        //End
        System.out.println(comp.toString());
    }
}
