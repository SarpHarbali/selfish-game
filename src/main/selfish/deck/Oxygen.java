package selfish.deck;

import java.io.Serial;
import java.io.Serializable;

/**
 * class for oxygen
 * @author Sarp
 * @version 03/05
 */
public class Oxygen extends Card implements Serializable, java.lang.Comparable {
    @Serial
    private static final long serialVersionUID = -5396837397685805393L;
    private int value;

    /**
     * const oxygen
     * @param value value of oxy
     */
    public Oxygen(int value) {
        super("Oxygen", "This is an oxygen card");
        this.value = value;
    }

    /**
     * get value
     * @return value
     */
    public int getValue() {
        return value;
    }

    /**
     * to string
     * @return String
     */
    public String toString() {
        return super.toString()+"("+getValue()+")";
    }

    /**
     * comparing oxygens
     * @param o the object to be compared.
     * @return oxygen
     */
    @Override
    public int compareTo(Object o) {
        if (o instanceof Oxygen) {
            return Integer.compare(this.value, ((Oxygen) o).value);
        } else {
            return super.compareTo(o);
        }

    }
}
