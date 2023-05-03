package selfish.deck;

import java.io.Serial;
import java.io.Serializable;

public class Card implements Serializable, java.lang.Comparable {
    @Serial
    private static final long serialVersionUID = 2157053851976390896L;
    private String name;
    private String description;

    public Card(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public int compareTo(Card c) {
        return this.name.compareTo(c.name);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}