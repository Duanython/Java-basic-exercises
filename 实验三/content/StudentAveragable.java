package 实验三.content;
import 实验一.content.Student;

import java.time.LocalDate;

/**
 * {@code StudentAveragable}继承自 {@link Student}
 * 求平均值的算法是将全部数值相加除以数值集合的个数。
 * @author 段云飞
 * @since 2019-10-25
 */
public class StudentAveragable extends Student implements Average {
    /**
     * Constructs a newly allocated Student object with the necessary information
     * @param name Student's name.
     * @param age Student's age.
     * @param birthday Student's birthday.
     * @param javaExpGrade Student's java experiment grade.
     * @throws IllegalArgumentException name is nonullable and age is nonnegative number.
     */
    public StudentAveragable(String name, int age, LocalDate birthday, double javaExpGrade) {
        super(name, age, birthday, javaExpGrade);
    }

    /**
     * average grade of all students by the average algorithm
     * is to divide all the values by the number of value sets.
     * @return Students' overall java average grade.
     */
    static double aver(){
        return getjavaAverExpGrade();
    }
}
