package 实验一.content;

import java.time.LocalDate;

/**
 * {@code Student} 类记录了学生个人信息及其Java实验成绩。
 * {@code Student} 类静态字段记录了全体学生的统计属性。
 * <p><ul>
 *     <li>对 {@code age}、{@code name} 字段进行了合法性检查。</li>
 *     <li>允许使用负分作为成绩的特殊标识，且不会算入平均成绩中。</li>
 *     <li>增设了 {@code hasChanged} 字段用于延迟、减少除法开销。</li>
 *     <li>缺点：类的API较少，仅设计了至少使用一次的方法，需进一步扩展。</li>
 * </ul></p>
 * @author 段云飞
 * @since 2019-10-18
 */
public class Student {
    private String name;
    private int age;
    private LocalDate birthday;
    private double javaExpGrade;
    private static double javaTotalExpGrade = 0;
    private static long stucount = 0;
    private static double javaAverExpGrade = 0;
    private static boolean hasChanged = false;

    /**
     * Constructs a newly allocated Student object with the necessary information
     * @param name Student's name.
     * @param age Student's age.
     * @param birthday Student's birthday.
     * @param javaExpGrade Student's java experiment grade.
     * @throws IllegalArgumentException name is nonullable and age is nonnegative number.
     */
    public Student(String name, int age, LocalDate birthday, double javaExpGrade) {
        setName(name);
        setAge(age);
        this.birthday = birthday;
        this.javaExpGrade = javaExpGrade;
        //允许使用负分标识考试的各种意外情况，但不会计算进平均成绩。
        if(Double.compare(javaExpGrade, 0) >= 0) javaTotalExpGrade += javaExpGrade;
        stucount++;
        hasChanged = true;
    }

    /**
     * Student's age
     * @return Student's age
     */
    public int getage()
    {
        return age;
    }

    /**
     * set up the name of this Student to the specified value.
     * @param name Student's new name.
     * @throws IllegalArgumentException name is nonullable.
     */
    public void setName(String name) {
        if (name == null) throw new IllegalArgumentException("姓名不能为空");
        this.name = name.trim();
    }

    /**
     * average grade of all students.
     * @return Students' overall java average grade.
     */
    public static double getjavaAverExpGrade(){
        //只有调用 Student::new 时平均成绩才会发生变化
        // 因此需hasChaned字段作为辅助标识判断是否调用构造器。
        if(hasChanged){
            javaAverExpGrade = javaTotalExpGrade/stucount;
            hasChanged = false;
        }
        return javaAverExpGrade;
    }

    /**
     * total experiment grade of all students.
     * @return Students' overall java total experiment grade.
     */
    public static double getJavaTotalExpGrade() {
        return javaTotalExpGrade;
    }

    /**
     * the number of all students.
     * @return the number of all Student objects.
     */
    public static long getStucount() {
        return stucount;
    }

    /**
     * Student's java experiment grade
     * @return Student's java experiment grade
     */
    public double getJavaExpGrade() {
        return javaExpGrade;
    }

    /**
     * return the student's information.
     * @return the student's name and java experiment grade.
     */
    @Override
    public String toString() {
        return String.format("学生姓名：%s , 学生成绩：%.1f", name, javaExpGrade);
    }
    //It is unreasonable to set the age.
    // It should be calculated automatically according to the date of birth
    private void setAge(int age) {
        if(age < 0) throw new IllegalArgumentException("年龄不能为负数");
        this.age = age;
    }
}
