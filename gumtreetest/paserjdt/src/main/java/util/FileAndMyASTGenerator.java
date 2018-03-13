package util;

import structure.MyASTGenerator;

public class FileAndMyASTGenerator {

    public String fileName;

    public MyASTGenerator myASTGenerator;

    public FileAndMyASTGenerator(String fileName, MyASTGenerator myASTGenerator) {
        this.fileName = fileName;
        this.myASTGenerator = myASTGenerator;
    }
}
