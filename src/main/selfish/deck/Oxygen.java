package selfish.deck;

public class Oxygen extends Card{
    private int value;

    public Oxygen(int valueIn) {
        super("Oxygen", "This is an oxygen card")
        value = valueIn;
    }

    public int getValue() {
        return value;
    }
}
