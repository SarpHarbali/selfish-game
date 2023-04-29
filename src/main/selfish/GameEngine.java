package selfish;
import selfish.deck.GameDeck;
import selfish.deck.SpaceDeck;

import java.util.Random;

public class GameEngine {
    private Random random;

    private GameDeck gameDeck;

    private GameDeck gameDiscard;

    private SpaceDeck spaceDeck;

    private SpaceDeck spaceDiscard;

    private GameEngine() {}



    public GameEngine(long seed, String gameDeck, String spaceDeck) throws GameException {
        random = new Random(seed);
        this.gameDeck = new GameDeck(gameDeck);
        this.gameDiscard = new GameDeck();
        this.spaceDeck = new SpaceDeck(spaceDeck);
        this.spaceDiscard = new SpaceDeck();
    }
}
