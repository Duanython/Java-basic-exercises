package 实验二.content;

/**
 * {Triangle} 表示几何图形三角形及其数学属性，如周长、面积等。
 * <p><ul>
 *     <li>在数学层面上进行了三角形三边关系的验证。</li>
 *     <li>缺点：该类的实用性和功能性仍有待完善。</li>
 * </ul></p>
 * @author 段云飞
 * @since 2019-10-24
 */
public class Triangle extends Coordinate implements Shape {
    private double x;
    private double y;
    private double z;

    /**
     * Initializes a newly Triangle object with a specific value.
     * @param x one of the three sides of a triangle.
     * @param y the other side of a triangle.
     * @param z the left side of triangle.
     * @throws IllegalArgumentException the three values can't form a triangle.
     */
    public Triangle(double x, double y, double z) {
        Coordinate.requirePositiveNum(x, y, z);
        Triangle.requirethreeSideRelationship(x, y, z);
        this.x = x;
        this.y = y;
        this.z = z;
    }
    /**
     * compute and return the area of this triangle.
     * @return the area of this Triangle object.
     */
    @Override
    public double getArea() {
        double p = (x + y + z) / 2;
        return Math.sqrt(p * (p - x) * (p - y) * (p - z));
    }
    /**
     * compute and return the area of this triangle.
     * @return the perimeter of this Triangle object.
     */
    @Override
    public double getPerimeter() {
        return x + y + z;
    }

    private static void requirethreeSideRelationship(double x, double y, double z){
        if(Double.compare(x + y, z) <= 0
                || Double.compare(x + z, y) <= 0
                || Double.compare(y + z,x) <= 0)
            throw new IllegalArgumentException(
                    String.format("x(%g),y(%g),z(%g) can't form a triangle.", x, z, y));
    }
}