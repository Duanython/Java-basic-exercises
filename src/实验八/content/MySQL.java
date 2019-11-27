package 实验八.content;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

/**
 * {@code MySQL} 类是题设的辅助工具类，仅由静态方法组成。
 * <p><ul>
 *     <li>{@code MySQL} 完成一些独立于题设的工具性任务，
 *     如数据库的连接，以特定格式读取整个查询结果。</li>
 *     <li>{@link #getConnection(String)} 使用指定的属性
 *     文件加载JDBC驱动并建立数据库文件，学习了{@link Properties}
 *     的部分API及用法。</li>
 *     <li>{@link #printResultSet(ResultSet)} 使用特定的
 *     格式打印结果集，因为使用{@link ResultSet#getMetaData()}
 *     方法，因此理论上可以输出任意查询结果集。</li>
 *     <li>缺点：由于作者写此工具类时仅仅学习了一天的JDBC
 *     编程，并且还没有学习XML文档、属性文件，所以实现此工
 *     具类很有可能是重复且低效的造轮子工作。</li>
 *     <li>缺点：{@code MySQL} 仅用于熟悉mysql连接时使用，
 *     并不具有通用性，仅适用于小型测试程序。Web或企业环境
 *     中部署JDBC应用时所需的JNDI、DataSource域、注解方式
 *     仍需深入学习。</li>
 * </ul></p>
 * @author 段云飞
 * @since 2019-11-27
 */
public final class MySQL {
    //Tool class does not need public constructor
    private MySQL(){}

    /**
     * Attempts to establish a connection to the given property file.
     * the property file normally at least should include a "user" 、"password"、
     * "drivers"、"host"、"port" and "database".
     * @param propsfilename a list of arbitrary string tag/value pairs as connection arguments;
     * @return a Connection to the URL
     * @throws SQLException if a database access error occurs or the url is null
     * @throws IOException if the driver cannot be located by the specified class loader
     * or the file does not exist, is a directory rather than a regular file, or for some
     * other reason cannot be opened for reading.
     */
    public static Connection getConnection(String propsfilename) throws SQLException, IOException {
        Properties props = new Properties();
        try(var in = new FileReader(propsfilename, StandardCharsets.UTF_8)){
            props.load(in);
        }
        try {
            Class.forName(props.getProperty("drivers"));
        } catch (ClassNotFoundException e) {
            throw new IOException("JDBC驱动加载失败",e);
        }
        String url = String.format("jdbc:mysql://%s:%s/%s?useSSL=false",
                props.getProperty("host"),
                props.getProperty("port"),
                props.getProperty("database")
        );
        return DriverManager.getConnection(url,
                props.getProperty("username"),
                props.getProperty("password"));
    }

    /**
     * Print the query result set in a specific format.
     * @param results the result set of a query.
     * @throws SQLException if a database access error occurs;
     * the result set concurrency is CONCUR_READ_ONLY or this
     * method is called on a closed result set
     * @throws NullPointerException if {@code results} is null.
     */
    public static void printResultSet(ResultSet results) throws SQLException {
        Objects.requireNonNull(results);
        var meta = results.getMetaData();
        int count = meta.getColumnCount();
        for (int i = 1; i <= count; i++){
            System.out.print(meta.getColumnLabel(i) + " ");
        }
        System.out.println();
        while (results.next()){
            for (int i = 1; i <= count; i++)
                System.out.print(results.getString(i) + " ");
            System.out.println();
        }
    }
}
