package selfish.deck;

import selfish.GameException;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameDeck extends Deck implements Serializable {
    @Serial
    public static final long serialVersionUID = 7533881621621413554L;
    public static String HACK_SUIT = "Hack suit";
    public static String HOLE_IN_SUIT = "Hole in suit";
    public static String LASER_BLAST = "Laser blast";
    public static String OXYGEN = "Oxygen";
    public static String OXYGEN_1 = "Oxygen(1)";
    public static String OXYGEN_2 = "Oxygen(2)";
    public static String OXYGEN_SIPHON = "Oxygen syphon";
    public static String ROCKET_BOOSTER = "Rocket booster";
    public static String SHIELD = "Shield";
    public static String TETHER = "Tether";
    public static String TRACTOR_BEAM = "Tractor beam";

    public GameDeck() {}

    public GameDeck(String path) throws GameException {
        super.add(loadCards(path));
        for(int i = 0; i<10; i++) {
            super.add(new Oxygen(2));
        }
        for(int k = 0; k<38; k++) {
            super.add(new Oxygen(1));
        }
    }

    public Oxygen drawOxygen(int value) {
        Card card;
        while (true) {
            card = draw();
            if (card instanceof Oxygen && ((Oxygen) card).getValue() != value) {
                return (Oxygen) card;
            } else {
                add(card);
            }
        }
    }

    public Oxygen[] splitOxygen(Oxygen dbl) {
        return null;
    }
}
