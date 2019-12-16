package 实验七.content;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

/**
 * {@code ServerHandler} 代表应用服务器，用
 * 于处理客户端发来的验证码，并回送验证结果。
 * <p><ul>
 * <li>在学习了{@link Socket} 、{@link java.net.ServerSocket}
 * 并复习了已有的I/O流类的API及用法后基本且完备的完成了题设任务。</li>
 * <li>缺点：套接字输入输出流可进一步优化，例如可以使用
 * {@link java.nio.channels.SocketChannel} 提高与客户端的交互性能</li>
 * <li>缺点：Web服务器与应用服务器模型参考自《深入理解计算机系统》
 * 的C语言Web服务器例题，并不熟悉当下较流行的Web服务器框架。</li>
 * <li>缺点：该程序仅作为初学者练习使用，可实用性较差。</li>
 * </ul></p>
 *
 * @author 段云飞
 * @since 2019-11-27
 */

public class ServerHandler implements Runnable {
    private Socket socket;
    private String verficode = "8192";

    /**
     * Construct a server-side processing task
     * with the specified socket.
     *
     * @param socket the specified socket
     * @throws NullPointerException if the params
     *                              socket and verficode is null.
     */
    public ServerHandler(Socket socket) {
        Objects.requireNonNull(socket);
        this.socket = socket;
    }

    /**
     * The task handler on the server side opens I/O stream and
     * receives the verification code sent by the client for verification.
     * If the verification is successful, specific actions will be executed.
     * There are three verification opportunities for ciphertext verification.
     * If all three verification fails, the user will be prompted and the output
     * stream will be half closed, waiting for the client to disconnect.
     */
    @Override
    public void run() {
        try (var in = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8);
             var out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8)) {
            out.println("Verifying Server!");
            int readcount = 0;
            boolean registsucc = false;
            while (readcount < 3 && in.hasNextLine()) {
                if (in.nextLine().trim().equals(this.verficode)) {
                    out.println("Registration Successful!");
                    registsucc = true;
                    break;
                }
                out.println("PassWord Wrong!");
                readcount++;
            }
            if (readcount >= 3)
                out.println("Illegal User!");
            else if (registsucc)
                actionsAfterSuccessfulVerification();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void actionsAfterSuccessfulVerification() {
        //验证成功后的动作暂定为啥也不做
    }
}