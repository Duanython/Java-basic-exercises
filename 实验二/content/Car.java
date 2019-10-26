package 实验二.content;
/**
 * {Car} 表示现实生活中的小汽车
 * <p><ul>
 *     <li>对输入参数进行了有效性验证</li>
 *     <li>缺点：该类的实用性和功能性仍有待完善。</li>
 * </ul></p>
 * @author 段云飞
 * @since 2019-10-24
 */
public class Car extends Vehicle{
    private int loader;
    /**
     * Initializes a newly car object with a specific value.
     * @param wheels the wheels of this car.
     * @param weight the weight of this car.
     * @param loader the loader of this car.
     * @throws IllegalArgumentException params should be positive number.
     */
    public Car(int wheels, int weight, int loader) {
        super(wheels, weight);
        Vehicle.requirePositiveNum(loader);
        this.loader = loader;
    }
    /**
     * return the loader of this Truck object.
     * @return the loader of this Truck object.
     */
    public int getLoader() {
        return loader;
    }
}
