package 实验七;

import 实验七.content.*;

/**
 * 工具类 {@code ExpSeven} 的两个方法分别验证实验七的两道小题。
 * 每一道小题均使用包 {@code 实验七.content} 中的多个类
 * <p>实验七概述：
 * <ol>
 *     <li>{@link Client} 与 {@link Server} 在本题中分别代表着
 *     两台不同机器的客户端线程与服务器端线程，其中服务器端线程使
 *     用线程池技术，可同时接受指定数目的客户端请求。</li>
 *     <li></li>
 * </ol></p>
 * @author 段云飞
 * @since 2019-11-27
 */

public final class ExpSeven {
    //Tool class does not need public constructor
    private ExpSeven() {}

    /**
     * simulate JUnit's continuous execution of specified validation methods.
     * @param args useless param
     */
    public static void main(String[] args) {
        expSeven_1_Verify();
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
        //暂定服务器最大并发数为50
        new Thread(new Server(50, 8189), "服务器线程").start();
        new Thread(new Client("localhost", 8189),"客户端线程").start();
    }

    /**
     * 实现聊天室功能。
     * 1）完成服务器端和一个客户端之间的聊天功能。
     * 2）扩展部分，采用多线程技术实现一个服务器端和多个客户端之间的聊天
     * 功能。
     */
    public static void expSeven_2_Verify() {

    }
}
