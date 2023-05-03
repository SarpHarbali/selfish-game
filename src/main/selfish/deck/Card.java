package selfish.deck;

import java.io.Serial;
import java.io.Serializable;

/**
 * class for card
 * @author Sarp
 * @version 03/05
 */
public class Card implements Serializable, java.lang.Comparable {
    @Serial
    private static final long serialVersionUID = 2157053851976390896L;
    private String name;
    private String description;

    /**
     * card const
     * @param name card name
     * @param description card desc
     */
    public Card(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * to string method
     * @return String
     */
    public String toString() {
        return name;
    }

    /**
     * gets decs
     * @return desc String
     */
    public String getDescription() {
        return description;
    }


    /**
     * another compare to
     * @param o the object to be compared.
     * @return equal big or small
     */
    @Override
    public int compareTo(Object o) {
        return this.name.compareTo(((Card)o).name);
    }
}