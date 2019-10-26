package 实验二.content;

/**
 * 将 {@code Coordinate} 作为 {@code Rectangle} 、
 * {@code Circle}、{@code Triangle} 的基类是非常
 * 不合理的设计。
 * <p>
 *     作为折中的办法：<ul>
 *         <li>删除实验提示代码给定的构造器</li>
 *         <li>永不使用令人混淆的字段{@code x} 与 {@code y}</li>
 *     </ul>
 * </p>
 * @author 段云飞
 * @since 2019-10-24
 */
public class Coordinate{
    private double x;
    private double y;

    /**
     * The length of any side of the shape should be positive.
     * @param nums the array of side of the shape.
     */
    public static void requirePositiveNum(double... nums){
        //Coordinate的子类均需调用该方法，因此就将它提取到它们的公共基类Coordinated中。
        for (double i : nums)
            if(Double.compare(i, 0) <= 0)
                throw new IllegalArgumentException("params should be nonnegaive number.");
    }
}