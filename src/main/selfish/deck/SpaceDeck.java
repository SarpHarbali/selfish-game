package selfish.deck;

import selfish.GameException;

import java.util.List;

public class SpaceDeck extends Deck {
    private static String ASTEROID_FIELD = "Asteroid field";
    private static String BLANK_SPACE = "Blank space";
    private static String COSMIC_RADIATION = "Cosmic radiation";
    private static String GRAVITATIONAL_ANOMALY = "Gravitational anomaly";
    private static String HYPERSPACE = "Hyperspace";
    private static String METEOROID = "Meteoroid";
    private static String MYSTERIOUS_NEBULA = "Mysterious nebula";
    private static String SOLAR_FLARE = "solar flare";
    private static String USEFUL_JUNK = "useful junk";
    private static String WORMHOLE = "wormhole";

    public SpaceDeck() {}

    public SpaceDeck(String path) throws GameException {
        super();
        List<Card> game_deck_list = loadCards(path);
    }
}
