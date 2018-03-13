import org.eclipse.jdt.core.dom.ASTNode;
import org.junit.Test;
import structure.Main;
import structure.MyASTGenerator;
import structure.astnode.MyASTNode;
import structure.astnode.MyMethodNode;
import variableflow.GeneratorVariableDeclaration;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class AstVectorTest {

    @Test
    public void generatorVector() throws Exception {
        long startTime=System.currentTimeMillis();   //获取开始时间

        String dir = "/home/shi/Desktop/codeclone/dataset/selected";
        AstVector astVector = new AstVector();
        astVector.generatorVector(dir);

        long endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
    }

}