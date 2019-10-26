package 实验五.content;

import java.util.EnumMap;
import java.util.Map;

public class TicketOffice {
    public static final TicketOffice Conductor
            = new TicketOffice(Money.Five.getLiteral(), Integer.MAX_VALUE,
            Map.of(Money.Five,1,Money.TEN,0,Money.TWENTY,0));

    public final int TICKET_PRICE;
    private int ticketsLeft;
    private EnumMap<Money, Integer> cashier;

    private TicketOffice(int TICKET_PRICE, int ticketsLeft, Map<Money, Integer> cashier) {
        this.TICKET_PRICE = TICKET_PRICE;
        this.ticketsLeft = ticketsLeft;
        this.cashier = new EnumMap<>(cashier);
    }
}