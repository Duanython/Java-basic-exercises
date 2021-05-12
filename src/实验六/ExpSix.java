package 实验六;

import 实验六.content.SecretExample;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

/**
 * 工具类 {@code ExpSix} 的两个方法分别验证实验六的两道小题。
 * 第一小题使用包 {@code 实验六.content} 中的类
 * <p>实验六概述：
 * <ol>
 *     <li> {@link SecretExample} 按实验要求将源代码粘贴、填空、运行。</li>
 *     <li> 第二小题不需要任何的自定义类，在学习 {@link Files}、{@link Path}、
 *     {@link java.nio.charset.Charset}、{@link StandardCharsets} 以及I/O家
 *     族的各个类的API和用法后，可轻松的解决题设问题。</li>
 * </ol></p>
 *
 * @author 段云飞
 * @since 2019-11-20
 */

public final class ExpSix {
    //Tool class does not need public constructor
    private ExpSix() {
    }

    /**
     * simulate JUnit's continuous execution of specified validation methods.
     *
     * @param args useless param
     */
    public static void main(String[] args) {
        expSix_1_Verify();
        expSix_2_Verify();
    }

    /**
     * 编写一个 Java 应用程序，将已存在的扩展名为.txt 的文本文件加密后存入
     * 另一个文本文件中。按模板要求，将【代码 1】~【代码 7】替换为 Java 程序代
     * 码。...（省略了实验指定的源代码）
     */
    public static void expSix_1_Verify() {
        SecretExample.main(null);
    }

    /**
     * 编程完成下列功能：
     * 1）首先建立两个文件 myfiel.txt 和 myfile2.txt。
     * 2）从标准设备中输入多名学生信息，如学号，姓名，专业，班级，家庭住
     * 址等，待输入"bye"时结束，将输入内容保存在 myfile1.txt 文件中。
     * 3）再将 myfile1.txt 文件中内容拷贝到 myfile2.txt。
     */
    public static void expSix_2_Verify() {
        try {
            Path pathone = Path.of("file/myfile1.txt");
            Path pathtwo = Path.of("file/myfile2.txt");
            if (Files.notExists(pathone))
                Files.createFile(pathone);
            if (Files.notExists(pathtwo))
                Files.createFile(pathtwo);
            System.out.println("Hello! Enter BYE to exit.");
            try (var in = new Scanner(System.in, StandardCharsets.UTF_8);
                 var buffer = new BufferedWriter(
                         new FileWriter(pathone.toFile(), StandardCharsets.UTF_8))) {
                while (in.hasNextLine()) {
                    String line = in.nextLine();
                    if (line.trim().equals("BYE"))
                        break;
                    buffer.write(line + "\r\n");
                }
            }
            Files.copy(pathone, pathtwo, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}