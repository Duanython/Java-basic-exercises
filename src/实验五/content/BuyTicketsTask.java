package 实验五.content;

/**
 * {@code BuyTicketsTask} 是题设中买票任务的抽象
 * <p><ul>
 * <li>其参与者是购票人 {@link People}与购票数{@code int}</li>
 * <li>{@code BuyTicketsTask} 可作为事件处理的组件，便于管理与扩展</li>
 * <li>缺点：由于题设较简单，并没有突出此种设计方法的优势，反而代码量较大。</li>
 * </ul></p>
 *
 * @author 段云飞
 * @since 2019-11-26
 */
public class BuyTicketsTask {
    private static int ticketprice = Money.FIVE.getValue();
    private People customer;
    private int buycount;

    /**
     * Construct a ticket purchase task by using
     * the specified buyer and number of tickets.
     *
     * @param customer Specific ticket customers.
     * @param buycount Specific the number of tickets.
     */
    public BuyTicketsTask(People customer, int buycount) {
        this.customer = customer;
        this.buycount = buycount;
    }

    /**
     * Returns the price of tickets.
     *
     * @return the price of tickets.
     */
    public static int getTicketprice() {
        return ticketprice;
    }

    /**
     * Returns the customer who buy tickets.
     *
     * @return the customer who buy tickets.
     */
    public People getCustomer() {
        return customer;
    }

    /**
     * Returns the number of tickets to buy.
     *
     * @return the number of tickets to buy.
     */
    public int getBuycount() {
        return buycount;
    }

    /**
     * buy tickets and change at the specific ticket office.
     *
     * @param cashregister specific ticket office.
     * @return returns {@code true} if the change is successful
     */
    public boolean buying(Wallet cashregister) {
        return cashregister.charge(buycount * ticketprice, customer.getWallet());
    }
}