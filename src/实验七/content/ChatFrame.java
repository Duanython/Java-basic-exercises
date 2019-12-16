package 实验七.content;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

/**
 * {@code ChatFrame} 代表客户端，用于与Siri聊天，它的
 * 可视化界面由已经被淘汰的swing和awt技术完成。
 * <p><ul>
 * <li>采用多线程技术解决了“客户端在等待服务器端回复时的阻塞态”的问题，
 * 现在可以边等待服务器端的消息，边发送消息。</li>
 * <li>缺点：上文的那个优点在规范上需使用 {@link EventQueue#invokeLater(Runnable)}
 * 完成耗时任务，但实际使用时并不奏效，在deadline前并未发现原因。</li>
 * <li>缺点：因为有.NET可视化界面的基础且Java的可视化技术早已被淘汰，
 * 在考试中根本不考，因此在编写程序时并没有遵守Java GUI编程的规范。</li>
 * <li>缺点：由于时间与精力有限，UI界面较丑，没有设计与添加icon图标</li>
 * <li>缺点：没有添加按Enter键自动发送消息的事件，因为实现该事件的代码比C#繁琐</li>
 * </ul></p>
 *
 * @author 段云飞
 * @since 2019-12-16
 */
public class ChatFrame extends JFrame implements Runnable {
    private final JTextArea text_receiver = new JTextArea();
    private InetSocketAddress endpoint;
    private Socket socket = new Socket();
    private JButton sendbutton = new JButton("发送");
    private JTextField text_sender = new JTextField(16);

    /**
     * Use a specific IP address to construct a form with a
     * specific UI interface, and initialize the settings of
     * some components, which are independent of socket connection.
     * At this time, socket communication with the server is not
     * established, only the specified IP address is saved.
     *
     * @param endpoint the specified IP address
     * @throws HeadlessException if GraphicsEnvironment.isHeadless()
     *                           returns true.
     * @see GraphicsEnvironment#isHeadless
     * @see Component#setSize
     * @see Component#setVisible
     * @see JComponent#getDefaultLocale
     */
    public ChatFrame(InetSocketAddress endpoint) throws HeadlessException {
        //调用父类构造函数
        super("正在连接Siri...");
        //初始化私有字段
        Objects.requireNonNull(endpoint);
        this.endpoint = endpoint;
        text_receiver.setEditable(false);
        //工具栏添加发送按钮和文本框
        JToolBar toolbar = new JToolBar();
        toolbar.add(text_sender);
        toolbar.add(sendbutton);
        //分组控件添加滚动条、工具栏、聊天记录
        Panel panel = new Panel(new BorderLayout());
        panel.setName("Siri");
        panel.add(new JScrollPane(text_receiver));
        panel.add(toolbar, "South");
        //选项卡添加已经装好其他子控件的分组控件
        JTabbedPane JTab = new JTabbedPane();
        JTab.add(panel);
        //Frame初始化并添加选项卡
        this.add(JTab);
        this.setBounds(400, 240, 600, 500);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Establish socket communication with the client, and
     * initialize some settings and bind dynamic events,
     * which need to use the connected socket
     */
    @Override
    public void run() {
        try {
            this.socket.connect(this.endpoint, 5000);
            var socketin = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8);
            var socketout = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
            this.sendbutton.addActionListener(
                    e -> {
                        String request = this.text_sender.getText();
                        this.text_sender.setText("");
                        synchronized (this.text_receiver) {
                            this.text_receiver.append(request + "\n");
                        }
                        socketout.println(request);
                    }
            );
            this.setVisible(true);
            this.setTitle("聊天室");
            new Thread(
                    () -> {
                        while (socketin.hasNextLine()) {
                            synchronized (this.text_receiver) {
                                text_receiver.append("Siri: " + socketin.nextLine() + "\n");
                            }
                        }
                    }
            ).start();
        } catch (IOException ignore) {
            this.setTitle("连接Siri失败或请求超时，请重试。");
        }
    }

}