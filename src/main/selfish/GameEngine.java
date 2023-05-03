package selfish;
import selfish.deck.*;

import java.io.*;
import java.util.*;

/**
 * @author Sarp
 * @version 03/05
 */
public class GameEngine implements Serializable {

    @Serial
    private static final long serialVersionUID = -7253958447986048805L;

    private Collection<Astronaut> activePlayers = new LinkedList<>();
    private List<Astronaut> corpses = new ArrayList<>();
    private Astronaut currentPlayer;
    private boolean hasStarted;
    private Random random;
    private GameDeck gameDeck;
    private GameDeck gameDiscard;
    private SpaceDeck spaceDeck;
    private SpaceDeck spaceDiscard;
    private boolean startedTurn;


    /**
     * empty const
     */
    private GameEngine() {}

    /**
     * constructor for game engine
     * @param seed random
     * @param gameDeck which deck
     * @param spaceDeck which deck
     * @throws GameException except
     */
    public GameEngine(long seed, String gameDeck, String spaceDeck) throws GameException {
        random = new Random(seed);
        this.gameDeck = new GameDeck(gameDeck);
        this.gameDiscard = new GameDeck();
        this.spaceDeck = new SpaceDeck(spaceDeck);
        this.spaceDiscard = new SpaceDeck();
        this.gameDeck.shuffle(random);
        this.spaceDeck.shuffle(random);
    }

    /**
     * adds players
     * @param player which player
     * @return how many players
     * @throws IllegalStateException except
     */
    public int addPlayer(String player) throws IllegalStateException {
        if (hasStarted || getFullPlayerCount() == 5) {
            throw new IllegalStateException();
        } else {

            ((LinkedList<Astronaut>)activePlayers).addFirst(new Astronaut(player, this));
            return activePlayers.size();
        }

    }

    /**
     * ends turn
     * @return no of players
     */
    public int endTurn() {
        startedTurn = false;
        if (currentPlayer.isAlive()) {
            ((LinkedList<Astronaut>)activePlayers).addFirst(currentPlayer);
        }
        currentPlayer = null;
        return activePlayers.size();
    }

    /**
     * is game over?
     * @return bool
     */
    public boolean gameOver() {
        for (Astronaut astronaut : activePlayers) {
            if (astronaut.hasWon()) {
                return true;
            }
        }
        return activePlayers.size() == 0;
    }

    /**
     * get all the players
     * @return list of players
     */
    public List<Astronaut> getAllPlayers() {
        List<Astronaut> ast = new ArrayList<>();
        if (activePlayers.size() > 0) {
            for (Astronaut a : activePlayers) {
                ast.add(a);
            }
        }
        if (corpses.size() > 0) {
            for (Astronaut a : corpses) {
                ast.add(a);
            }
        }
        if (currentPlayer != null && !corpses.contains(currentPlayer)) {
            ast.add(currentPlayer);
        }
        return ast;
    }

    /**
     * get current player
     * @return the player
     */
    public Astronaut getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * get full count
     * @return no of players
     */
    public int getFullPlayerCount() {
        return getAllPlayers().size();
    }

    /**
     * get game deck
     * @return gamedeck
     */
    public GameDeck getGameDeck() {
        return this.gameDeck;
    }

    /**
     * get game discard
     * @return game discard
     */
    public GameDeck getGameDiscard() {
        return this.gameDiscard;
    }

    /**
     * get space deck
     * @return space deck
     */
    public SpaceDeck getSpaceDeck() {
        return this.spaceDeck;
    }

    /**
     * get space discard
     * @return space discard
     */
    public SpaceDeck getSpaceDiscard() {
        return this.spaceDiscard;
    }

    /**
     * get the winner
     * @return winner
     */
    public Astronaut getWinner() {
        for (Astronaut astronaut : activePlayers) {
            if (astronaut.hasWon()) {
                return astronaut;
            }
        }
        return null;
    }

