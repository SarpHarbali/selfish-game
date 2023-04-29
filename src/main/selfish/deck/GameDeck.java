package selfish.deck;

import selfish.GameException;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class GameDeck extends Deck implements Serializable {
    @Serial
    private static final long serialVersionUID = 7533881621621413554L;
    private static String HACK_SUIT = "Hack suit";
    private static String HOLE_IN_SUIT = "Hole in suit";
    private static String LASER_BLAST = "Laser blast";
    private static String OXYGEN = "Oxygen";
    private static String OXYGEN_1 = "Oxygen(1)";
    private static String OXYGEN_2 = "Oxygen(2)";
    private static String OXYGEN_SIPHON = "Oxygen syphon";
    private static String ROCKET_BOOSTER = "Rocket booster";
    private static String SHIELD = "Shield";
    private static String TETHER = "Tether";
    private static String TRACTOR_BEAM = "Tractor beam";

    public GameDeck() {}

    public GameDeck(String path) throws GameException {
        super();
        List<Card> game_deck_list = loadCards(path);
    }
}
