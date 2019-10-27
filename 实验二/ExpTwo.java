package 实验二;

import 实验二.content.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * 工具类 {@code ExpTwo} 的五个方法分别验证实验二的五道小题。
 * 每一道小题均使用包 {@code 实验二.content} 中的多个类
 * <p>实验二概述：
 * <ol>
 *     <li>{@code MyDate} 的静态方法 {@link MyDate#of(int, int, int)}模拟
 *     {@link LocalDate#of(int, int, int)} 并学习了{@link java.time.Month} 、
 *     {@link java.time.Year} </li>
 *
 *     <li>实验要求基类 {@code Person} 及派生类 {@code Student}具有成员方法
 *     “开户、存款、取款、查询（余额、明细）、销户”等操作是非常低级的OOP程序设计错误，
 *     折中办法是认为 {@code Person} 持有 {@code Card} 时具有上述一系列动作。
 *     设计其实现时采用了静态工厂模式，遵循“复合优于继承”的原则，抽象产品 {@code Card}
 *     (模拟现实世界中的消费型卡)、具体产品 {@code CampusCardService.CampusCard}
 *     (模拟现实世界的校园一卡通)、具体工厂 {@code CampusCardService} 模拟现实世界中的
 *     校园一卡通服务中心，类的实现模拟了Fork/Join框架的部分设计思想。</li>
 *
 *     <li>基类 {@code Vehicle} 及其子类 {@code Car}、{@code Truck}
 *     巩固了类的继承基础知识。</li>
 *
 *     <li>实验要求" {@code Rectangle} 、{@code Circle}、{@code Triangle}继承 {@code Coordinate} "
 *     不论是用于数学计算还是二维图形描述，提出这一要求的人都像是一个OOP初学者，应采用“复合优于继承”原则解决问题。
 *     三个类实现了{@code Shape} 以及数据合法性验证，{@code Triangle}三边关系验证。</li>
 *
 *     <li>{@code YMD_2}、{@code Mypackage.Test_YMD} 按实验要求将源代码粘贴并运行。</li>
 * </ol></p>
 * @author 段云飞
 * @since 2019-10-24
 */
public final class ExpTwo {
    //Tool class does not need public constructor
    private ExpTwo(){}
    /**
     * simulate JUnit's continuous execution of specified validation methods.
     * @param args useless param
     */
    public static void main(String[] args) {
        //expTwo_1_Verify();
        expTwo_2_Verify();
        //expTwo_3_Verify();
        //expTwo_4_Verify();
        //expTwo_5_Verify();
    }

    /**
     * 编写 MyDate 类，完善上次实验中的人员信息录入，实现日期合法性判
     * 断，包括大小月和闰年。
     */
    public static void expTwo_1_Verify(){
        int year, month, dayOfMonth;
        try(var in = new Scanner(System.in)){
            year = in.nextInt();
            month = in.nextInt();
            dayOfMonth = in.nextInt();
        }
        try {
            MyDate myDate = MyDate.of(year, month, dayOfMonth);
        }catch (DateTimeException e){
            System.out.println("illegal Date:\n" + e);
        }
    }

    /**
     * 声明一个 Person 类和派生类 Student，成员变量包括学号、姓名、入学
     * 时间、身份证号、学分绩点等信息，成员方法包括开户、存款、取款、查询（余
     * 额、明细）、销户等操作
     */
    public static void expTwo_2_Verify(){
        Person Mr_Duan = new Student("Mr.Duan",
                "999999999911229999", LocalDate.of(2019,10,24),
                3.99);
        //模拟现实操作：开通校园一卡通 -> 充值一万元
        Mr_Duan.openCard(10000);
        int i = 0;
        try {
            // -> 每次消费998
            while (true)
                Mr_Duan.drawMoney(998);
        }catch (MoneyRunLowException e){
            // -> 余额不足时充值 -> 打印消费明细单
            Mr_Duan.reCharge(1000);
            Mr_Duan.printConsumeDetails();
        }
        // -> 注销校园一卡通
        Mr_Duan.logoffCard();
    }

    /**
     * 设计一个汽车类 Vehicle，包含的属性有车轮个数（wheels）和车重
     * （weight）。小车类 Car 是 Vehicle 类的子类，其中包含的属性有载人数（loader）。
     * 卡车类（Truck 类）是 Car 类的子类，其中包含的属性有载重量（payload）。每
     * 一个类都有相关数据的输出
     */
    public static void expTwo_3_Verify(){
        Vehicle[] vehicles = {
                new Vehicle(4,15000),
                new Car(4,8000,4),
                new Truck(4,18000,2,50000)
        };
        for (Vehicle i : vehicles)
            System.out.println(String.format("汽车类型：%s 车轮个数：%d, 车重：%d",
                    i.getClass(), i.getWheels(),i.getWeight()));
    }

    /**
     * 定义接口 Shape 及其抽象方法 getArea()和 getPerimeter()用于计算图形和
     * 面积和周长。定义类 Rectangle(矩形)、类 Circle(圆形)、类 Triangle(三角形)，要
     * 求这些类继承点类 Coordinates()并实现接口的抽象方法。
     * 提示：...（省略了实验指定的源代码）
     */
    public static void expTwo_4_Verify(){
        Shape[] shapes = {
                new Rectangle(4,8),
                new Circle(5),
                new Triangle(3, 4, 5)
        };
        for (Shape i : shapes)
            System.out.println(String.format("图形形状：%s 图形面积：%g, 图形周长：%g",
                    i.getClass(), i.getArea(),i.getPerimeter()));
    }

    /**
     * 包的定义和使用
     *  创建自定义包 Mypackage
     * 在存放源程序的文件夹中建立一个子文件夹 Mypackage。例如，在“E:\java\
     * 程序”文件夹之中创建一个与包同名的子文件夹 Mypackage（E:\java\程序
     * \Mypackage），并将编译过的 class 文件放入该文件夹中。注意：包名与文件夹
     * 名大小写要一致。再添加环境变量 classpath 的路径，例如：E:\j2sdk1.4.2_01\lib;
     * E:\java\程序
     *  在包中创建类
     * YMD.java 程序功能：在源程序中，首先声明使用的包名 Mypackage，然
     * 后创建 YMD 类，该类具有计算今年的年份并输出一个带有年月日的字符串的
     * 功能。源代码如下: ...（省略了实验指定的源代码）
     * 编译 Test_YMD.java 文件，然后将 Test_YMD.class 文件存放到 Mypackage
     * 文件夹中。
     *  编写使用包 Mypackage 中 Test_YMD 类的程序
     * YMD_2.java 程序功能：给定某人姓名与出生日期，计算该人年龄，并输出
     * 该人姓名，年龄，出生日期。程序使用了 Test_YMD 的方法来计算年龄。源代
     * 码如下： ...（省略了实验指定的源代码）
     * 编译并运行程序。
     */
    public static void expTwo_5_Verify(){
        YMD_2.main(null);
    }
}
