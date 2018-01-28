package structure;

import org.junit.Test;

import static org.junit.Assert.*;

public class DecompilerTest {
    @Test
    public void decompilerClass() throws Exception {
        String dgs = "1";
        String ori = "/home/shi/Desktop/codeDetectorCode/Similarity.class";
        String des = "/home/shi/Desktop/codeDetectorCode/decomp/source";

//        -dgs=1 Similarity.class decomp/source
        String [] opts = {dgs, ori, des};
        Decompiler.decompilerClass(ori, des);
    }

    @Test
    public void decompilerJar() throws Exception {
        String jar = "/home/shi/Desktop/codeDetectorCode/junit-4.12.jar";
        String des = "/home/shi/Desktop/codeDetectorCode/decomp";
        Decompiler.decompilerJar(jar, des);
    }

}