package 实验二.content;

import 实验二.content.Mypackage.Test_YMD;

/**
 * {@code YMD_2} 是实验给定的源代码中的类。
 * 用于测试 {@code Test_YMD}
 */
public class YMD_2 {
    private String name;
    private Test_YMD birth;

    /**
     * Experiment with the main method in the given source code.
     * @param args useless param
     */
    public static void main(String[] args) {
        YMD_2 a = new YMD_2("张弛",1990, 1,11);
        a.output();
    }
    /**
     * Experiment with constructor in the given source code.
     */
    public YMD_2(String n1, Test_YMD d1) {
        name = n1;
        birth = d1;
    }
    /**
     * Experiment with  another constructor in the given source code.
     */
    public YMD_2(String n1, int y, int m, int d){
        this(n1, new Test_YMD(y, m, d)); //初始化变量与对象
    }
    /**
     * Experiment with method in the given source code.
     */
    public int age(){  //计算年龄
        return Test_YMD.thisyear() - birth.year();
                       //返回当前年与出生年的差即年龄
    }
    /**
     * Experiment with method in the given source code.
     */
    public void output(){
        System.out.println("姓名：" + name);
        System.out.println("出生年月：" + birth.toString());
        System.out.println("今年年龄" + age());
    }
}
