package 实验五.content;
/**
 * 半成品，未完成 mark
 */
public class TicketBuyer {
    public static final int MAXTICKETSBUYATONCE = 4;
    private String name;
    private Money money;
    private int count;

    public TicketBuyer(String name, Money money, int count) {
        if(count <= 0)
            throw new IllegalArgumentException("Invalid number of tickets bought.");
        if(count > MAXTICKETSBUYATONCE)
            throw new IllegalArgumentException("You can buy " + MAXTICKETSBUYATONCE + " tickets at most at once.");
        this.name = name;
        this.money = money;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public Money getMoney() {
        return money;
    }

    public int getCount() {
        return count;
    }
}
