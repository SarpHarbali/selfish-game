package selfish;
import selfish.deck.*;

import java.io.*;
import java.util.*;

public class GameEngine implements Serializable {

    @Serial
    private static final long serialVersionUID = -7253958447986048805L;

    private Queue<Astronaut> activePlayers = new LinkedList<>();
    private Astronaut currentPlayer;
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


    public int addPlayer(String player) {
        activePlayers.add(new Astronaut(player, this));
        return activePlayers.size();
    }

    public int endTurn() {
        activePlayers.offer(activePlayers.remove());
        return activePlayers.size();
    }

    public boolean gameOver() {
        return false;
    }

    public List<Astronaut> getAllPlayers() {
        return null;
    }

    public Astronaut getCurrentPlayer() {
        return currentPlayer;
    }

    public int fullPlayerCount() {
        return 0;
    }

    public GameDeck getGameDeck() {
        return this.gameDeck;
    }

    public GameDeck getGameDiscard() {
        return this.gameDiscard;
    }

    public SpaceDeck getSpaceDeck() {
        return this.spaceDeck;
    }

    public SpaceDeck getSpaceDiscard() {
        return this.spaceDiscard;
    }

    public Astronaut getWinner() {
        return null;
    }

    public void killPlayer(Astronaut corpse) {

    }

    public static GameEngine loadState(String path) throws GameException {
        try {
            FileInputStream fin = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fin);
            GameEngine g = (GameEngine) in.readObject();
            in.close();
            return g;
        } catch (IOException e) {
            throw new GameException("Error while loading data", e);
        } catch (ClassNotFoundException e) {
            throw new GameException("Error while finding class", e);
        }

    }

    public void mergeDecks(Deck deck1, Deck deck2) {

    }

    public void saveState(String path) throws GameException {
        try {
            FileOutputStream fout = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(this);
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new GameException("Error while saving data", e);
        }
    }

    public Oxygen[] splitOxygen(Oxygen dbl) {
        return null;
    }

    public void startTurn() {
        currentPlayer = activePlayers.peek();
    }

    public Card travel(Astronaut traveller) {
        return null;
    }

}
