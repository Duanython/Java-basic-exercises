package 实验二.content;
/**
 * {Circle} 表示几何图形圆及其数学属性，如周长、面积等。
 * <p><ul>
 *     <li>缺点：该类的实用性和功能性仍有待完善。</li>
 * </ul></p>
 * @author 段云飞
 * @since 2019-10-24
 */
public class Circle extends Coordinate implements Shape{
    /**
     * the mpproximate value of PI
     */
    public static final double PI = Math.PI;
    private double r;

    /**
     * Initializes a newly Circle object with a specific value
     * @param r the radius of this Circle object.
     */
    public Circle(double r) {
        Coordinate.requirePositiveNum(r);
        this.r = r;
    }
    /**
     * compute and return the area of this circle.
     * @return the area of this Circle object.
     */
    @Override
    public double getArea() {
        return PI * r * r;
    }
    /**
     * compute and return the area of this circle.
     * @return the perimeter of this Circle object.
     */
    @Override
    public double getPerimeter() {
        return  2 * PI * r;
    }
}
