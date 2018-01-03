package type2;

import org.eclipse.jdt.core.dom.*;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 1/2/18
 * Time: 10:12 PM
 * Description:
 */
public class Matcher4Type2 extends ASTMatcher {


    @Override
    public boolean match(MethodDeclaration node, Object other) {
        if (!(other instanceof MethodDeclaration)) {
            return false;
        }
        MethodDeclaration o = (MethodDeclaration) other;
        int level = node.getAST().apiLevel();
        return node.isConstructor() == o.isConstructor()
                && safeSubtreeMatch(node.getJavadoc(), o.getJavadoc())
                && (level >= AST.JLS3
                ? safeSubtreeListMatch(node.modifiers(), o.modifiers())
                && safeSubtreeListMatch(node.typeParameters(), o.typeParameters())
                // n.b. compare return type even for constructors
                && safeSubtreeMatch(node.getReturnType2(), o.getReturnType2())
                : node.getModifiers() == o.getModifiers()
                // n.b. compare return type even for constructors
                && safeSubtreeMatch(node.getReturnType2(), o.getReturnType2()))
//                && safeSubtreeMatch(node.getName(), o.getName())
                && (level >= AST.JLS8
                ? safeSubtreeMatch(node.getReceiverType(), o.getReceiverType())
                && safeSubtreeMatch(node.getReceiverQualifier(), o.getReceiverQualifier())
                : true)
                && safeSubtreeListMatch(node.parameters(), o.parameters())
                && (level >= AST.JLS8
                ? safeSubtreeListMatch(node.extraDimensions(), o.extraDimensions())
                && safeSubtreeListMatch(node.thrownExceptionTypes(), o.thrownExceptionTypes())
                : node.getExtraDimensions() == o.getExtraDimensions()
                && safeSubtreeListMatch(node.thrownExceptionTypes(), o.thrownExceptionTypes()))
                && safeSubtreeMatch(node.getBody(), o.getBody());
    }

    @Override
    public boolean match(AnnotationTypeDeclaration node, Object other) {
        if (!(other instanceof AnnotationTypeDeclaration)) {
            return false;
        }
        AnnotationTypeDeclaration o = (AnnotationTypeDeclaration) other;
        // node type added in JLS3 - ignore old JLS2-style modifiers
        return (safeSubtreeMatch(node.getJavadoc(), o.getJavadoc())
                && safeSubtreeListMatch(node.modifiers(), o.modifiers())
                && safeSubtreeMatch(node.getName(), o.getName())
                && safeSubtreeListMatch(node.bodyDeclarations(), o.bodyDeclarations()));
    }

    @Override
    public boolean match(AnnotationTypeMemberDeclaration node, Object other) {
        if (!(other instanceof AnnotationTypeMemberDeclaration)) {
            return false;
        }
        AnnotationTypeMemberDeclaration o = (AnnotationTypeMemberDeclaration) other;
        // node type added in JLS3 - ignore old JLS2-style modifiers
        return (safeSubtreeMatch(node.getJavadoc(), o.getJavadoc())
                && safeSubtreeListMatch(node.modifiers(), o.modifiers())
                && safeSubtreeMatch(node.getType(), o.getType())
                && safeSubtreeMatch(node.getName(), o.getName())
                && safeSubtreeMatch(node.getDefault(), o.getDefault()));
    }

    @Override
    public boolean match(ClassInstanceCreation node, Object other) {
        if (!(other instanceof ClassInstanceCreation)) {
            return false;
        }
        ClassInstanceCreation o = (ClassInstanceCreation) other;
        int level = node.getAST().apiLevel();
        if (level == AST.JLS2) {
            if (!safeSubtreeMatch(node.getType(), o.getType())) {
                return false;
            }
        }
        if (level >= AST.JLS3) {
            if (!safeSubtreeListMatch(node.typeArguments(), o.typeArguments())) {
                return false;
            }
            if (!safeSubtreeMatch(node.getType(), o.getType())) {
                return false;
            }
        }
        return
                safeSubtreeMatch(node.getExpression(), o.getExpression())
                        && safeSubtreeListMatch(node.arguments(), o.arguments())
                        && safeSubtreeMatch(
                        node.getAnonymousClassDeclaration(),
                        o.getAnonymousClassDeclaration());
    }

