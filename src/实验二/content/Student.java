package 实验二.content;

import java.time.LocalDate;

/**
 * {@code Student} 是现实世界学生的另一个抽象。
 * <p><ul>
 *     <li>字段 {@code campuscard} 表示每个 {@code Studnet}
 *     实例都持有一个实现了 {@code Card} 接口的类，代表每个学生
 *     个体都持有属于自己的校园一卡通。</li>
 *
 *     <li>{@code Student} 实现了{@link Person} 的抽象方法，将
 *     具体实现委托给字段 {@code campuscard} 的方法。</li>
 *
 *     <li>将开通和注销卡的操作委托给 {@link CampusCardService}
 *     代表着学生开通校园卡和注销校园卡均需校园一卡通服务中心这一机构。</li>
 *      <li>缺点： “开户、存款、取款、查询（余额、明细）、销户”等操作
 *      作为 {@code Student} 的公有方法不合时宜，但仍未想出更好的设计方法。
 *      初步设想是将{@code public final} 作为字段 {@code campuscard}的修饰符。
 *      尽可使用{@code Card} ，但不可补办、注销等操作。</li>
 * </ul></p>
 * @author 段云飞
 * @since 2019-10-25
 */
public class Student extends Person {
    private final int studentID;
    private LocalDate addmissionTime;
    private double gradePoint;
    private Card campuscard;
    private static int autodistributor = 174367;

    /**
     * Constructs a newly allocated Student object with the necessary information
     * @param name Student's name.
     * @param idnum Student's ID.
     * @param addmissionTime Student's addmission time.
     * @param gradePoint Student's grade point.
     * @throws IllegalArgumentException Invalid ID card information
     */
    public Student(String name, String idnum, LocalDate addmissionTime, double gradePoint) {
        super(name, idnum);
        this.addmissionTime = addmissionTime;
        this.gradePoint = gradePoint;
        this.studentID = autodistributor++;
    }

    /**
     * return this student's student id.
     * @return student id of this student.
     */
    public int getStudentID() {
        return studentID;
    }
    /**
     * open campus card with the specific money at hebutCardService.
     * @param initmoney Payment amount of the first card
     * @return Latest balance
     */
    @Override
    public Card openCard(double initmoney) {
        return (campuscard =
                CampusCardService.hebutCardService.newCampusCard(this, initmoney)
        );
    }
    /**
     * Campus card recharge operation
     * @param money Recharge amount
     * @return Latest balance of card
     */
    @Override
    public double reCharge(double money) {
        requireOpenfirst();
        return campuscard.reCharge(money);
    }
    /**
     * Campus card consume operation
     * @param money Consumption amount of this consumption
     * @return Surplus amount of this Card.
     * @throws MoneyRunLowException The balance of this card
     * is not enough to consume the specified amount
     */
    @Override
    public double drawMoney(double money) throws MoneyRunLowException {
        requireOpenfirst();
        return campuscard.consume(money);
    }
    /**
     * Get card balance
     * @return the balance of this card.
     */
    @Override
    public double queryBalance() {
        requireOpenfirst();
        return campuscard.getBanlance();
    }
    /**
     * Print the detailed consumption record on the terminal device
     */
    @Override
    public void printConsumeDetails() {
        requireOpenfirst();
        campuscard.printConsumeDetails();
    }
    /**
     * this is an abstract method.
     * log off this card at hebutCardService.
     * @return refund amount of this card.
     */
    @Override
    public double logoffCard() {
        requireOpenfirst();
        return CampusCardService.hebutCardService.delCampusCard(this);
    }
    private void requireOpenfirst(){
        if(campuscard == null)
            throw new IllegalStateException("需先开通校园一卡通。");
    }
}