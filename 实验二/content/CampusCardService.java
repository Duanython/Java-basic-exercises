package 实验二.content;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code CampusCardService} 是现实生活中校园一卡通服务中心的抽象
 * 每一个 {@code CampusCardService} 实例都代表了某一个学校的一卡通
 * 服务中心，它们服务于该学校的学生，开通、补办、注销该学校学生的一卡通。
 * <p><ul>
 *     <li>设计思路参考了Fork/Join框架的源代码。</li>
 *     <li>{@code CampusCardService} 在静态工厂模式中担当具体工厂角色。</li>
 *     <li>内部类{@link CampusCardService.CampusCard}的生产、回收、补办等操作
 *     需先向 {@code CampusCardService} 的字段 {@code cards} 注册，以便统一管理。</li>
 *     <li>{@link Bill} 用于辅助 {@link CampusCardService.CampusCard} 的账单打印操作。</li>
 *     <li>缺点：注销校园卡之后的善后工作的设计仍有不足，只要存在外部引用则数据仍会残留在内存、数据库中。
 *     但是这种设计也模拟了现实生活中即便校园卡废弃了，但是仍可保留实体卡做收藏的情况。</li>
 * </ul></p>
 * @author 段云飞
 * @since 2019-10-24
 */
public class CampusCardService {
    private final String schoolname;
    //类似于学校一卡通服务中心的数据库，保存着学生一卡通的信息。
    private final Map<Integer,CampusCard> cards;
    /**
     * Because there is no class designed for school.
     * so I can only new a CampusCardService object
     * named hebutCardService here.
     */
    public static final CampusCardService hebutCardService
            = new CampusCardService("河北工业大学");

    /**
     * Constructs a newly allocated CampusCardService object for a school.
     * @param schoolname the name of the school.
     */
    public CampusCardService(String schoolname) {
        this.schoolname = schoolname;
        this.cards = new HashMap<>();
    }

    /**
     * return the name of the school to which
     * Campus Card Service Center belongs.
     * @return the name of school.
     */
    public String getSchoolname() {
        return schoolname;
    }

    /**
     * returns a campus card with specific student information.
     * a deposit of ${@link CampusCard#DEPOSIT} will be charged.
     * @param stu a student who want to apply for a campus card.
     * @param banlance first recharge amount
     * @return a newly campus card
     * @throws IllegalArgumentException banlance is less than {@link CampusCard#DEPOSIT}.
     * @throws IllegalStateException This student has been applied for a campus card.
     */
    public CampusCard newCampusCard(Student stu, double banlance){
        if(Double.compare(banlance, 20) < 0)
            throw new IllegalArgumentException("办理校园卡时金额("
                    + banlance + ") < " + CampusCard.DEPOSIT + "。");
        int stuid = stu.getStudentID();
        if(cards.containsKey(stuid))
            throw new IllegalStateException("该学生已经办理校园卡。");
        CampusCard c = new CampusCard(stu, banlance);
        cards.put(stuid, c);
        return c;
    }

    /**
     * log off a campus card with specific student information
     * @param stu a student who want to log off a campus card.
     * @return Sum of campus card balance and {@link CampusCard#DEPOSIT}.
     * @throws IllegalStateException The student has not applied for campus card.
     */
    public double delCampusCard(Student stu){
        CampusCard v = cards.remove(stu.getStudentID());
        if(v == null) throw new IllegalStateException("该学生没有办理校园卡。");
        return v.deleteself();
    }

