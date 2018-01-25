package shi.com;


import shi.com.common.MyLog;
import org.apache.log4j.Logger;

public class AddclassCopy {

    public int addInt(int x, int y) {
        // 记录debug级别的信息
        logger.debug("This is debug message.");

        System.out.println("this is add function between integer");
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        int z = x + y;
        System.out.println("The result is z!");
        System.out.println("z = " + z);
        String str1 = "x = " + x;
        String str2 = "y = " + y;
        MyLog myLog = new MyLog();
        myLog.setStr1(str1);
        myLog.setStr2(str2);
        myLog.log();


        // 记录info级别的信息
        logger.info("This is info message.");
        // 记录error级别的信息
        logger.error("This is error message.");

        String name = "name";
        String myName = "myName";

        return z;
    }

}
