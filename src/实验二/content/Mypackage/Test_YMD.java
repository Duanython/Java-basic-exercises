package 实验二.content.Mypackage;

import java.util.*;
/**
 * {@code Test_YMD} 是实验给定的源代码中的类。
 * 用于学习 {@code package}.
 */
public class Test_YMD {
    private int year,month,day;

    public static void main(String[] arg3){}

    public Test_YMD(int y,int m,int d){
        year = y;
        month= (((m>=1) & (m<=12))?m:1);
        day= (((d>=1) & (d<=31))?d:1);
    }

    public Test_YMD(){
        this(0,0,0);
    }

    public static int thisyear(){
        return Calendar.getInstance().get(Calendar.YEAR);//返回当年的年份
    }

    public int year(){
        return year;
    }

    public String toString() {
        return year + "-" + month + "-" + day;
    }
}
