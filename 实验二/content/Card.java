package 实验二.content;

/**
 * {@code Card} 是现实世界中消费型卡的抽象
 * 具有充值、消费、查看余额、查看流水等操作
 * <p><ul>
 *     <li>在静态工厂设计模式中担当抽象产品角色。</li>
 *     <li>办理卡和注册卡不应该是 {@code Card} 操作</li>
 *     <li>缺点：{@link #printConsumeDetails()} 作为 {@code Card}
 *     的操作不是很合适，应该为{@code #getConsumeDetails()}，在这里
 *     使用前者是为了减少一些写程序的工作量。</li>
 * </ul></p>
 * @author 段云飞
 * @since 2019-10-24
 */
public interface Card {
    /**
     * Indicates the recharge operation of this card
     * @param money Recharge amount
     * @return Latest balance of card
     */
    double reCharge(double money);

    /**
     * Get card balance
     * @return the balance of this card.
     */
    double getBanlance();

    /**
     * Indicates the consumption operation of this card
     * @param money Consumption amount of this consumption
     * @return Surplus amount of this Card.
     * @throws MoneyRunLowException The balance of this card
     * is not enough to consume the specified amount
     */
    double consume(double money) throws MoneyRunLowException;

    /**
     * Print the detailed consumption record on the terminal device
     */
    void printConsumeDetails();
}
