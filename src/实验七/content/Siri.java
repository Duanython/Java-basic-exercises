package 实验七.content;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code Siri} 代表应用服务器，用于回复客户端发来的聊天
 * 内容，其中可回复的内容保存在一个线程安全的哈希表中。
 * <p><ul>
 * <li>服务器端的机器人Siri的回答储存在线程安全的哈希表中，可并发访问</li>
 * <li>缺点：该程序仅作为初学者练习使用，并不具有任何实用性。</li>
 * <li>缺点：机器人的聊天算法应使用机器学习的知识训练出一个
 * 模型供服务端机器人使用</li>
 * <li>Siri只有在回复结束聊天的对话“拜拜”时才会秒回，会让使用者想多的。</li>
 * </ul></p>
 *
 * @author 段云飞
 * @since 2019-12-16
 */
public class Siri implements Runnable {
    private static final Map<String, String> sessionMap = new ConcurrentHashMap<>(
            Map.of(
                    "Hi, Siri", "有什么事?",
                    "今天天气怎么样", "不怎么样",
                    "没事了", "哦哦哦哦哦哦哦",
                    "你想我了吗", "我每一天都在想你",
                    "我爱你", "我们结婚吧",
                    "你喜欢我吗", "我喜欢你好久了",
                    "你的名字", "你好，我叫Siri",
                    "", "哦"
            )
    );
    private Socket socket;

    public Siri(Socket socket) {
        Objects.requireNonNull(socket);
        this.socket = socket;
    }

    /**
     * The robot Siri establishes socket communication with the client,
     * receives the chat conversation sent by the client,
     * and replies the chat result to the client after 1.5 seconds
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try (var in = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8);
             var out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8)) {
            out.println("你终于来啦！");
            String request;
            while (in.hasNextLine() && !(request = in.nextLine()).equals("拜拜")) {
                Thread.sleep(1500);
                out.println(sessionMap.getOrDefault(request, "啊，我不想聊这个，下一个话题"));
            }
            out.println("拜拜");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
