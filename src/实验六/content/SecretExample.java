package 实验六.content;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * {@code SecretExample} 是实验给定的源代码中的类。
 * 用于学习文件IO的知识与简单加密过程。
 */
public class SecretExample {
    public static void main(String[] args) {
        File fileone = new File("hello.txt");
        File filetwo = new File("hello.secret");
        char b[] = new char[100];
        try {
            FileReader in = new FileReader(fileone); //【代码 1】创建指向 fileone 的字符输入流
            FileWriter out = new FileWriter(filetwo); //【代码 2】创建指向 fileontwo 的字符输出流
            int n = -1;
            while ((n = in.read(b)) != -1) {
                for (int i = 0; i < n; i++) {
                    b[i] = (char) (b[i] ^ 'a');
                }
                out.write(b, 0, n); //【代码 3】out 将数组 b 的前 n 单元写到文件
            }
            out.close(); //【代码 4】out 关闭
            in = new FileReader(filetwo);  //【代码 5】创建指向 fileontwo 的字符输入流
            System.out.println("加密后的文件内容:");
            n = in.read(b);
            while (n != -1) {
                String str = new String(b, 0, n);
                System.out.println("dfjakfjd" + str);
                n = in.read(b);
            }
            in = new FileReader(filetwo);
            System.out.println("解密后的文件内容:");
            while ((n = in.read(b)) != -1) {
                for (int i = 0; i < n; i++) {
                    b[i] = (char) (b[i] ^ 'a');
                }
                String str = new String(b, 0, n);
                System.out.println(str);
            }
            in.close(); //【代码 6】in 关闭
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}