import org.junit.Test;

import static org.junit.Assert.*;

public class GetFilePathTest {
    @Test
    public void getPath() throws Exception {
        String dir = "/home/shi/Desktop/codeclone/dataset/selected";
        GetFilePath.writePath(dir);
    }

}