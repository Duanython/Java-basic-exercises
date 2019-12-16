package 实验七;

import 实验七.content.*;

import java.awt.*;
import java.net.InetSocketAddress;

/**
 * 工具类 {@code ExpSeven} 的两个方法分别验证实验七的两道小题。
 * 每一道小题均使用包 {@code 实验七.content} 中的多个类
 * <p>实验七概述：
 * <ol>
 *     <li> {@link Server} 贯穿整个实验七，代表着 Web服务器，
 *     支持多客户端连接：等待并接收客户端套接字，然后将服务委
 *     托给应用服务器，随后阻塞以等待下一个客户端套接字。</li>
 *     <li> {@link Client} 代表第一题的客户端，是用户端与应用
 *     服务器端传输数据的媒介，转发用户输入数据并给出提示。</li>
 *     <li> {@link ServerHandler} 代表第一题的应用服务器，用于
 *     处理客户端发来的验证码，并回送验证结果。</li>
 *     <li> {@link ChatFrame} 代表第二题的客户端，使用Java Swing
 *     与 awt 技术，完成一个简易的可视化的聊天程序，和机器人Siri聊天</li>
 *     <li> {@link Siri} 代表第二题的服务器端，是一个只会回答特定问题
 *     的木讷机器人，内置了与客户端套接字传输数据的功能。</li>
 *     <li>在实验 deadline 前发现了一个新问题：套接字并没有在try-resources块
 *     中声明，但在try-resources块结束后, 套接字会隐式关闭，我会在以后的时间
 *     里查阅资料消除我在套接字关闭相关的知识盲区。
 *     </li>
 * </ol></p>
 *
 * @author 段云飞
 * @since 2019-11-27
 */

public final class ExpSeven {
    //Tool class does not need public constructor
    private ExpSeven() {
    }

    /**
     * simulate JUnit's continuous execution of specified validation methods.
     *
     * @param args useless param
     */
    public static void main(String[] args) {
        //expSeven_1_Verify();
        expSeven_2_Verify();
    }

    /**
     * 使用 ServerSocket 类和 Socket 类实现按如下协议通信的服务器端和客户
     * 端程序。
     * 服务器程序的处理规则如下：
     * 1） 向客户端程序发送 Verifying Server!。
     * 2） 若读口令次数超过 3 次，则发送 Illegal User!给客户端，程序退出。否
     * 则向下执行步骤 3）。
     * 3） 读取客户端程序提供的口令。
     * 4） 若口令不正确，则发送 PassWord Wrong!给客户端，并转步骤 2），否则
     * 向下执行步骤 5）。
     * 5） 发送 Registration Successful!给客户端程序。
     * 客户端程序的处理规则如下：
     * 1） 读取服务器反馈信息。
     * 2） 若反馈信息不是 Verifying Server!，则提示 Server Wrong!，程序退出。
     * 否则向下执行步骤 3）
     * 3） 提示输入 PassWord 并将输入的口令发送给服务器。
     * 4） 读取服务器反馈信息。
     * 5） 若反馈信息是 Illegal User!，则提示 Illegal User!，程序退出。否则向下
     * 执行步骤 6）
     * 6） 若反馈信息是PassWord Wrong!，则提示PassWord Wrong!，并转步骤3），
     * 否则向下执行步骤。
     * 7） 输出 Registration Successful!。
     */
    public static void expSeven_1_Verify() {
        //暂定服务器最大并发数为50，服务器套接字地址默认为本机，端口为 8189。
        new Thread(new Server(50, 8189, ServerHandler::new), "第一题Web服务器线程").start();
        //目标套接字地址由构造函数传入，本机套接字地址由操作系统随机分配。
        new Thread(new Client("localhost", 8189), "第一题客户端线程").start();
    }

    /**
     * 实现聊天室功能。
     * 1）完成服务器端和一个客户端之间的聊天功能。
     * 2）扩展部分，采用多线程技术实现一个服务器端和多个客户端之间的聊天功能
     */
    public static void expSeven_2_Verify() {
        //暂定服务器最大并发数为50，服务器套接字地址默认为本机，端口为 8188。
        new Thread(new Server(50, 8188, Siri::new), "第二题Web服务器线程").start();
        //目标套接字地址由构造函数传入，本机套接字地址由操作系统随机分配。
        EventQueue.invokeLater(new ChatFrame(new InetSocketAddress(8188)));
    }
}
