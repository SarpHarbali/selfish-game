package selfish.deck;

public class Oxygen extends Card{
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
