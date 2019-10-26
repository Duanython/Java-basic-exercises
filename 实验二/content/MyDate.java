package 实验二.content;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

/**
 * {@code MyDate} 的静态方法 {@link MyDate#of(int, int, int)}
 * 模拟 {@link LocalDate#of(int, int, int)} 的设计思路并稍加修改。
 * <p><ul>
 *     <li>{@code MyCode.of(int, int, int)} 对输入的年月日进行
 *     合法性判断，若日期合法则创建并返回MyDate实例。</li>
 *     <li>出于完备性的考虑，设计了{@code Month} 与 {@code Year}
 *     代表现实世界中的月份与年</li>
 *     <li>缺点：由于示例过少，未能体现此设计方式的优势</li>
 *     <li>缺点：该类的实用性和功能性仍有待完善。</li>
 * </ul></p>
 * @author 段云飞
 * @since 2019-10-24
 */
public final class MyDate {
    private final int year;
    private final byte month;
    private final byte day;
    /**
     * Obtains an instance of MyDate from a year, month and day.
     * @param year The year between Integer.MIN_VALUE and Integer.MAX_VALUE.
     * @param month The month between 1 and 12.
     * @param dayofMonth the day-of-month to represent, from 1 to 31
     * @return the local date, not null
     * @throws DateTimeException if the value of any field is out of range,
     * or if the day-of-month is invalid for the month-year
     */
    public static MyDate of(int year, int month, int dayofMonth){
        if(dayofMonth < 1 || dayofMonth > Month.of(month).length(Year.isLeap(year)))
            throw new DateTimeException("Invalid value for DayOfMonth: " + dayofMonth);
        return new MyDate(year, month, dayofMonth);
    }
    //Public constructors are easy to confuse users
    private MyDate(int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = (byte) month;
        this.day = (byte) dayOfMonth;
    }
}