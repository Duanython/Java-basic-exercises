package 实验三.content;

/**
 * A meaningless Exception class.
 * @author 段云飞
 * @since 2019-10-25
 */
public class XYZException extends Throwable {
    private static String xyzMessage = "This is a XYZ";

    public XYZException(){
        super(xyzMessage);
    }
}
