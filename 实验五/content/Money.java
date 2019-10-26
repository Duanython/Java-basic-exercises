package 实验五.content;

/**
 * 半成品，未完成 mark
 */
public enum Money {
    Five(5),
    TEN(10),
    TWENTY(20);
    private int literal;

    Money(int literal) {
        this.literal = literal;
    }

    public int getLiteral() {
        return literal;
    }
}
