import org.junit.Test;

import static org.junit.Assert.*;

public class MultAST2VectorTest {
    @Test
    public void multAst2Vector() throws Exception {
        String dir = "/home/shi/Desktop/codeclone/dataset/selected";
        String output = "/home/shi/Desktop/codeclone/dataset";
        new MultAST2Vector().multAst2Vector(dir,output);
    }

}