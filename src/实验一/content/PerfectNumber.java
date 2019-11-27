package 实验一.content;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RejectedExecutionException;

/**
 * {@code PerfectNumber} 使用Fork/Join框架提供的类完成计算密集型任务
 * <p><ul>
 *     <li>类的整体设计与{@link PrimeNumber} 一致，仅内部实现不同。</li>
 *     <li>内部类 {@link PerfectNumberCompute} 用于完成每个线程的核心计算任务。</li>
 *     <li>缺点：求完全数的操作采用经优化过的蛮力算法，算法优化工作做得非常少。</li>
 *     <li>Fork/Join框架仍然不熟悉，需进一步学习和练习。</li>
 * </ul></p>
 * @author 段云飞
 * @since 2019-10-24
 */
public final class PerfectNumber {
    /**
     * The range of perfect number is defined according
     * to the superset natural number of perfect number
     */
    public static final long MIN_RANGE = 1;
    public static final long MAX_RANGE = Long.MAX_VALUE;

    //Tool class does not need public constructor
    private PerfectNumber(){}

    /**
     * compute the perfect number in [from , to).
     * @param from the first number to be computed.
     * @param to the number after the last computed number.
     * @return The List of perfect numbers.
     * @throws RejectedExecutionException a task cannot be accepted for execution
     * @throws IllegalArgumentException - if params from > to or from < {@code MIN_RANGE}.
     */
    public static List<Long> parallelCompute(long from, long to){
        PerfectNumber.rangeCheck(from, to);
        ForkJoinPool pool = new ForkJoinPool();
        var task = new PerfectNumberCompute(from, to);
        //blocking util all of the compute were done.
        pool.invoke(task);
        return PerfectNumberCompute.results;
    }

    private static void rangeCheck(long from, long to){
        if(from > to)
            throw new IllegalArgumentException("fromIndex(" + from + ") > toIndex(" + to + ")");
        if(from < MIN_RANGE)
            throw new IllegalArgumentException("fromIndex(" + from + ") < " + MIN_RANGE);
    }

    private static class PerfectNumberCompute extends RecursiveAction {
        private static final long THRESHOLD = 1 << 13;
        private static final ArrayList<Long> results;
        private long from;
        private long to;

        static {
            results = new ArrayList<>();
        }

        /**
         * Construct a recursive subtask
         * @param from the (inclusive) initial value
         * @param to  the exclusive upper bound
         */
        PerfectNumberCompute(long from, long to) {
            this.from = from;
            this.to = to;
        }

        /**
         *The core operation of finding perfect number
         * adopts the optimized brute force algorithm
         */
        @Override
        public void compute() {
            if(to - from < THRESHOLD){
                while (from < to){
                    //求完全数的核心操作
                    long sum = 1;
                    for (long miner = 2, maxer = from;
                         miner < maxer; miner++){
                        if(from % miner == 0){
                            maxer = from / miner;
                            sum += miner + maxer;
                        }
                    }
                    //完全数非常非常少，因此同步开销很小
                    //所以采用ArrayList型的集合收集完全数
                    //采用synchronized完成线程间的同步操作。
                    if(sum == from){
                        synchronized (results){
                            results.add(from);
                        }
                    }
                    from++;
                }
            }
            else {
                long mid = (from + to) / 2;
                var first = new PerfectNumberCompute(from, mid);
                var second = new PerfectNumberCompute(mid, to);
                invokeAll(first, second);
            }
        }
    }
}
