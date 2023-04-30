package selfish.deck;

import selfish.GameException;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class SpaceDeck extends Deck implements Serializable {
    @Serial
    public static final long serialVersionUID = 5685883857496540257L;
    public static String ASTEROID_FIELD = "Asteroid field";
    public static String BLANK_SPACE = "Blank space";
    public static String COSMIC_RADIATION = "Cosmic radiation";
    public static String GRAVITATIONAL_ANOMALY = "Gravitational anomaly";
    public static String HYPERSPACE = "Hyperspace";
    public static String METEOROID = "Meteoroid";
    public static String MYSTERIOUS_NEBULA = "Mysterious nebula";
    public static String SOLAR_FLARE = "solar flare";
    public static String USEFUL_JUNK = "useful junk";
    public static String WORMHOLE = "wormhole";

    public SpaceDeck() {}

    public SpaceDeck(String path) throws GameException {
        add(loadCards(path));
    }
}
