package 实验二.content;

/**
 * {@code Rectangle} 表示几何图形矩形及其数学属性，如周长、面积等。
 * <p><ul>
 *     <li>缺点：该类的实用性和功能性仍有待完善。</li>
 * </ul></p>
 * @author 段云飞
 * @since 2019-10-24
 */
public class Rectangle extends Coordinate implements Shape{
    private double length;
    private double width;

    /**
     * Initializes a newly Rectangle object with a specific value
     * @param length the length of this Rectangle object.
     * @param width the width of this Rectangle object.
     */
    public Rectangle(double length, double width) {
        Coordinate.requirePositiveNum(length, width);
        this.length = length;
        this.width = width;
    }

    /**
     * compute and return the area of this rectangle.
     * @return the area of this Rectangle object.
     */
    @Override
    public double getArea()  {
        return length * width;
    }

    /**
     * compute and return the area of this rectangle.
     * @return the perimeter of this Rectangle object.
     */
    @Override
    public double getPerimeter() {
        return 2 * (length + width);
    }
}