    @Override
    public boolean match(EnumConstantDeclaration node, Object other) {
        if (!(other instanceof EnumConstantDeclaration)) {
            return false;
        }
        EnumConstantDeclaration o = (EnumConstantDeclaration) other;
        return (
                safeSubtreeMatch(node.getJavadoc(), o.getJavadoc())
                        && safeSubtreeListMatch(node.modifiers(), o.modifiers())
                        && safeSubtreeMatch(node.getName(), o.getName())
                        && safeSubtreeListMatch(node.arguments(), o.arguments())
                        && safeSubtreeMatch(
                        node.getAnonymousClassDeclaration(),
                        o.getAnonymousClassDeclaration()));
    }

    @Override
    public boolean match(EnumDeclaration node, Object other) {
        if (!(other instanceof EnumDeclaration)) {
            return false;
        }
        EnumDeclaration o = (EnumDeclaration) other;
        return (
                safeSubtreeMatch(node.getJavadoc(), o.getJavadoc())
                        && safeSubtreeListMatch(node.modifiers(), o.modifiers())
                        && safeSubtreeMatch(node.getName(), o.getName())
                        && safeSubtreeListMatch(node.superInterfaceTypes(), o.superInterfaceTypes())
                        && safeSubtreeListMatch(node.enumConstants(), o.enumConstants())
                        && safeSubtreeListMatch(
                        node.bodyDeclarations(),
                        o.bodyDeclarations()));
    }

    @Override
    public boolean match(ExpressionMethodReference node, Object other) {
        if (!(other instanceof ExpressionMethodReference)) {
            return false;
        }
        ExpressionMethodReference o = (ExpressionMethodReference) other;
        return (
                safeSubtreeMatch(node.getExpression(), o.getExpression())
                        && safeSubtreeListMatch(node.typeArguments(), o.typeArguments())
                        && safeSubtreeMatch(node.getName(), o.getName()));
    }

    @Override
    public boolean match(FieldAccess node, Object other) {
        if (!(other instanceof FieldAccess)) {
            return false;
        }
        FieldAccess o = (FieldAccess) other;
        return (
                safeSubtreeMatch(node.getExpression(), o.getExpression())
                        && safeSubtreeMatch(node.getName(), o.getName()));
    }

    @Override
    public boolean match(ImportDeclaration node, Object other) {
        if (!(other instanceof ImportDeclaration)) {
            return false;
        }
        ImportDeclaration o = (ImportDeclaration) other;
        if (node.getAST().apiLevel() >= AST.JLS3) {
            if (node.isStatic() != o.isStatic()) {
                return false;
            }
        }
        return (
                safeSubtreeMatch(node.getName(), o.getName())
                        && node.isOnDemand() == o.isOnDemand());
//        return node.isOnDemand() == o.isOnDemand();
    }

    @Override
    public boolean match(MemberRef node, Object other) {
        if (!(other instanceof MemberRef)) {
            return false;
        }
        MemberRef o = (MemberRef) other;
        return (
                safeSubtreeMatch(node.getQualifier(), o.getQualifier())
                        && safeSubtreeMatch(node.getName(), o.getName()));
    }

    @Override
    public boolean match(MemberValuePair node, Object other) {
        if (!(other instanceof MemberValuePair)) {
            return false;
        }
        MemberValuePair o = (MemberValuePair) other;
        return (safeSubtreeMatch(node.getName(), o.getName())
                && safeSubtreeMatch(node.getValue(), o.getValue()));
    }

    @Override
    public boolean match(MethodRef node, Object other) {
        if (!(other instanceof MethodRef)) {
            return false;
        }
        MethodRef o = (MethodRef) other;
        return (
                safeSubtreeMatch(node.getQualifier(), o.getQualifier())
                        && safeSubtreeMatch(node.getName(), o.getName())
                        && safeSubtreeListMatch(node.parameters(), o.parameters()));
    }

    @Override
    public boolean match(MethodRefParameter node, Object other) {
        if (!(other instanceof MethodRefParameter)) {
            return false;
        }
        MethodRefParameter o = (MethodRefParameter) other;
        int level = node.getAST().apiLevel();
        if (level >= AST.JLS3) {
            if (node.isVarargs() != o.isVarargs()) {
                return false;
            }
        }
        return (
                safeSubtreeMatch(node.getType(), o.getType())
                        && safeSubtreeMatch(node.getName(), o.getName()));   //待考虑
    }

