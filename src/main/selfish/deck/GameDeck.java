package selfish.deck;

import selfish.GameException;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class GameDeck extends Deck {
    @Serial
    private static final long serialVersionUID = 1088614975085601733L;
    public static final String HACK_SUIT = "Hack suit";
    public static final String HOLE_IN_SUIT = "Hole in suit";
    public static final String LASER_BLAST = "Laser blast";
    public static final String OXYGEN = "Oxygen";
    public static final String OXYGEN_1 = "Oxygen(1)";
    public static final String OXYGEN_2 = "Oxygen(2)";
    public static final String OXYGEN_SIPHON = "Oxygen syphon";
    public static final String ROCKET_BOOSTER = "Rocket booster";
    public static final String SHIELD = "Shield";
    public static final String TETHER = "Tether";
    public static final String TRACTOR_BEAM = "Tractor beam";


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
        Oxygen oxygen = null;
        for (Card card : getCards()) {
            if (card instanceof Oxygen && ((Oxygen) card).getValue() == value) {
                getCards().remove(card);
                oxygen = (Oxygen) card;
                break;
            }
        }
        if (oxygen == null) {
            throw new IllegalStateException();
        }
        return oxygen;
    }

    public Oxygen[] splitOxygen(Oxygen dbl) throws IllegalStateException {
        if (dbl.getValue() != 2) {
            throw new IllegalArgumentException();
        } else {
            add(dbl);

            Oxygen[] oxys = new Oxygen[2];
            int count = 0;

            for (Card card : getCards()) {
                if (card.toString().equals(OXYGEN_1) && count == 0) {
                    oxys[count] = (Oxygen) card;
                    count++;
                } else if ((card.toString().equals(OXYGEN_1) && count == 1)) {
                    oxys[count] = (Oxygen) card;
                    count++;
                }
            }
            if (count != 2) {
                throw new IllegalStateException();
            }
            remove(oxys[0]);
            remove(oxys[1]);
            return oxys;
        }

    }
}