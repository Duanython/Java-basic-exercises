package 实验五;

import 实验五.content.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

/**
 * 工具类 {@code ExpFive} 的三个方法分别验证实验五的三道小题。
 * 第二小题和第三小题均使用包 {@code 实验五.content} 中的多个类
 * <p>实验五概述：
 * <ol>
 *     <li>第一小题不需要使用任何自定义类，采用流操作生成数据集
 *     labda表达式、FutuerTask类及Thread类实现多线程操作。</li>
 *     <li> {@link ThreadExample}、{@code Rabit}、
 *     {@code Tortoise} 按实验要求将源代码粘贴并运行。</li>
 *     <li> 采用生产者、消费者模型解决顾客排队买票问题，其中共享
 *     数据集使用阻塞队列实现。 {@link Money} 模拟现实世界的货币，
 *     属于可枚举类型；{@link Wallet} 作为 {@code Money} 实例的
 *     容器，并完成找钱逻辑(我发现找钱逻辑是贪心法找钱问题的抽象)，
 *     {@link People} 模拟现实生活中的顾客，是{@link BuyTicketsTask}
 *     中的参与者，而后者则模拟了买票任务。生产者线程生产买票任务，
 *     添加到名为 {@code readyqueue} 的阻塞队列中，并从名为 {@code failqueue}
 *     的阻塞队列中取出购买失败的买票任务，重新加入到{@code readyqueue}。
 *     消费者线程处理买票任务并完成找钱逻辑，输出买票信息，若购买失败则将
 *     该买票任务加入到{@code failqueue}。</li>
 * </ol></p>
 * @author 段云飞
 * @since 2019-10-24
 */
public final class ExpFive {
    //Tool class does not need public constructor
    private ExpFive() {}
    /**
     * simulate JUnit's continuous execution of specified validation methods.
     * @param args useless param
     */
    public static void main(String[] args) {
        expFive_1_Verify();
        expFive_2_Verify();
        expFive_3_Verify();
    }

    /**
     * 编写一个有两个线程的程序，第一个线程用来计算1~100之间的偶数及个数，
     * 第二个线程用来计算1-100之间的偶数及个数。
     */
    public static void expFive_1_Verify() {
        //The first time I use class Stream except System.in and System.out,
        //I can't find a way for for two threads to operate a stream at the same time.
        IntPredicate isOdd = i -> i % 2 == 1;
        var odd = new FutureTask<>(()-> IntStream.rangeClosed(1, 100).filter(isOdd).count());
        var even = new FutureTask<>(()-> IntStream.rangeClosed(1, 100).filter(isOdd.negate()).count());
        new Thread(odd).start();
        new Thread(even).start();
        try {
            System.out.printf("There are %d odd numbers and %d even numbers.",
                    odd.get(), even.get());
        } catch (InterruptedException | ExecutionException ignored) {
        }
    }

    /**
     * 编写一个Java应用程序，在主线程中再创建两个线程，要求线程经历四种
     * 状态：新建，运行、中断和死亡。按模板要求，将【代码1】~【代码8】替换为
     * Java程序代码。  ...（省略了实验指定的源代码）
     */
    public static void expFive_2_Verify() {
        ThreadExample.main(null);
    }

    /**
     * 编写Java应用程序模拟5个人排队买票。售票员只有1张五元的钱，电影
     * 票五元钱一张。假设5个人的名字及排队顺序是：赵、钱、孙、李、周。“赵”
     * 拿1张二十元的人民币买2张票，“钱”拿1张二十元的人民币买1张票，“孙”1
     * 张十元的人民币买1张票，“李”拿1张十元的人民币买2张票，“周”拿1张五元
     * 的人民币买1张票。
     * 要求售票员按如下规则找赎：
     * （1）二十元买1张票，找零：1张十元；不许找零2张五元。
     * （2）二十元买1张票，找零：1张十元，1张五元；不许找零3张五元。
     * （3）十元买一张票，找零1张五元。
     */
    public static void expFive_3_Verify() {
        var readyqueue = new LinkedBlockingQueue<BuyTicketsTask>();
        var failqueue = new LinkedBlockingQueue<BuyTicketsTask>();
        new Thread(() -> {
            String[] names = {"赵", "钱", "孙", "李", "周"};
            int[] ticketcounts = {2, 1, 1, 2, 1};
            Money[] monies = {Money.TWENTY, Money.TWENTY, Money.TEN, Money.TEN, Money.FIVE};
            try {
                for (int i = 0; i < 5; i++)
                    readyqueue.put(
                            //买票任务的参与者是People与买票数
                            new BuyTicketsTask(
                                    //而People具有的属性是姓名和他的钱包
                                    new People(names[i], new Wallet(monies[i])),
                                    ticketcounts[i]
                            )
                    );
                } catch (InterruptedException ignore) {
                    System.err.println("任务生产失败");
                    return;
            }
            try {
                BuyTicketsTask fail;
                //若等待5秒仍没有买票任务则认为没有失败任务。
                while ((fail = failqueue.poll(5, TimeUnit.SECONDS)) != null)
                    readyqueue.put(fail);
            }catch (InterruptedException ignore){
                if(failqueue.size() > 0)
                    System.err.println("任务处理失败。");
            }
        },"生产、失败任务处理线程").start();
        new Thread(() -> {
            int ticketleft = Integer.MAX_VALUE;
            //cashier 是售票窗口的抽象。
            Wallet cashier = new Wallet(Money.FIVE);
            try {
                BuyTicketsTask task;
                //若等待5秒仍没有买票任务则认为已处理完所有任务。
                while ((task = readyqueue.poll(5,TimeUnit.SECONDS)) != null){
                    if(task.buying(cashier)){
                        ticketleft--;
                        System.out.printf("%s先生买票成功。\n",
                                task.getCustomer().getName());
                    }
                    else{
                        failqueue.put(task);
                        //这样好不公平。
                        System.out.printf("由于电影院找不开钱，%s先生去最后排队\n",
                                task.getCustomer().getName());
                    }
                }
            }catch (InterruptedException ignore){
                int len = readyqueue.size();
                if(len > 0)
                    System.err.printf("%s被中断，有%d个任务处理未处理。\n",
                            Thread.currentThread().getName(), len);
            }
        },"购票任务处理线程").start();
    }
}