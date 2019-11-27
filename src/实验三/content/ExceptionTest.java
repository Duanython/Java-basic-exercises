package 实验三.content;
/**
 * {@code ExceptionTest} 是实验给定的源代码中的类。
 * 用于测试 {@link ArrayIndexOutOfBoundsException}
 */
public class ExceptionTest {

    public static void main(String[] args) {
        int i = 0;
        String greeting[] = {"Hello", "Only", "Test"};
        while (i < 4){
            try {
                System.out.println(greeting[i]);
            }
            catch (ArrayIndexOutOfBoundsException e){
                System.out.println("数组越界");
            }
            finally {
                System.out.println("总会运行");
            }
            i++;
        }
    }
}
