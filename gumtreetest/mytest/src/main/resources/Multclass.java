package shi.com;

import shi.com.common.MyLog;

public class Multclass {
    public int multInt(int x, int y) {
        System.out.println("this is add function between integer");
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        int z = x + y;
        System.out.println("The result is z!");
        System.out.println("z = " + x);
        String str1 = "x = " + x;
        String str2 = "y = " + y;
        MyLog myLog = new MyLog();
        myLog.setStr1(str1);
        myLog.setStr2(str2);
        myLog.log();
        return z;
    }

    public double multDouble(double a, double b) {
        System.out.println("this is add function between integer");
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        double c = a * b;
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

    public long multLong(long a, long b) {
        System.out.println("this is add function between integer");
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        long c = a * b;
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
