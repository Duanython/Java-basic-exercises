package 实验二.content;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * {@code Person} 是现实世界人类个体的抽象
 * <p><ul>
 *     <li>对 {@code Idnum} 字段利用正则表达式进行有效性检查。</li>
 *     <li> {@code Person} 类带有 {@code abstract} 修饰的公有方法应
 *      委托给实现 Card 接口的类。</li>
 *      <li>缺点：即便 “开户、存款、取款、查询（余额、明细）、销户”等操
 *      作是由 {@code abstract} 修饰的公有方法，但作为 {@code Person}
 *      的方法还是不合时宜的。</li>
 * </ul></p>
 * @author 段云飞
 * @since 2019-10-24
 */
public abstract class Person {
    private String name;
    private final String Idnum;
    private static final Predicate<String> rexidnum;

    static {
        //验证中国身份证号的正则表达式
        rexidnum = Pattern.compile("^[1-9]\\d{7}((0\\d)" +
                "|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|" +
                "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))" +
                "(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$").asMatchPredicate();
    }

    /**
     * provide protected constructor for {@code Person}'s
     * subclass to access initialization private fields
     * @param name Person's name.
     * @param idnum Person's ID.
     * @throws IllegalArgumentException Invalid ID card information
     */
    protected Person(String name, String idnum) {
        if(!rexidnum.test(idnum))
            throw new IllegalArgumentException("无效身份证信息");
        this.Idnum = idnum;
        this.name = name;
    }

    /**
     * return the name of somebody.
     * @return the name of somebody.
     */
    public String getName() {
        return name;
    }

    /**
     * this is an abstract method.
     * open card account with the specific money.
     * @param initmoney Payment amount of the first card
     * @return Latest balance
     */
    public abstract Card openCard(double initmoney);

    /**
     * this is an abstract method.
     * Indicates the recharge operation of this card
     * @param money Recharge amount
     * @return Latest balance of card
     */
    public abstract double reCharge(double money);

    /**
     * this is an abstract method.
     * Indicates the consumption operation of this card
     * @param money Consumption amount of this consumption
     * @return Surplus amount of this Card.
     * @throws MoneyRunLowException The balance of this card
     * is not enough to consume the specified amount
     */
    public abstract double drawMoney(double money) throws MoneyRunLowException;

    /**
     * this is an abstract method.
     * query card's balance
     * @return the balance of this card.
     */
    public abstract double queryBalance();
    /**
     * this is an abstract method.
     * Print the detailed consumption record on the terminal device
     */
    public abstract void printConsumeDetails();

    /**
     * this is an abstract method.
     * log off this card at designated institution.
     * @return refund amount of this card.
     */
    public abstract double logoffCard();
}