package 实验七.content;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * {@code Server} 代表了Web服务器接收请求并执行
 * <p><ul>
 *     <li>{@code Server} 的实现简洁并功能单一：仅仅接受并捕获客户端
 *     套接字地址，并将它转交给专门的服务处理类，代表了客户端-服务器端
 *     模型的通用实现方法。</li>
 *     <li>{@code Server} 使用线程池技术完成多客户端的并发访问，并响应中断请求。</li>
 *     <li>缺点：服务器端生成密文的功能仅作为示例使用，没有实用性。</li>
 *     <li>缺点：{@code Server} 该程序仅作为初学者练习使用，基本完成了
 *     服务器应具有的功能，但这种实现方法并不能满足高性能服务器的要求，
 *     仍需进一步学习 {@code java.nio} 包中的类完成服务器高并发的需求。</li>
 * </ul></p>
 * @author 段云飞
 * @since 2019-11-27
 */
public class Server implements Runnable {
    private int maxconcurrent;
    private int port;
    private Thread thisThread = Thread.currentThread();
    /**
     * Construct a server task with the specified
     * maximum number of servers accessible and port number.
     * @param maxconcurrent Maximum number of servers accessible
     * If the parameter is 0, the default value 50 will be taken
     * @param port specified port number
     * @throws IllegalArgumentException if the {@code port} parameter
     * is outside the range of valid port values or {@code maxconcurrent}
     * less than zero.
     */
    public Server(int maxconcurrent, int port) {
        if(maxconcurrent < 0 || port < 0 || port > 65535)
            throw new IllegalArgumentException("paramters should be non negative number.");
        this.maxconcurrent = maxconcurrent == 0 ? 50 : maxconcurrent;
        this.port = port;
    }

    /**
     * The server builds a thread pool with the specific value capacity and
     * establishes a server-side socket. If the thread executing the run method
     * is not interrupted, it receives the socket connection request from
     * the client,generates the ciphertext,and concurrently executes the
     * server-side request processing task.
     */
    @Override
    public void run() {
        var executor = Executors.newFixedThreadPool(maxconcurrent);
        try (ServerSocket s = new ServerSocket(port))
        {
            int i = 1;
            while (!thisThread.isInterrupted())
            {
                Socket incoming = s.accept();
                System.out.println("Spawning " + i);
                executor.execute(new ServerHandler(incoming, spawnVerificationCode()));
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    private String spawnVerificationCode(){
        return "8192";
    }
}
