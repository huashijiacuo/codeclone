package shi.com;


import shi.com.common.MyLog;
import org.apache.log4j.Logger;

public class Addclass {

    public int addInt(int a, int b) {
        // 记录debug级别的信息
        logger.debug("This is debug message.");

        System.out.println("this is add function between integer");
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        int c = a + b;
        System.out.println("The result is c!");
        System.out.println("c = " + c);
        String str1 = "a = " + a;
        String str2 = "b = " + b;
        MyLog myLog = new MyLog();
        myLog.setStr1(str1);
        myLog.setStr2(str2);
        myLog.log();


        // 记录info级别的信息
        logger.info("This is info message.");
        // 记录error级别的信息
        logger.error("This is error message.");
        return c;
    }

}
