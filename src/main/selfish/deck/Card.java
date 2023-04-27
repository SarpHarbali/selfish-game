package selfish.deck;

public class Card {
    private String name;
    private String description;

    public Card(String nameIn, String descriptionIn) {
        name = nameIn;
        description = descriptionIn;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getDiscription() {
        return description;
    }
}

