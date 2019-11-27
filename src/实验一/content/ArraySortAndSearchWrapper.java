package 实验一.content;

import java.util.Arrays;
import java.util.Comparator;

/**
 * {@code ArraySortAndSearchWrapper} 包裹了实验要求的两个方法，学习了Comparator接口
 * <p><ul>
 *     <li>缺点：Comparator接口的静态方法使用很不熟练。</li>
 * </ul></p>
 * @author 段云飞
 * @since 2019-10-25
 */
public final class ArraySortAndSearchWrapper{
    //Tool class does not need public constructor
    private ArraySortAndSearchWrapper(){}

    /**
     * Sorts and search a range of the specified array.
     * @param a the array to be searched
     * @param fromIndex the index of the first element (inclusive) to be searched
     * @param toIndex  the index of the last element (exclusive) to be searched
     * @param key the value to be searched for
     * @param c the comparator by which the array is ordered. A null value indicates
     *          that the elements' natural ordering should be used.
     * @param <T> the type of elements in this array.
     * @return index of the search key, if it is contained in the array within the
     * specified range; otherwise, (-(insertion point) - 1). The insertion point is
     * defined as the point at which the key would be inserted into the array: the
     * index of the first element in the range greater than the key, or toIndex if
     * all elements in the range are less than the specified key. Note that this
     * guarantees that the return value will be >= 0 if and only if the key is found.
     * @throws ClassCastException  if the search key is not comparable to the elements
     * of the array within the specified range.
     * @throws IllegalArgumentException if fromIndex > toIndex
     * @throws ArrayIndexOutOfBoundsException if fromIndex < 0 or toIndex > a.length
     */
    public static <T> int sortandsearch(T[] a, int fromIndex, int toIndex, T key, Comparator<? super T> c){
        Arrays.sort(a, fromIndex, toIndex, c);
        return Arrays.binarySearch(a, fromIndex, toIndex, key, c);
    }
}
