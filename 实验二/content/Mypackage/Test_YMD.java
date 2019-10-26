package 实验二.content.Mypackage;

import java.util.*;
/**
 * {@code Test_YMD} 是实验给定的源代码中的类。
 * 用于学习 {@code package}.
 */
public class Test_YMD {
    private int year,month,day;
    /**
     * Experiment with the main method in the given source code.
     * @param arg3 useless param
     */
    public static void main(String[] arg3){}
    /**
     * Experiment with constructor in the given source code.
     */
    public Test_YMD(int y,int m,int d){
        year = y;
        month= (((m>=1) & (m<=12))?m:1);
        day= (((d>=1) & (d<=31))?d:1);
    }
    /**
     * Experiment with  another constructor in the given source code.
     */
    public Test_YMD(){
        this(0,0,0);
    }
    /**
     * Experiment with  static method in the given source code.
     */
    public static int thisyear(){
        return Calendar.getInstance().get(Calendar.YEAR);//返回当年的年份
    }
    /**
     * Experiment with getter in the given source code.
     */
    public int year(){
        return year;
    }
    /**
     * Experiment with  method in the given source code.
     */
    public String toString() {
        return year + "-" + month + "-" + day;
    }
}
