package 实验五.content;

/**
 * {@code People} 是现实世界中顾客的抽象
 * <p><ul>
 * <li> {@code People} 与 {@link Wallet} 复合，模拟
 * 现实世界顾客的部分属性，如姓名，持有钱包。</li>
 * <li>缺点：{@code People} 是面向对象程序设计过于
 * 模拟现实世界的产物，有利于客观世界的表示，但一定
 * 程度上增加了代码量，甚至增加了程序运行开销。</li>
 * </ul></p>
 *
 * @author 段云飞
 * @since 2019-11-26
 */

public class People {
    private Wallet wallet;
    private String name;

    /**
     * Constructs a newly allocated People object with the necessary information
     *
     * @param name   his (her) name
     * @param wallet his (her) wallet
     */
    public People(String name, Wallet wallet) {
        this.name = name;
        this.wallet = wallet;
    }

    /**
     * Returns his (her) name.
     *
     * @return his (her) name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns his (her) wallet
     *
     * @return his (her) wallet
     */
    public Wallet getWallet() {
        return wallet;
    }
}