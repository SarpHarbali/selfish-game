package selfish.deck;

import selfish.GameException;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class SpaceDeck extends Deck implements Serializable {
    @Serial
    public static final long serialVersionUID = 5685883857496540257L;
    public static final String ASTEROID_FIELD = "Asteroid field";
    public static final String BLANK_SPACE = "Blank space";
    public static final String COSMIC_RADIATION = "Cosmic radiation";
    public static final String GRAVITATIONAL_ANOMALY = "Gravitational anomaly";
    public static final String HYPERSPACE = "Hyperspace";
    public static final String METEOROID = "Meteoroid";
    public static final String MYSTERIOUS_NEBULA = "Mysterious nebula";
    public static final String SOLAR_FLARE = "solar flare";
    public static final String USEFUL_JUNK = "useful junk";
    public static final String WORMHOLE = "wormhole";

    public SpaceDeck() {}

    public SpaceDeck(String path) throws GameException {
        add(loadCards(path));
    }
}
