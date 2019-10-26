package 实验五;

import 实验五.content.ThreadExample;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 工具类 {@code ExpFive} 的三个方法分别验证实验五的三道小题。
 * 第一小题和第三小题均使用包 {@code 实验五.content} 中的多个类
 * <p>实验二概述：
 * <ol>
 *     <li>第一小题不需要使用任何自定义类，采用流操作生成数据集
 *     labda表达式、FutuerTask类及Thread类实现多线程操作。</li>
 *     <li> {@code ThreadExample}、{@code Rabit}、
 *     {@code Tortoise} 按实验要求将源代码粘贴并运行。</li>
 *     <li> </li>
 * </ol></p>
 * @author 段云飞
 * @since 2019-10-24
 */
public final class ExpFive {
    //Tool class does not need public constructor
    private ExpFive() {}
    /**
     * simulate JUnit's continuous execution of specified validation methods.
     * @param args useless param
     */
    public static void main(String[] args) {
        expFive_1_Verify();
        expFive_2_Verify();
        expFive_3_Verify();
    }

    /**
     * 编写一个有两个线程的程序，第一个线程用来计算1~100之间的偶数及个数，
     * 第二个线程用来计算1-100之间的偶数及个数。
     */
    public static void expFive_1_Verify() {
        //The first time I use class Stream except System.in and System.out,
        //I can't find a way for for two threads to operate a stream at the same time.
        var tophundred = Stream.iterate(1, i -> i + 1).limit(100).collect(Collectors.toList());
        Predicate<Integer> isoddnum = i -> i % 2 == 1;
        var first = new FutureTask<>(() -> tophundred.stream().filter(isoddnum).count());
        var second = new FutureTask<>(() -> tophundred.stream().filter(isoddnum.negate()).count());
        new Thread(first).start();
        new Thread(second).start();
        try {
            System.out.printf("There are %d odd numbers and %d even numbers",
                    first.get(), second.get());
        } catch (InterruptedException | ExecutionException ignored) {
        }
    }

    /**
     * 编写一个Java应用程序，在主线程中再创建两个线程，要求线程经历四种
     * 状态：新建，运行、中断和死亡。按模板要求，将【代码1】~【代码8】替换为
     * Java程序代码。  ...（省略了实验指定的源代码）
     */
    public static void expFive_2_Verify() {
        ThreadExample.main(null);
    }

    /**
     * 编写Java应用程序模拟5个人排队买票。售票员只有1张五元的钱，电影
     * 票五元钱一张。假设5个人的名字及排队顺序是：赵、钱、孙、李、周。“赵”
     * 拿1张二十元的人民币买2张票，“钱”拿1张二十元的人民币买1张票，“孙”1
     * 张十元的人民币买1张票，“李”拿1张十元的人民币买2张票，“周”拿1张五元
     * 的人民币买1张票。
     * 要求售票员按如下规则找赎：
     * （1）二十元买1张票，找零：1张十元；不许找零2张五元。
     * （2）二十元买1张票，找零：1张十元，1张五元；不许找零3张五元。
     * （3）十元买一张票，找零1张五元。
     */
    public static void expFive_3_Verify() {

    }

}