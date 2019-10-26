package 实验二.content;

/**
 * {@code MoneyRunLowException} 描述了消费时余额不足的异常情况。
 * 该异常配和 {@link Card#consume(double)} 及其子类方法使用。
 * <p><ul>
 *     <li>{@link Throwable} 类已经设计的足够完备，设计 {@code MoneyRunLowException}
 *     仅仅用于区分余额不足这种特殊状态，不需要额外的异常处理操作。</li>
 *     <li>{@code MoneyRunLowException} 继承自受查异常 {@link Exception}，表示出现此
 *     类异常时程序员必须处理并通知用户。</li>
 *     <li>缺点：设计 {@link Card#consume(double)} 时在遇到余额不足情况，并未想出比抛出
 *     异常更好的解决方案，只能自己设计一个易区分的受查异常作为折中办法。</li>
 * </ul></p>
 * @author 段云飞
 * @since 2019-10-24
 */
public class MoneyRunLowException extends Exception {
    /**
     * Constructs a new MoneyRunLowException with the specified left money.
     * @param leftmoney account balance in case of insufficient balance
     */
    public MoneyRunLowException(double leftmoney){
        super(String.format("Sorry, your credit is " +
                "running low.You have $ %.2g left.", leftmoney));
    }
}
