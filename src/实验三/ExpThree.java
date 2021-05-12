package 实验三;

import 实验三.content.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * 工具类 {@code ExpThree} 的三个方法分别验证实验三的三道小题。
 * 每一道小题均使用包 {@code 实验三.content} 中的多个类
 * <p>实验三概述：
 * <ol>
 *     <li> {@link ExceptionTest} 按实验要求将源代码粘贴并运行。</li>
 *     <li>{@link XYZException} , 学习了 {@link Throwable} 源码与API。 </li>
 *     <li>{@code StudentAveragable} 、{@code StudentAverageWithoutMaxandMIn}
 *     实现了接口 {@code Average} 并继承了 {@link 实验一.content.Student} </li>
 * </ol></p>
 * @author 段云飞
 * @since 2019-10-24
 */
public final class ExpThree {
    //Tool class does not need public constructor
    private ExpThree(){}

    /**
     * simulate JUnit's continuous execution of specified validation methods.
     * @param args useless param
     */
    public static void main(String[] args) {
        expThree_1_Verify();
        expThree_2_Verify();
        expThree_3_Verify();

    }

    /**
     * 、用 try-catch-finally 结构实现异常处理。编译并运行程序，写出程序运
     * 行结果。 ...（省略了实验指定的源代码）
     */
    public static void expThree_1_Verify(){
        ExceptionTest.main(null);
    }

    /**
     * 设计一个 Java 程序，自定义异常类，从命令行（键盘）输入一个字符
     * 串，如果该字符串值为“XYZ”，则抛出一个异常信息“This is a XYZ”，如果
     * 从命令行输入 ABC，则没有抛出异常。（只有 XYZ 和 ABC 两种输入）。
     */
    public static void expThree_2_Verify(){
        String message;
        var in = new Scanner(System.in);
        message = in.next();
        try {
            if (message.equals("XYZ")) throw new XYZException();
        }
        catch (XYZException e){
            System.err.println(e);
        }

    }

    /**
     * 声明一个 Average 接口，继续完善学生信息录入程序，其中约定求平均
     * 值的方法；声明多个类实现 Average 接口，分别给出求平均值的方法实现。
     * 例如，在一组数值中，一种算法是，全部数值相加后求平均值，另一种算法
     * 是，去掉一个最高分和一个最低分后，再将总分求平均等；使用键盘输入数据
     * 时，对于不能转换成数值的字符串进行异常处理.
     */
    public static void expThree_3_Verify() {
        /*我的实验一的学生信息录入程序已经非常完善了，足以完成实验三.3的奇怪要求了
          此方法在前者的基础之上简化部分代码并测试 Average 接口。不想用标准输入流了，
          很麻烦。改成文件输入流方便一丢丢。'Student.txt'在项目工作目录下。
         */
        var aver = new ArrayList<Average>();
        try (var in = new Scanner(new File("file/Student.txt"))) {
            while (in.hasNextLine()){
                var s = in.nextLine().split(" ");
                aver.add(new StudentAverageWithoutMaxandMIn(s[0], Integer.parseInt(s[1]),
                        LocalDate.of(Integer.parseInt(s[2]), Integer.parseInt(s[3]), Integer.parseInt(s[4])),
                        Double.parseDouble(s[5])));
            }
        } catch (FileNotFoundException | NumberFormatException e) {
            e.printStackTrace();
        }
        for (Average i :aver)
            System.out.println(i);
        System.out.println(StudentAverageWithoutMaxandMIn.aver());
    }
}
