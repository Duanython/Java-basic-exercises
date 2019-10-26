package 实验一;

import 实验一.content.*;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 * 工具类 {@code ExpOne} 的五个方法分别验证实验一的五道小题。
 * 每一道小题均使用包 {@code 实验一.content} 中的一个类
 * <p>实验一概述：
 * <ol>
 *     <li>{@code Student} 使用了一些程序设计技巧实现了平均分的惰性求值，减少使用除法操作的次数。</li>
 *     <li>泛型工具类 {@code ArraySortAndSearchWrapper} 包裹了实验要求的两个方法，学习了Comparator接口</li>
 *     <li>工具类 {@code PrimeNumber} 使用线程池及其相关类完成并行计算任务。</li>
 *     <li>工具类 {@code PerfectNumber} 使用Fork/Join框架提供的类完成计算密集型任务</li>
 *     <li>工具类 {@code FeasibleSoluSet} 使用静态内部类 {@code Tuple} 与 {@code List<T} 完成API设计工作</li>
 * </ol></p>
 * @author 段云飞
 * @since 2019-10-18
 */
public final class ExpOne {
    //Tool class does not need public constructor
    private ExpOne(){}

    /**
     * simulate JUnit's continuous execution of specified validation methods.
     * @param args useless param
     */
    public static void main(String[] args) {
        expOne_1_Verify();
        expOne_2_Verify();
        expOne_3_Verify();
        expOne_4_Verify();
        expOne_5_Verify();
    }

    /**
     * 编写一个 Java 应用程序，用户从键盘输入十名学生的信息，至少包括
     * 姓名、年龄、出生年月日、java 课程实验成绩，成绩使用浮点数，年龄使用整
     * 型，程序将输出年龄、java 课程实验成绩的平均值。
     */
    public static void expOne_1_Verify(){
        final int STUCOUNT = 10;
        ArrayList<Student> stus = new ArrayList<>(STUCOUNT);
        System.out.println("输入格式：姓名 年龄 出生年 月 日 Java课程实验成绩");
        try (Scanner in = new Scanner(System.in)){
            for (int i = 0; i < STUCOUNT; i++){
                try {
                    String[] s = in.nextLine().split(" ");
                    if (s.length < 6) throw new IOException("数据个数少于6个");
                    LocalDate d = LocalDate.of(
                            Integer.parseInt(s[2]),
                            Integer.parseInt(s[3]),
                            Integer.parseInt(s[4]));
                    stus.add(new Student(s[0], Integer.parseInt(s[1]), d, Double.parseDouble(s[5])));
                } catch (IOException | IllegalArgumentException | DateTimeException e) {
                    System.out.println(e + "  请重新输入:");
                    i--;
                }
            }
        }
        System.out.println("Java课程实验平均成绩：" + Student.getjavaAverExpGrade());
        System.out.println("学生年龄:");
        for (Student i : stus)
            System.out.print(i.getage() + " ");
    }

    /**
     * 使用 Arrays 类实现数组排序：使用 java.util 包中的 Arrays 类的类方法
     * public static void sort(double a[])可以把参数 a 指定的 double 类型数组按升序排
     * 序；public static void sort(double a[], int start , int end)可以把参数 a 指定的 double
     * 类型数组中从位置 start 到 end 位置的值按升序排序。
     * 给定数组 int a[]={12,34,9,-23,45,6,90,123,19,45,34}; 从键盘读入一个整数，
     * 使用折半查找判断该整数是否在这个数组中，并将结果输出
     */
    public static void expOne_2_Verify(){
        Integer[] a = {12,34,9,-23,45,6,90,123,19,45,34};
        int key;
        try(var in = new Scanner(System.in)){ key = in.nextInt(); }
        key = ArraySortAndSearchWrapper.sortandsearch(a, 0, a.length, key, Comparator.naturalOrder());
        if (key >= 0) System.out.println("index = " + key);
        else System.out.println("Not Find 404 ,index should be insert into " + (-key - 1));
    }

    /**
     * 输出 100~200 之间的所有素数
     */
    public static void expOne_3_Verify(){
        long from = 100;
        long to = 200000;
        try {
            var result = PrimeNumber.parallelCompute(from, to);
            for (var prime : result){
                if (prime > 200) break;
                System.out.print(prime + " ");
            }
            System.out.printf("\nThere are %d prime numbers between %d and %d",
                    result.size(), from, to);
        }catch (ExecutionException e){
            System.out.println("compute error,please execute again.");
        }
    }

    /**
     * 采用 for 循环求 1 至 1000 之内的所有“完全数”。所谓“完全数”是
     * 指一个数，恰好等于它的因子之和。例如，6 是一个完全数，因为 6 的因子为 1、
     * 2、3，而 6＝1+2+3。
     */
    public static void expOne_4_Verify(){
        long from = 1;
        long to = 1000;
        var results = PerfectNumber.parallelCompute(from, to);
        System.out.println(results);
    }

    /**
     * 已知 XYZ+YZZ=532，其中 X、Y 和 Z 为数字，编程求出 X，Y 和 Z
     * 的值。
     */
    public static void expOne_5_Verify(){
        var results = FeasibleSoluSet.compute();
        for (var result : results)
            System.out.println(result);
    }
}

