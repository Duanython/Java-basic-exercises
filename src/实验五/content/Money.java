package 实验五.content;

import java.text.NumberFormat;

/**
 * {@code Money} 模拟现实世界各种面值的货币
 * <p><ul>
 *     <li>设计可枚举类 {@code Money} 使货币作为参数传递时
 *     是可控的，其灵感有那么一丢丢来自于{@link java.time.Month}</li>
 *     <li>其设计模式参考自单例模式</li>
 *     <li>缺点：设计过多对现实世界抽象的工具类在一定程度上会使简单
 *     问题复杂化，若没有文档加以说明，会更难以阅读。</li>
 * </ul></p>
 * @author 段云飞
 * @since 2019-11-26
 */

public enum Money {
    /**
     * The singleton instance for the 5 of specified currency type.
     */
    FIVE(5),
    /**
     * The singleton instance for the 10 of specified currency type.
     */
    TEN(10),
    /**
     * The singleton instance for the 20 of specified currency type.
     */
    TWENTY(20);
    private int value;
    private static NumberFormat currency = NumberFormat.getCurrencyInstance();

    /**
     * Set the specified currency type.
     * @param currency the specified currency type.
     */
    public static void setCurrency(NumberFormat currency) {
        Money.currency = currency;
    }

    /**
     * Get the specified currency type.
     * @return the specified currency type.
     */
    public static NumberFormat getCurrency() {
        return currency;
    }

    /**
     * Gets the currency int value.
     * @return the currency int value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Construct a currency of a specific denomination.
     * @param value the currency int value.
     */
    Money(int value) {
        this.value = value;
    }

    /**
     * Returns money in a specific currency unit.
     * @return money in a specific currency unit.
     */
    @Override
    public String toString() {
        return currency.format(value);
    }
}
