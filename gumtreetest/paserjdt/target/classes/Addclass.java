package shi.com;


import shi.com.common.MyLog;
import org.apache.log4j.Logger;

public class Addclass {
    private static final Logger logger = Logger.getLogger(Addclass.class);

    public int addInt(int a, int b) {
        // 记录debug级别的信息
        logger.debug("This is debug message.");
        int m, n = 4;
        int p = 3;
        int q;
        Addclass addclass = new Addclass();
        Addclass addclass1;
        new Addclass()
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

    public double addDouble(double a, double b) {
        System.out.println("this is add function between integer");
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        double c = a + b;
        System.out.println("The result is c!");
        System.out.println("c = " + c);
        String str1 = "a = " + a;
        String str2 = "b = " + b;
        MyLog myLog = new MyLog();
        myLog.setStr1(str1);
        myLog.setStr2(str2);
        myLog.log();
        return  c;
    }

    public long addLong(long a, long b) {
        System.out.println("this is add function between integer");
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        long c = a + b;
        System.out.println("The result is c!");
        System.out.println("c = " + c);
        String str1 = "a = " + a;
        String str2 = "b = " + b;
        MyLog myLog = new MyLog();
        myLog.setStr1(str1);
        myLog.setStr2(str2);
        myLog.log();
        return  c;
    }
}
