package 实验一.content;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * {@code PrimeNumber} 采用 线程池执行器 及相关的类以并行的方式完成计算任务。
 * <p><ul>
 *     <li>内部类{@link ComputePrime} 用于完成每个线程的核心计算任务。</li>
 *     <li>缺点：求素数的操作采用经优化过的蛮力算法，算法优化工作做得非常少。</li>
 *     <li>缺点：每个线程固定的执行 1 << 13 次求素数运算，当要计算的数据过多时略显笨拙。</li>
 * </ul></p>
 * @author 段云飞
 * @since 2019-10-24
 */
public final class PrimeNumber{
    /**
     * There are the minimum prime number and the maximum calculable range.
     */
    public static final long MIN_PRIMENUMBER = 2;
    public static final long MAX_RANGE = Long.MAX_VALUE;

    //Tool class does not need public constructor
    private PrimeNumber(){}

    /**
     * compute the prime number in [from , to).
     * @param from the first number to be computed.
     * @param to the number after the last computed number.
     * @return The List of prime numbers.
     * @throws IllegalArgumentException - if params from > to or from < {@code MIN_PRIMENUMBER}.
     */
    public static List<Long> parallelCompute(long from, long to) throws ExecutionException {
        PrimeNumber.rangeCheck(from, to);
        //使用内部类 ComputePrime 的工厂方法派发任务。
        List<Long> primeColls = new LinkedList<>();
        if (from == 2) {
            primeColls.add(from);
            from++;
        }
        var tasks = ComputePrime.newComputePrimeTasks(from, to);
        var executor = Executors.newCachedThreadPool();
        try {
            var results = executor.invokeAll(tasks);
            for (var result : results)
                //每个线程都有属于自己的素数集，大大减少了同步开销。
                primeColls.addAll(result.get());

        } catch (InterruptedException | ExecutionException e) {
            throw new ExecutionException("concurrent running error", e);
        } finally {
            executor.shutdown();
        }
        return primeColls;
    }

    private static void rangeCheck(long from, long to){
        if(from > to)
            throw new IllegalArgumentException("fromIndex(" + from + ") > toIndex(" + to + ")");
        if(from < MIN_PRIMENUMBER)
            throw new IllegalArgumentException("fromIndex(" + from + ") < " + MIN_PRIMENUMBER);
    }

    private static class ComputePrime implements Callable<List<Long>>{
        private static final long EVERYTASKWORKLOAD = 1 << 13;
        private long from;
        private long to;
        private List<Long> primesets;

        //经由工厂方法 newComputePrimeTasks 统一生成并派发任务。
        private ComputePrime(long from, long to) {
            this.from = from;
            this.to = to;
            this.primesets = new LinkedList<>();
        }

        /**
         *The core operation of finding prime number
         * adopts the optimized brute force algorithm
         * @return the prime list.
         */
        @Override
        public List<Long> call() {
            long sqrtnum, j;
            for (long i = from % 2 == 0 ? from + 1 : from;
                 i < to; i += 2) {
                sqrtnum = (long)Math.sqrt(i);
                for (j = 3; j <= sqrtnum; j += 2)
                    if(i % j == 0) break;
                if(j > sqrtnum) primesets.add(i);
            }
            return primesets;
        }

        private static Collection<ComputePrime> newComputePrimeTasks(long from, long to){
            //每个线程固定的执行 1 << 13 次求素数运算，当要计算的数据过多可能任务过多，CPU负载过重。
            var tasks = new ArrayList<ComputePrime>();
            long singleTaskFrom = from;
            long singleTaskTo = from + EVERYTASKWORKLOAD;
            while (singleTaskTo < to){
                tasks.add(new ComputePrime(singleTaskFrom, singleTaskTo));
                singleTaskFrom = singleTaskTo;
                singleTaskTo += EVERYTASKWORKLOAD;
            }
            tasks.add(new ComputePrime(singleTaskFrom, to));
            return tasks;
        }

    }
}