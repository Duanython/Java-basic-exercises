package 实验八;

import 实验八.content.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 工具类 {@code ExpEight} 的三个方法分别验证实验八的三个不同要求。
 * 主函数、b要求、c要求使用了包{@link 实验八.content}中的工具类
 * <p>实验八概述：
 * <ol>
 *     <li>主函数 {@link #main(String[])} 建立数据库连接并将其连接
 *     传递给实现a、b、c三个要求的方法，分别实现不同的要求。</li>
 *     <li> {@link #expEight_a_Verify(Connection)} 采用Java预备语句
 *     {@link PreparedStatement} 和批量更新{@link PreparedStatement#addBatch()} 、
 *     {@link PreparedStatement#executeBatch()} 完成数据插入操作。</li>
 *     <li> {@link #expEight_b_Verify(Connection)} 采用工具类方法
 *     {@link MySQL#printResultSet(ResultSet)} 打印查询结果。</li>
 *     <li> {@link #expEight_c_Verify(Connection)} 关闭自动提交，
 *     简单的构建JDBC事务编程，若sql语句执行失败，则调用
 *     {@link Connection#rollback()}进行事务回滚。</li>
 * </ol></p>
 * @author 段云飞
 * @since 2019-11-27
 */
public final class ExpEight {

    //Tool class does not need public constructor
    private ExpEight() {}

    /**
     * 数据库应用程序
     * 1）首先在数据库应用程序中创建数据库 Studentinfo，按照下表的结构在数
     * 据库中建立"student"表。
     * 字段名 Java数据类型 宽度 SQL数据类型
     *  Name String        10  Char(10)
     *  Sex  String        2   Char(2)
     *  Age  Int           3   Integer
     *  2）编写程序
     *  a) 向"student"表中填入若干数据记录；
     *  b)在"student"表中分别查询所有记录以及满足条件"age>18"的记录。
     *  c)编程创建学生成绩表，并进行数据插入、修改、删除、查询和成绩统计等操作。
     *  注：不限定数据库类型，
     * @param args useless param
     */
    public static void main(String[] args) {
        //1）操作：建立数据库Studentinfo详情请参见"数据库.png"
        //2) 编写程序完成指定操作见该类的3个验证方法。
        try(var conn = MySQL.getConnection("database.properties")){
            //expEight_a_Verify(conn);
            //expEight_b_Verify(conn);
            expEight_c_Verify(conn);
        } catch (SQLException e){
            for (var i : e)
                i.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * a) 向"studentinfo"表中填入若干数据记录；
     * <p><ul>
     *     <li>批量插入的数据源来自于前面实验的txt文件</li>
     *     <li>缺点：由于与SQL有关的方法均有 {@link SQLException} 因此
     *     异常处理块较散乱，结构性较差，很有可能有未考虑到的异常发生。</li>
     *     <li>缺点：对批量更新和事务提交的处理仍不熟练，需进一步学习。</li>
     * </ul></p>
     * @param conn a Connection to the specific database.
     */
    public static void expEight_a_Verify(Connection conn) {
        try {
            //先关闭自动提交模式，收集批量操作，提交该操作后
            //恢复最初的自动提交模式。
            boolean autocommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            //该try块用于处理所有与预备语句相关的问题。
            try(var insert = conn.prepareStatement("INSERT INTO student VALUES (?, ?, ?)")){
                Files.lines(Path.of("Student.txt"), StandardCharsets.UTF_8).forEach(
                        str -> {
                            var split = str.split(" ");
                            try {
                                //Student.txt文件的第一个字段为姓名
                                //第二个字段为年龄。性别均设置为"m"
                                insert.setString(1, split[0]);
                                insert.setString(2, "m");
                                insert.setInt(3, Integer.parseInt(split[1]));
                                insert.addBatch();
                            }catch (SQLException e){
                                for (var i : e)
                                    i.printStackTrace();
                            }
                        });
                insert.executeBatch();
                conn.commit();
            }
            conn.setAutoCommit(autocommit);
        } catch (SQLException | IOException e){
            e.printStackTrace();
        }
    }

    /**
     * b)在"studentinfo"表中分别查询所有记录以及满足条件"age>18"的记录。
     * <p><ul>
     *     <li>基本的完成了数据库表查询操作，并对异常进行遍历处理。</li>
     *     <li>使用{@link MySQL#printResultSet(ResultSet)} 方法打印数据表</li>
     * </ul></p>
     * @param conn a Connection to the specific database.
     */
    public static void expEight_b_Verify(Connection conn) {
        try(var stat = conn.createStatement()){
            MySQL.printResultSet(stat.executeQuery( "SELECT * FROM student WHERE age > 18"));
        }catch (SQLException e){
            for (var i : e)
                i.printStackTrace();
        }
    }

    /**
     * c)编程创建学生成绩表，并进行数据插入、修改、删除、查询和成绩统计等操作。
     * <p><ul>
     *     <li>批量插入的数据源来自于前面实验的txt文件。</li>
     *     <li>从数据库的的创建到删除均同属于同一事务，若执行失败则回滚。</li>
     *     <li>缺点：由于与SQL有关的方法均有 {@link SQLException} 因此
     *     异常处理块较散乱，结构性较差，很有可能有未考虑到的异常发生。</li>
     *     <li>缺点：对事务处理仍不熟练，需进一步练习和深入学习。</li>
     * </ul></p>
     * @param conn a Connection to the specific database.
     */
    public static void expEight_c_Verify(Connection conn) {
        try {
            boolean autocommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            try(var stat = conn.createStatement()){
                //表创建
                stat.executeUpdate("CREATE TABLE stugrade(name CHAR(10),grade NUMERIC(4,1));");
                //表数据插入
                Files.lines(Path.of("Student.txt"), StandardCharsets.UTF_8).forEach(
                        str -> {
                            var split = str.split(" ");
                            try {
                                //Student.txt文件的第一个字段为姓名，第六个字段为成绩
                                stat.executeUpdate(String.format("INSERT INTO stugrade VALUES ('%s', %s);", split[0], split[5]));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                );
                //表数据修改
                stat.executeUpdate("UPDATE stugrade SET grade=99.5 WHERE grade >= 60");
                //表数据删除
                stat.executeUpdate("DELETE FROM stugrade WHERE name = 'Faker'");
                //表数据查询
                MySQL.printResultSet(stat.executeQuery("SELECT * FROM stugrade WHERE grade >= 60"));
                //表成绩统计
                MySQL.printResultSet(stat.executeQuery("SELECT AVG(grade) FROM stugrade"));
                //表删除
                stat.executeUpdate("DROP TABLE stugrade");
            }catch (SQLException e){
                System.err.println("事务执行失败。");
                e.printStackTrace();
                try {
                    conn.rollback();
                }catch (SQLException e1){
                    System.err.println("事务回滚失败");
                    e1.printStackTrace();
                }
            }
            conn.setAutoCommit(autocommit);
        } catch (SQLException | IOException e){
            e.printStackTrace();
        }
    }
}