package util;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.IMethodBinding;
/**
 * 检查工具类
 *
 * @author Chen Zhe
 *
 */

/*import cn.net.cobot.parsers.javaparser.core.dom.ASTNode;
import cn.net.cobot.parsers.javaparser.core.dom.IBinding;
import cn.net.cobot.parsers.javaparser.core.dom.MethodBinding;
import cn.net.cobot.parsers.javaparser.core.dom.Name;
import cn.net.cobot.parsers.javaparser.core.dom.QualifiedName;
import cn.net.cobot.parsers.javaparser.core.dom.SimpleName;
import cn.net.cobot.parsers.javaparser.core.dom.TypeBinding;
import cn.net.cobot.parsers.javaparser.core.dom.VariableBinding;
import cn.net.cobot.parsers.javaparser.core.dom.VariableDeclarationFragment;*/

public class JavaCheckerUtility {
	/**
	 * 获取定义点
	 *
	 * @author Chen Zhe
	 * @param binding
	 * @return 返回一个binding的定义点ASTNode
	 */
	public static ASTNode getDeclaration(IBinding binding, ASTNode node) {
	    CompilationUnit cu = (CompilationUnit) node.getRoot();
		if (binding instanceof IMethodBinding) {
			IMethodBinding bind = (IMethodBinding)binding;
            ASTNode declaration = cu.findDeclaringNode(bind);
			return declaration;
		} else if (binding instanceof ITypeBinding) {
			ITypeBinding bind = (ITypeBinding)binding;
			ASTNode declaration = cu.findDeclaringNode(bind);
			return declaration;
		} else if (binding instanceof IVariableBinding) {
			IVariableBinding bind = (IVariableBinding)binding;
			ASTNode declaration = cu.findDeclaringNode(bind);
			if (declaration instanceof VariableDeclarationFragment) {
				ASTNode defNode = ((VariableDeclarationFragment)declaration).getParent();
				return defNode;
			}
			return declaration;
		}
		return null;
	}

	/**
	 * 获取定义点
	 *
	 * @author Chen Zhe
	 * @param Name name
	 * @return 返回一个name的定义点ASTNode
	 */
	public static ASTNode getDeclaration(Name name) {
		if (name instanceof SimpleName) {
			return getDeclaration(((SimpleName)name).resolveBinding(), name);
		} else if (name instanceof QualifiedName) {
			return getDeclaration(((QualifiedName)name).getName().resolveBinding(), name);
		}
		return null;
	}

}
