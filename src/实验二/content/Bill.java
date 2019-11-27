package 实验二.content;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * {@code Bill} 是现实世界账单的抽象
 * 在本项目中配合 {@link CampusCardService.CampusCard} 使用
 * <p><ul>
 *     <li>{@link Bill.billentry} 是账单条目的抽象，是账单的一条记录</li>
 *     <li>缺点：账单条目没有个数限制，应限制为只保存近半年的消费记录。</li>
 *     <li> {@link billentry#toString()} 显示时的格式不具有通用性</li>
 * </ul></p>
 * @author 段云飞
 * @since 2019-10-25
 */
public class Bill {
    private final ArrayList<billentry> billentries;
    private final String begin = "DateTime\t\t\tRemarks\t\tDelta\t\tBalance\n";
    private StringBuilder builder = new StringBuilder(begin);

    /**
     * Constructs an empty bill with an initial capacity of ten.
     */
    public Bill() {
        billentries = new ArrayList<>();
    }

    /**
     * Constructs a bill containing the billentry of the specified collection
     * @param oldbill the bill whose billentry are to be placed into this bill
     */
    public Bill(Bill oldbill) {
        billentries = new ArrayList<>(oldbill.billentries.size());
        addAll(0, oldbill);
    }

    /**
     * equivalent to {@link Bill#Bill()} and {@link #add(Event, double, double)}
     * @param event the remarks of the billentry.
     * @param detal Amount change of the banlance.
     * @param banlanceAtThatTime the banlance after the event happened.
     */
    public Bill(Event event, double detal, double banlanceAtThatTime){
        this();
        add(event, detal, banlanceAtThatTime);
    }

    /**
     * construct a billentry with the specified value and appends this
     * object to the end of this bill.
     * @param event the remarks of the billentry.
     * @param detal Amount change of the banlance.
     * @param banlanceAtThatTime the banlance after the event happened.
     * @return {@code true} if this bill changed as a result of the call
     */
    public boolean add(Event event, double detal, double banlanceAtThatTime){
        Bill.detalVerify(event, detal);
        var v = new billentry(event, detal, banlanceAtThatTime);
        builder.append(v.toString()).append('\n');
        return billentries.add(v);
    }

    /**
     * Inserts all of the billentry in the oldbill into this bill, starting at
     * the specified position. Shifts the element currently at that position
     * (if any) and any subsequent billentry to the right (increases their indices).
     * @param index  index at which to insert the first element from the specified bills.
     * @param oldbill bill containing billentry to be added to this bill
     * @return {@code true} if this bill changed as a result of the call
     */
    public boolean addAll(int index, Bill oldbill){
        builder.append(oldbill.builder.subSequence(
                begin.length(), oldbill.builder.length()));
        return billentries.addAll(index,oldbill.billentries);
    }

    /**
     * return String representation of bill
     * @return String representation of bill
     */
    @Override
    public String toString() {
        return builder.toString();
    }

    /**
     * clear all the billentries of this bills.
     */
    public void clear(){
        billentries.clear();
        //I can't find any method that wipe cache.
        builder = new StringBuilder(begin);
    }

    private static void detalVerify(Event event, double detal){
        switch (event){
            case CONSUME: if(Double.compare(detal, 0) > 0)
                throw new IllegalArgumentException(
                        "field detal should be nonpositive number while cosuming."); break;
            case OPEN:
            case RECHARGE: if(Double.compare(detal, 0) < 0)
                throw new IllegalArgumentException(
                        "detal should be nonnegative number while recharing or opening."); break;
            default: break;
        }
    }

    private static class billentry{
        private final LocalDateTime dateTime;
        private final Event event;
        private final double detal;
        private final double banlanceAtThatTime;
        private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");

        private billentry(Event event, double detal, double banlanceAtThatTime) {
            this.dateTime = LocalDateTime.now();
            this.event = event;
            this.detal = detal;
            this.banlanceAtThatTime = banlanceAtThatTime;
        }

        /**
         * the information about one bill entry.
         * @return the information about one bill entry.
         */
        @Override
        public String toString() {
            return String.format("%s\t%s\t\t%.2f\t\t%.2f",
                    dateTime.format(formatter), event.toString(),
                    detal, banlanceAtThatTime);
        }
    }
    /**
     * {@code Event} represent all possible
     * operation sets of campus one card,such
     * as open、consume、recharge.
     */
    public enum Event{
        /**
         * OPEN、CONSUME、RECHARGE represent the remarks of billentry.
         */
        OPEN("Open student account."),
        CONSUME("Student consume."),
        RECHARGE("Student recharge.");
        private final String message;

        /**
         * construct a newly state that represent the remarks of billentry.
         * @param message the meaning of {@code enum Event}
         */
        Event(String message) {
            this.message = message;
        }

        /**
         * return the meaning of {@code enum Event}
         * @return return the meaning of {@code enum Event}
         */
        public String getMessage() {
            return message;
        }
    }
}
