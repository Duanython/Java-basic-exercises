package 实验五.content;

import java.util.Arrays;

/**
 * {@code Wallet} 是现实世界中钱包的抽象
 * <p><ul>
 * <li>其公有方法接受的参数是类型安全的{@link Money} 实例，
 * 防止传入非法面值的货币</li>
 * <li>其内部实现则使用数组表示货币数量，便于数值计算，放弃
 * 了最初使用{@link java.util.EnumMap}实现货币计数的设想。</li>
 * <li>方法{@link #charge(int, Wallet)} 的找钱算法是贪心法</li>
 * <li>缺点：类的设计思想过于模拟现实世界时会产生大量代码，并且不利于某些
 * 数值型书法的实现。</li>
 * <li>缺点：该类仍不具有通用性，构造方法的设计合理性有待商榷。其实现可能
 * 会有性能瓶颈。</li>
 * <li>缺点：方法 {@link #charge(int, Wallet)} 在接口设计上存在着一些不合理性。</li>
 * </ul></p>
 *
 * @author 段云飞
 * @since 2019-11-26
 */

public class Wallet {
    private static final int[] icon = Arrays.stream(Money.values()).mapToInt(Money::getValue).toArray();
    private int[] count;

    /**
     * Construct a wallet and deposit it
     * in a currency of a specified denomination
     *
     * @param monies the set of Money
     */
    public Wallet(Money... monies) {
        count = new int[icon.length];
        for (Money i : monies)
            count[Arrays.binarySearch(icon, i.getValue())]++;
    }

    /**
     * Use the money in the specified wallet to purchase the goods
     * with the specified price and complete the change operation
     *
     * @param totalprice Total price of goods
     * @param monies     the specific wallet for customers' money
     * @return returns {@code true} if the change is successful
     */
    public boolean charge(int totalprice, Wallet monies) {
        int sum = monies.sumofWallet();
        if (sum < totalprice)
            //顾客的钱不能支付的起商品
            return false;
        else if (sum == totalprice) {
            //正好
            this.merge(monies);
            monies.clear();
            return true;
        } else {
            sum -= totalprice;
            //贪心法找钱的核心代码
            int[] tmp = new int[count.length];
            for (int i = icon.length - 1; i >= 0; i--) {
                tmp[i] = Math.min(sum / icon[i], count[i]);
                sum -= tmp[i] * icon[i];
            }
            if (sum > 0)
                return false;
            else {
                this.merge(monies);
                monies.count = tmp;
                return true;
            }
        }
    }

    //钱包清空
    private void clear() {
        Arrays.fill(this.count, 0);
    }

    //把钱包里的钱放在一起，但并不减少merged中的钱数
    //需搭配额外的操作才可使用
    private void merge(Wallet merged) {
        for (int i = 0; i < count.length; i++)
            count[i] += merged.count[i];
    }

    //钱包里的钱的总数
    private int sumofWallet() {
        int sum = 0;
        for (int i = 0; i < count.length; i++)
            sum += count[i] * icon[i];
        return sum;
    }
}
