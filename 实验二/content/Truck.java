package 实验二.content;
/**
 * {Truck} 表示现实生活中的卡车
 * <p><ul>
 *     <li>对输入参数进行了有效性验证</li>
 *     <li>缺点：该类的实用性和功能性仍有待完善。</li>
 * </ul></p>
 * @author 段云飞
 * @since 2019-10-24
 */
public class Truck extends Car{
    private int payload;

    /**
     * Initializes a newly Truck object with a specific value.
     * @param wheels the wheels of this truck.
     * @param weight the weight of this truck.
     * @param loader the loader of this truck.
     * @param payload the payload of this truck.
     * @throws IllegalArgumentException params should be positive number.
     */
    public Truck(int wheels, int weight, int loader, int payload) {
        super(wheels, weight, loader);
        Vehicle.requirePositiveNum(payload);
        this.payload = payload;
    }

    /**
     * return the payload of this Truck object.
     * @return the payload of this Truck object.
     */
    public int getPayload() {
        return payload;
    }
}
