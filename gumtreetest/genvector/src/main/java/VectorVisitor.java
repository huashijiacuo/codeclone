import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 3/13/18
 * Time: 11:30 AM
 * Description:
 */
public class VectorVisitor extends ASTVisitor {

    private  Map<String, Integer> nodeMap = new HashMap<String, Integer>();

    public Map<String, Integer> getNodeMap() {
        return nodeMap;
    }

    private void addNodeToMap(ASTNode node) {
        String nodeName = node.getClass().getName();
        if (nodeMap.containsKey(nodeName)) {
            int freq = nodeMap.get(nodeName);
            nodeMap.replace(nodeName,freq, freq+1);
        } else {
            nodeMap.put(nodeName, 1);
        }
    }

    @Override
    public boolean visit(AnnotationTypeDeclaration node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(AnnotationTypeMemberDeclaration node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(AnonymousClassDeclaration node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(ArrayAccess node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(ArrayCreation node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(ArrayInitializer node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(ArrayType node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(AssertStatement node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(Assignment node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(Block node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(BlockComment node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(BooleanLiteral node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(BreakStatement node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(CastExpression node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(CatchClause node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(CharacterLiteral node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(ClassInstanceCreation node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(CompilationUnit node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(ConditionalExpression node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(ConstructorInvocation node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(ContinueStatement node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(CreationReference node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(Dimension node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(DoStatement node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(EmptyStatement node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(EnhancedForStatement node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(EnumConstantDeclaration node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(EnumDeclaration node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(ExportsDirective node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(ExpressionMethodReference node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(ExpressionStatement node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(FieldAccess node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(FieldDeclaration node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(ForStatement node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(IfStatement node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(ImportDeclaration node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(InfixExpression node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(Initializer node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(InstanceofExpression node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(IntersectionType node) {
        addNodeToMap(node);
        return true;
    }


    @Override
    public boolean visit(LabeledStatement node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(LambdaExpression node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(LineComment node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(MarkerAnnotation node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(MemberRef node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(MemberValuePair node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(MethodRef node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(MethodRefParameter node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(MethodDeclaration node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(MethodInvocation node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(Modifier node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(ModuleDeclaration node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(ModuleModifier node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(NameQualifiedType node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(NormalAnnotation node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(NullLiteral node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(NumberLiteral node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(OpensDirective node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(PackageDeclaration node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(ParameterizedType node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(ParenthesizedExpression node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(PostfixExpression node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(PrefixExpression node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(ProvidesDirective node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(PrimitiveType node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(QualifiedName node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(QualifiedType node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(RequiresDirective node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(ReturnStatement node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(SimpleName node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(SimpleType node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(SingleMemberAnnotation node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(SingleVariableDeclaration node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(StringLiteral node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(SuperConstructorInvocation node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(SuperFieldAccess node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(SuperMethodInvocation node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(SuperMethodReference node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(SwitchCase node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(SwitchStatement node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(SynchronizedStatement node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(TagElement node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(TextElement node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(ThisExpression node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(ThrowStatement node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(TryStatement node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(TypeDeclaration node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(TypeDeclarationStatement node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(TypeLiteral node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(TypeMethodReference node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(TypeParameter node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(UnionType node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(UsesDirective node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(VariableDeclarationExpression node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(VariableDeclarationStatement node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(VariableDeclarationFragment node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(WhileStatement node) {
        addNodeToMap(node);
        return true;
    }

    @Override
    public boolean visit(WildcardType node) {
        addNodeToMap(node);
        return true;
    }

}