    @Override
    public boolean match(MethodInvocation node, Object other) {
        if (!(other instanceof MethodInvocation)) {
            return false;
        }
        MethodInvocation o = (MethodInvocation) other;
        if (node.getAST().apiLevel() >= AST.JLS3) {
            if (!safeSubtreeListMatch(node.typeArguments(), o.typeArguments())) {
                return false;
            }
        }
        return (
                safeSubtreeMatch(node.getExpression(), o.getExpression())
                        && safeSubtreeMatch(node.getName(), o.getName())
                        && safeSubtreeListMatch(node.arguments(), o.arguments()));
    }

    @Override
    public boolean match(NameQualifiedType node, Object other) {
        if (!(other instanceof NameQualifiedType)) {
            return false;
        }
        NameQualifiedType o = (NameQualifiedType) other;
        return safeSubtreeMatch(node.getQualifier(), o.getQualifier())
                && safeSubtreeListMatch(node.annotations(), o.annotations())
                && safeSubtreeMatch(node.getName(), o.getName());
    }

    @Override
    public boolean match(PackageDeclaration node, Object other) {
        if (!(other instanceof PackageDeclaration)) {
            return false;
        }
        PackageDeclaration o = (PackageDeclaration) other;
        if (node.getAST().apiLevel() >= AST.JLS3) {
            if (!safeSubtreeMatch(node.getJavadoc(), o.getJavadoc())) {
                return false;
            }
            if (!safeSubtreeListMatch(node.annotations(), o.annotations())) {
                return false;
            }
        }
        return safeSubtreeMatch(node.getName(), o.getName());
    }

    @Override
    public boolean match(QualifiedName node, Object other) {
        if (!(other instanceof QualifiedName)) {
            return false;
        }
        QualifiedName o = (QualifiedName) other;
        return safeSubtreeMatch(node.getQualifier(), o.getQualifier())
                && safeSubtreeMatch(node.getName(), o.getName());
    }


    public boolean match(QualifiedType node, Object other) {
        if (!(other instanceof QualifiedType)) {
            return false;
        }
        QualifiedType o = (QualifiedType) other;
        int level = node.getAST().apiLevel();
        return safeSubtreeMatch(node.getQualifier(), o.getQualifier())
                && (level >= AST.JLS8 ? safeSubtreeListMatch(node.annotations(), o.annotations()) : true)
                && safeSubtreeMatch(node.getName(), o.getName());
    }

    public boolean match(SimpleType node, Object other) {
        if (!(other instanceof SimpleType)) {
            return false;
        }
        SimpleType o = (SimpleType) other;
        int level = node.getAST().apiLevel();
        return (level >= AST.JLS8 ? safeSubtreeListMatch(node.annotations(), o.annotations()) : true)
                && safeSubtreeMatch(node.getName(), o.getName());
    }

    public boolean match(SingleVariableDeclaration node, Object other) {
        if (!(other instanceof SingleVariableDeclaration)) {
            return false;
        }
        SingleVariableDeclaration o = (SingleVariableDeclaration) other;
        int level = node.getAST().apiLevel();
        return (level >= AST.JLS3
                ? safeSubtreeListMatch(node.modifiers(), o.modifiers())
                : node.getModifiers() == o.getModifiers())
                && safeSubtreeMatch(node.getType(), o.getType())
                && (level >= AST.JLS8 && node.isVarargs()
                ? safeSubtreeListMatch(node.varargsAnnotations(), o.varargsAnnotations())
                : true)
                && (level >= AST.JLS3
                ? node.isVarargs() == o.isVarargs()
                : true)
//                && safeSubtreeMatch(node.getName(), o.getName())
                && ((level >= AST.JLS8)
                ? safeSubtreeListMatch(node.extraDimensions(), o.extraDimensions())
                : node.getExtraDimensions() == o.getExtraDimensions())
                && safeSubtreeMatch(node.getInitializer(), o.getInitializer());
    }


    public boolean match(SuperFieldAccess node, Object other) {
        if (!(other instanceof SuperFieldAccess)) {
            return false;
        }
        SuperFieldAccess o = (SuperFieldAccess) other;
        return (
                safeSubtreeMatch(node.getName(), o.getName())
                        && safeSubtreeMatch(node.getQualifier(), o.getQualifier()));
    }


