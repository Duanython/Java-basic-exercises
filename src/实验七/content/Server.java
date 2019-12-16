package 实验七.content;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.function.Function;

/**
 * {@link Server} 代表 Web服务器，支持多客户端连接：
 * 等待并接收客户端套接字，然后将服务委托给应用服务器，
 * 随后阻塞以等待下一个客户端套接字。
 * <p><ul>
 * <li> {@code Server} 使用线程池技术完成多客户端的并发访问，
 * 并能够响应Web服务器端其他线程的中断请求。</li>
 * <li>缺点：{@code Server} 该程序仅作为初学者练习使用，基本完成了
 * 服务器应具有的功能，但这种实现方法并不能满足高性能服务器的要求，
 * 仍需进一步学习 {@code java.nio} 包中的类完成Web服务器高并发的需求。</li>
 * </ul></p>
 *
 * @author 段云飞
 * @since 2019-11-27
 */
public class Server implements Runnable {
    private int maxconcurrent;
    private int port;
    private Function<Socket, Runnable> generator;
    private Thread thisThread = Thread.currentThread();

    /**
     * Construct a server task with the specified
     * maximum number of servers accessible and port number
     * and generate the specified Runnable, which is used to
     * communicate with the client after receiving the socket
     * sent by the client.
     *
     * @param maxconcurrent Maximum number of servers accessible
     *                      If the parameter is 0, the default value
     *                      50 will be taken
     * @param port          specified port number
     * @param generator     a function which produces a new Runnable
     *                      by the specified Socket.
     * @throws IllegalArgumentException if the {@code port} parameter
     *                                  is outside the range of valid
     *                                  port values or {@code maxconcurrent}
     *                                  less than zero.
     */
    public Server(int maxconcurrent, int port, Function<Socket, Runnable> generator) {
        if (maxconcurrent < 0 || port < 0 || port > 65535)
            throw new IllegalArgumentException("paramters should be non negative number.");
        Objects.requireNonNull(generator);
        this.maxconcurrent = maxconcurrent == 0 ? 50 : maxconcurrent;
        this.port = port;
        this.generator = generator;
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
        try (ServerSocket s = new ServerSocket(port)) {
            int i = 1;
            while (!thisThread.isInterrupted()) {
                Socket incoming = s.accept();
                System.out.println("Spawning " + i);
                try {
                    executor.execute(generator.apply(incoming));
                } catch (Throwable exefail) {
                    System.out.println("应用服务器执行时出现问题");
                    exefail.printStackTrace();
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
