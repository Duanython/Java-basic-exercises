package 实验七.content;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

/**
 * {@code Client} 代表了客户端发送请求任务。
 * <p><ul>
 *     <li>在学习了{@link Socket} 、{@link InetSocketAddress}
 *     并复习了已有的I/O流类的API及用法后基本且完备的完成了题设任务。</li>
 *     <li>缺点：与用户的交互逻辑有一点瑕疵。</li>
 *     <li>缺点：套接字输入输出流可进一步优化，例如可以使用
 *     {@link java.nio.channels.SocketChannel}提高用户交互性</li>
 *     <li>缺点：{@code Client} 不具有通用性，仅可为特定任务服务，
 *     该程序仅作为初学者练习使用，可实用性较差。</li>
 * </ul></p>
 * @author 段云飞
 * @since 2019-11-27
 */
public class Client implements Runnable{
    private String host;
    private int port;

    /**
     * Construct a client task with the
     * specified host name and port number.
     * @param host the specified host name
     * @param port specified port number.
     * @throws NullPointerException if {@code host} is null.
     * @throws IllegalArgumentException if the port parameter
     * is outside the range of valid port values
     */
    public Client(String host, int port) {
        Objects.requireNonNull(host);
        if(port < 0 || port > 65535)
            throw new IllegalArgumentException(
                    "the port parameter is outside the specified range of valid port values, which is between 0 and 65535, inclusive");
        this.host = host;
        this.port = port;
    }

    /**
     * Establish a socket connection with the specified server
     * address, open the input stream, prompt the user to input
     * ciphertext, and complete the verification function. If
     * the verification is successful, perform specific tasks.
     * Prompt the user if authentication fails.
     */
    @Override
    public void run() {
        String prompt = "PassWord:";
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port), 5000);
            try(var stdin = new Scanner(System.in);
                var socketin = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8);
                var socketout = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8)
            ){
                int caninputcount = 3;
                while (socketin.hasNextLine()){
                    String next = socketin.nextLine();
                    System.out.println(next);
                    switch (next){
                        case "Verifying Server!":
                        case "PassWord Wrong!":
                            if(caninputcount > 0){
                                System.out.print(prompt);
                                socketout.println(stdin.nextLine());
                                caninputcount--;
                            }
                            break;
                        case "Registration Successful!":
                            actionAfterLogoffSuccessful();
                        case "Illegal User!":break;
                        default:
                            System.out.println("Server Wrong!");
                    }
                }
            }
        } catch (IOException ignore) {
            System.out.println("连接超时，请重试。");
        }
    }

    private void actionAfterLogoffSuccessful(){
        //登录成功后暂定什么也不做
    }
}