    public boolean match(SuperMethodInvocation node, Object other) {
        if (!(other instanceof SuperMethodInvocation)) {
            return false;
        }
        SuperMethodInvocation o = (SuperMethodInvocation) other;
        if (node.getAST().apiLevel() >= AST.JLS3) {
            if (!safeSubtreeListMatch(node.typeArguments(), o.typeArguments())) {
                return false;
            }
        }
        return (
                safeSubtreeMatch(node.getQualifier(), o.getQualifier())
                        && safeSubtreeMatch(node.getName(), o.getName())
                        && safeSubtreeListMatch(node.arguments(), o.arguments()));
    }


    public boolean match(SuperMethodReference node, Object other) {
        if (!(other instanceof SuperMethodReference)) {
            return false;
        }
        SuperMethodReference o = (SuperMethodReference) other;
        return (safeSubtreeMatch(node.getQualifier(), o.getQualifier())
                && safeSubtreeListMatch(node.typeArguments(), o.typeArguments())
                && safeSubtreeMatch(node.getName(), o.getName()));
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
            if (!safeSubtreeListMatch(node.superInterfaceTypes(), o.superInterfaceTypes())) {
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
                        && safeSubtreeMatch(node.getName(), o.getName())
                        && safeSubtreeListMatch(node.bodyDeclarations(), o.bodyDeclarations()));
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
     * @since 3.10
     */
    public boolean match(TypeMethodReference node, Object other) {
        if (!(other instanceof TypeMethodReference)) {
            return false;
        }
        TypeMethodReference o = (TypeMethodReference) other;
        return (
                safeSubtreeMatch(node.getType(), o.getType())
                        && safeSubtreeListMatch(node.typeArguments(), o.typeArguments())
                        && safeSubtreeMatch(node.getName(), o.getName()));
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
     * @since 3.1
     */
    public boolean match(TypeParameter node, Object other) {
        if (!(other instanceof TypeParameter)) {
            return false;
        }
        TypeParameter o = (TypeParameter) other;
        int level = node.getAST().apiLevel();
        return (level >= AST.JLS8 ? safeSubtreeListMatch(node.modifiers(), o.modifiers()) : true)
                && safeSubtreeMatch(node.getName(), o.getName())
                && safeSubtreeListMatch(node.typeBounds(), o.typeBounds());
    }


    /**
     * Returns whether the given node and the other object match.
     * <p>
     * The default implementation provided by this class tests whether the
     * other object is a node of the same type with structurally isomorphic
     * child subtrees. Subclasses may override this method as needed.
     * </p>
     * <p>
     * Note that extra array dimensions are compared since they are an
     * important part of the type of the variable.
     * </p>
     *
     * @param node the node
     * @param other the other object, or <code>null</code>
     * @return <code>true</code> if the subtree matches, or
     *   <code>false</code> if they do not match or the other object has a
     *   different node type or is <code>null</code>
     *   声明时，只关注变量类型，不关心名字
     */
    public boolean match(VariableDeclarationFragment node, Object other) {
        if (!(other instanceof VariableDeclarationFragment)) {
            return false;
        }
        VariableDeclarationFragment o = (VariableDeclarationFragment) other;
        int level = node.getAST().apiLevel();
/*        return safeSubtreeMatch(node.getName(), o.getName())
                && (level >= AST.JLS8
                ? safeSubtreeListMatch(node.extraDimensions(), o.extraDimensions())
                : node.getExtraDimensions() == o.getExtraDimensions())
                && safeSubtreeMatch(node.getInitializer(), o.getInitializer());*/
        return (level >= AST.JLS8
                ? safeSubtreeListMatch(node.extraDimensions(), o.extraDimensions())
                : node.getExtraDimensions() == o.getExtraDimensions())
                && safeSubtreeMatch(node.getInitializer(), o.getInitializer());
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
     *   只关注变量类型，不关心名字
     */
    @Override
    public boolean match(SimpleName node, Object other) {
        if (!(other instanceof SimpleName)) {
            return false;
        }
       /* SimpleName o = (SimpleName) other;
        return node.getIdentifier().equals(o.getIdentifier());*/
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
     *   String类型的常量不关心
     */
    public boolean match(StringLiteral node, Object other) {
        if (!(other instanceof StringLiteral)) {
            return false;
        }
       /* StringLiteral o = (StringLiteral) other;
        return safeEquals(node.getEscapedValue(), o.getEscapedValue());*/
       return true;
    }
}
