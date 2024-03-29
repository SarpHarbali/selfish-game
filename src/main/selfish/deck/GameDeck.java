package selfish.deck;

import selfish.GameException;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * class for gamedeck
 * @author Sarp
 * @version 03/05
 */
public class GameDeck extends Deck {
    @Serial
    private static final long serialVersionUID = 1088614975085601733L;
    /**
     * hack suit
     */
    public static final String HACK_SUIT = "Hack suit";
    /**
     * hole in suit
     */
    public static final String HOLE_IN_SUIT = "Hole in suit";
    /**
     * laser blast
     */
    public static final String LASER_BLAST = "Laser blast";
    /**
     * oxygen
     */
    public static final String OXYGEN = "Oxygen";
    /**
     * oxygen 1
     */
    public static final String OXYGEN_1 = "Oxygen(1)";
    /**
     * oxygen 2
     */
    public static final String OXYGEN_2 = "Oxygen(2)";
    /**
     * siphon
     */
    public static final String OXYGEN_SIPHON = "Oxygen syphon";
    /**
     * rock boost
     */
    public static final String ROCKET_BOOSTER = "Rocket booster";
    /**
     * shield
     */
    public static final String SHIELD = "Shield";
    /**
     * tether
     */
    public static final String TETHER = "Tether";
    /**
     * tractor beam
     */
    public static final String TRACTOR_BEAM = "Tractor beam";

    /**
     * empty const
     */
    public GameDeck() {}

    /**
     * gamedeck const
     * @param path take path
     * @throws GameException except
     */
    public GameDeck(String path) throws GameException {
        super.add(loadCards(path));
        for(int i = 0; i<10; i++) {
            super.add(new Oxygen(2));
        }
        for(int k = 0; k<38; k++) {
            super.add(new Oxygen(1));
        }
    }

    /**
     * draw oxygen
     * @param value which oxygen value
     * @return Oxygen
     */
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

    /**
     * split the oxygen
     * @param dbl the double oxygen card
     * @return list of single oxygens
     * @throws IllegalStateException except
     */
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