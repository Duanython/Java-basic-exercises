package 实验五.content;
/**
 * {@code Tortoise} 、{@code Rabit}、{@code ThreadExample} 是实验给定的源代码中的类。
 * 用于测试 {@link Thread#run()}
 */
class Tortoise extends Thread{
    int sleepTime = 0,liveLength = 0;

    public Tortoise(String name, int sleepTime, int liveLength) {
        this.sleepTime = sleepTime;
        this.liveLength = liveLength;
        this.setName(name);//【代码1】设置线程的名字为name
    }

    @Override
    public void run() {
        while (true){
            liveLength--;
            System.out.println("@_@");
            try {
                Thread.sleep(sleepTime);//【代码2】让线程调用sleep()方法进入中断状态
            } catch (InterruptedException e) {
                if (liveLength<=0){
                    System.out.println(getName() + "进入死亡状态\n");
                    return;//【代码3】结束run()方法的语句
                }
            }
        }
    }
}
class Rabit extends Thread{
    int sleepTime = 0,liveLength = 0;

    public Rabit(String name, int sleepTime, int liveLength) {
        super(name);//【代码4】调用父类的构造函数，设置线程的名字为name
        this.sleepTime = sleepTime;
        this.liveLength = liveLength;
    }

    @Override
    public void run() {
        while (true){
            liveLength--;
            System.out.println("*_*");
            try {
                sleep(sleepTime);
            } catch (InterruptedException e) {
                if (liveLength<=0){
                    System.out.println(getName() + "进入死亡状态\n");
                    break;
                }
            }
        }
    }


}
public class ThreadExample {
    public static void main(String[] args) {
        Rabit rabit;
        rabit = new Rabit("a", 1500, 10);//【代码5】新建线程rabit
        Tortoise tortoise = new Tortoise("b",1500,3);//【代码6】新建线程Tortoise
        tortoise.start();//启动线程tortoise
        rabit.start();//启动线程rabit
    }
}
