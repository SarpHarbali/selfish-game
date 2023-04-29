package selfish.deck;

import java.io.Serial;
import java.io.Serializable;

public class Oxygen extends Card implements Serializable {
    @Serial
    private static final long serialVersionUID = -5396837397685805393L;
    private int value;

    public Oxygen(int value) {
        super("Oxygen", "This is an oxygen card");
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return super.toString();
    }
}
