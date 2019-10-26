package 实验一.content;

import java.util.LinkedList;
import java.util.List;

/**
 * {@code FeasibleSoluSet} 解实验指定的方程并返回解集。
 * <p><ul>
 *     <li>内部类 {@link Tuple} 以三元组的形式表示三元方程组的解</li>
 *     <li>缺点：没有设计出通用的解方程组的工具类</li>
 *     <li>缺点：没有设计出表示通用方程组的解的元组</li>
 * </ul></p>
 * @author 段云飞
 * @since 2019-10-24
 */
public final class FeasibleSoluSet {

    //Tool class does not need public constructor
    private FeasibleSoluSet(){}

    /**
     * XYZ + YZZ = 532
     * 其中XYZ、YZZ是一个数字，X、Y、Z可能取值为[0,9]
     * @return the solution set of specified equation
     */
    public static List<Tuple> compute(){
        List<Tuple> results = new LinkedList<>();
        for (int x = 0; x < 10; x++)
            for (int y = 0; y < 10; y++)
                for (int z = 0; z < 10; z++)
                    if(100 * x + 110 * y + 12 * z == 532)
                        results.add(new Tuple(x, y, z));
        return results;
    }

    public static class Tuple{
        private int x;
        private int y;
        private int z;

        /**
         * Constructs a triple with the value of x,y,z.
         * @param x the first value of this tripe.
         * @param y the second value of this tripe.
         * @param z the third value of this tripe.
         */
        public Tuple(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        /**
         * the first value of this tripe.
         * @return the value of X.
         */
        public int getX() {
            return x;
        }

        /**
         * the second value of this tripe.
         * @return the value of Y.
         */
        public int getY() {
            return y;
        }

        /**
         * the third value of this tripe.
         * @return the value of Z.
         */
        public int getZ() {
            return z;
        }

        /**
         * Returns a string representation of the contents of the specified triple.
         * @return a string representation of this triple.
         */
        @Override
        public String toString() {
            return String.format("(%d, %d, %d)",x, y, z);
        }
    }
}
