package 实验三.content;

import 实验一.content.Student;

import java.time.LocalDate;

/**
 * {@code StudentAverageWithoutMaxandMIn}继承自 {@link Student}
 * 求平均值的算法是去掉一个最高分，去掉一个最低分后求平均值。
 * @author 段云飞
 * @since 2019-10-25
 */
public class StudentAverageWithoutMaxandMIn extends Student implements Average {
    private static double min = 100;
    private static double max = 0;

    /**
     * Constructs a newly allocated Student object with the necessary information
     * @param name Student's name.
     * @param age Student's age.
     * @param birthday Student's birthday.
     * @param javaExpGrade Student's java experiment grade.
     * @throws IllegalArgumentException name is nonullable and age is nonnegative number.
     */
    public StudentAverageWithoutMaxandMIn(String name, int age, LocalDate birthday, double javaExpGrade) {
        super(name, age, birthday, javaExpGrade);
        if(Double.compare(javaExpGrade, min) < 0) min = javaExpGrade;
        if(Double.compare(javaExpGrade, max) > 0) max = javaExpGrade;
    }

    /**
     * average grade of all students by the average algorithm is to get the
     * average value after removing the highest score and the lowest score.
     * @return Students' overall java average grade.
     */
    public static double aver(){
            long countleft = getStucount() - 2;
            return countleft <= 0 ? 0 :
                    (getJavaTotalExpGrade() - max - min) /countleft;
    }
}