    /**
     * reissue a campus card with specific student information.
     * The balance and information of the old campus card will be transferred
     * to the new one, and a deposit of ${@link CampusCard#DEPOSIT} will be charged.
     * @param stu a student who want to reissue a campus card.
     * @param banlance the amount of money when reissuing.
     * @return a newly campus card with old campus card information.
     * @throws IllegalArgumentException banlance should be nonnegative number.
     * @throws IllegalStateException The student has not applied for campus card.
     */
    public CampusCard reissueCampusCard(Student stu, double banlance){
        CampusCardService.requireNonNegative(banlance, "补办校园卡时金额不能为负数");
        int stuid = stu.getStudentID();
        if(!cards.containsKey(stuid))
            throw new IllegalStateException("一卡通服务中心无该学生信息。");
        CampusCard v = CampusCard.reissue(stu, cards.remove(stuid), banlance);
        cards.put(stuid, v);
        return v;
    }

    /**
     * Equivalent to {@link #reissueCampusCard(Student, double) reissueCampusCard(stu, 0)}
     * @param stu a student who want to reissue a campus card.
     * @return a newly campus card with old campus card information.
     */
    public CampusCard reissueCampusCard(Student stu){
        return reissueCampusCard(stu, 0);
    }

    /**
     * {@code CampusCard} 是现实世界中校园卡的抽象
     * <p><ul>
     *     <li>继承自 {@link Car} 符合 OOP程序设计，使用者也
     *     无需关心实现 {@code Card} 的类的具体类型。</li>
     *     <li>{@code CampusCard} 在静态工厂模式中担当具体产品角色</li>
     *
     * </ul></p>
     */
    public static class CampusCard implements Card {
        /**
         * Deposit for campus card.
         */
        public static final double DEPOSIT = 20;
        private final String name;
        private final int studentID;
        private double banlance;
        private boolean isActive;
        private final Bill bill;

        private CampusCard(Student stu, double banlance){
            this.name = stu.getName();
            this.studentID = stu.getStudentID();
            this.banlance = banlance - DEPOSIT;
            bill = new Bill(Bill.Event.OPEN, banlance, banlance);
            isActive = true;
        }

        private static CampusCard reissue(Student stu, CampusCard old, double banlance){
            CampusCard nw = new CampusCard(stu, banlance + old.banlance);
            old.bill.addAll(0, old.bill);
            old.isActive = false;
            return nw;
        }
        /**
         * Indicates the recharge operation of this campus card
         * @param money Recharge amount
         * @return Latest balance of card
         */
        @Override
        public double reCharge(double money){
            requireActive();
            CampusCardService.requireNonNegative(money, "充值金额不能为负数。");
            banlance += money;
            bill.add(Bill.Event.RECHARGE, money, banlance);
            return banlance;
        }
        /**
         * Get campus card balance
         * @return the balance of this card.
         */
        @Override
        public double getBanlance() {
            requireActive();
            return banlance;
        }
        /**
         * Indicates the consumption operation of this campus card
         * @param money Consumption amount of this consumption
         * @return Surplus amount of this Card.
         * @throws MoneyRunLowException The balance of this card
         * is not enough to consume the specified amount
         */
        @Override
        public double consume(double money) throws MoneyRunLowException {
            requireActive();
            CampusCardService.requireNonNegative(money, "不能消费负数的金额。");
            double temp = banlance - money;
            if(Double.compare(temp, 0) < 0)
                throw new MoneyRunLowException(banlance);
            banlance = temp;
            bill.add(Bill.Event.CONSUME, (-1) * money, banlance);
            return banlance;
        }
        /**
         * Print the information of this card on the terminal device
         */
        public void printCampusCardInfomation(){
            requireActive();
            System.out.printf("姓名：%s\n学号：%d\n余额：%.2g\n",
                    name, studentID, banlance);
        }
        /**
         * Print the detailed consumption record on the terminal device
         */
        public void printConsumeDetails(){
            requireActive();
            System.out.println(bill);
        }

        private void requireActive(){
            if(!isActive)
                throw new IllegalStateException("该校园卡已注销。");
        }

        private double deleteself(){
            isActive = false;
            return banlance + DEPOSIT;
        }
    }

    private static void requireNonNegative(double v, String message){
        if(Double.compare(v, 0) < 0)
            throw new IllegalArgumentException(message);
    }
}