    /**
     * kill the player
     * @param corpse which player to kill
     */
    public void killPlayer(Astronaut corpse) {
        this.activePlayers.remove(corpse);
        this.corpses.add(corpse);
        corpse.getHand().clear();
        corpse.getActions().clear();
    }

    /**
     * loads cards
     * @param path uses path
     * @return the whole engine
     * @throws GameException except
     */
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

    /**
     * merge two decks
     * @param deck1 deck
     * @param deck2 discard
     */
    public void mergeDecks(Deck deck1, Deck deck2) {
        for (Card card : deck2.getCards()) {
            deck1.add(card);
        }
        deck2.clear();
        deck1.shuffle(random);
    }

    /**
     * get random
     * @return random
     */
    public Random getRandom() {
        return random;
    }

    /**
     * save the state
     * @param path to where
     * @throws GameException except
     */
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

    /**
     * splits the oxygen
     * @param dbl double ox
     * @return list of single ox
     */
    public Oxygen[] splitOxygen(Oxygen dbl) {
        Oxygen[] oxies = new Oxygen[2];

        int deckCount = 0;
        int discardCount = 0;

        for (Card card : gameDeck.getCards()) {
            if (card.toString().equals(GameDeck.OXYGEN_1)) {
                deckCount++;
            }
        }

        for (Card card : gameDiscard.getCards()) {
            if (card.toString().equals(GameDeck.OXYGEN_1)) {
                discardCount++;
            }
        }

        if (discardCount > 1) {
            oxies = gameDiscard.splitOxygen(dbl);
        } else if (deckCount > 1) {
            oxies = gameDeck.splitOxygen(dbl);
        } else if (discardCount == 1 && deckCount == 1) {
            mergeDecks(gameDeck, gameDiscard);
            oxies = gameDeck.splitOxygen(dbl);
        } else {
            mergeDecks(gameDeck, gameDiscard);
            throw new IllegalStateException();
        }

        return oxies;
    }

    /**
     * start the game
     */
    public void startGame() {
        if (getFullPlayerCount() == 1 || getFullPlayerCount() == 6) {
            throw new IllegalStateException();
        }
        if (hasStarted) {
            throw new IllegalStateException();
        }
        hasStarted = true;

        for (int i = 0; i< activePlayers.size(); i++) {
            startTurn();
            currentPlayer.addToHand(this.gameDeck.drawOxygen(2));
            currentPlayer.addToHand(this.gameDeck.drawOxygen(1));
            currentPlayer.addToHand(this.gameDeck.drawOxygen(1));
            currentPlayer.addToHand(this.gameDeck.drawOxygen(1));
            currentPlayer.addToHand(this.gameDeck.drawOxygen(1));
            endTurn();
        }

        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < activePlayers.size(); k++) {
                startTurn();
                currentPlayer.addToHand(this.gameDeck.draw());
                endTurn();
            }

        }


    }

    /**
     * get all the active players
     * @return ast collection
     */
    public Collection<Astronaut> getActivePlayers() {
        return activePlayers;
    }

    /**
     * starts turn for astronaut
     */
    public void startTurn() {
        if (gameOver() || !hasStarted || startedTurn) {
            throw new IllegalStateException();
        } else{
            startedTurn = true;
            ((LinkedList<Astronaut>)activePlayers).addFirst(((LinkedList<Astronaut>)activePlayers).removeLast());
            currentPlayer = ((LinkedList<Astronaut>)activePlayers).peek();
            ((LinkedList<Astronaut>)activePlayers).removeFirst();
        }

    }

    /**
     * travel in space
     * @param traveller which player
     * @return space card
     */
    public Card travel(Astronaut traveller) {
        if (traveller.getOxygens().size() < 2) {
            throw new IllegalStateException();
        } else {
            traveller.breathe();
            traveller.breathe();
            Card card = this.spaceDeck.draw();
            if (card.toString().equals(SpaceDeck.GRAVITATIONAL_ANOMALY)) {
                ((LinkedList<Card>)traveller.getTrack()).remove(traveller.getTrack().size() -1);
            }
            traveller.addToTrack(card);
            return card;

        }


    }

}
