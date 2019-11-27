package 实验二.content;
/**
 * {@code Vehicle} 表示现实生活中的交通工具
 * <p><ul>
 *     <li>对输入参数进行了有效性验证</li>
 *     <li>缺点：该类的实用性和功能性仍有待完善。</li>
 * </ul></p>
 * @author 段云飞
 * @since 2019-10-24
 */
public class Vehicle {
    private int wheels;
    private int weight;

    /**
     * Initializes a newly Vehicle object with a specific value.
     * @param wheels the wheels of this vehicle.
     * @param weight the weight of this vehicle.
     * @throws IllegalArgumentException params should be positive number.
     */
    public Vehicle(int wheels, int weight) {
        requirePositiveNum(wheels, weight);
        this.wheels = wheels;
        this.weight = weight;
    }

    /**
     * return the wheels of this vehicle.
     * @return the wheels of this vehicle.
     */
    public int getWheels() {
        return wheels;
    }

    /**
     * return the weight of this weight.
     * @return the weight of this weight.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Checks that the specified  integer number is nonegaive.
     * This method is designed primarily for doing parameter validation.
     * @param nums the array of parms that should be nonnegaive number.
     */
    protected static void requirePositiveNum(int... nums){
        for (int i : nums)
            if(i <= 0)
                throw new IllegalArgumentException("params should be nonnegaive number.");
    }
}




