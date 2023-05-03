package selfish.deck;

import selfish.GameException;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * class for space deck
 * @author Sarp
 * @version 03/05
 */
public class SpaceDeck extends Deck {
    @Serial
    private static final long serialVersionUID = -8071367678962477750L;
    /**
     * asteroid field
     */
    public static final String ASTEROID_FIELD = "Asteroid field";
    /**
     * blank space
     */
    public static final String BLANK_SPACE = "Blank space";
    /**
     * cosmic rad
     */
    public static final String COSMIC_RADIATION = "Cosmic radiation";
    /**
     * grav an
     */
    public static final String GRAVITATIONAL_ANOMALY = "Gravitational anomaly";
    /**
     * hyperspace
     */
    public static final String HYPERSPACE = "Hyperspace";
    /**
     * meteroid
     */
    public static final String METEOROID = "Meteoroid";
    /**
     * myst neb
     */
    public static final String MYSTERIOUS_NEBULA = "Mysterious nebula";
    /**
     * solar flare
     */
    public static final String SOLAR_FLARE = "solar flare";
    /**
     * useful junk
     */
    public static final String USEFUL_JUNK = "useful junk";
    /**
     * wormhole
     */
    public static final String WORMHOLE = "wormhole";

    /**
     * empty const for spacedeck
     */
    public SpaceDeck() {}

    /**
     * space deck const
     * @param path which path to take
     * @throws GameException except
     */
    public SpaceDeck(String path) throws GameException {
        add(loadCards(path));
    }
}
