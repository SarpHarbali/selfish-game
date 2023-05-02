package selfish;
import selfish.deck.*;

import java.io.*;
import java.util.*;

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



    private GameEngine() {}

    public GameEngine(long seed, String gameDeck, String spaceDeck) throws GameException {
        random = new Random(seed);
        this.gameDeck = new GameDeck(gameDeck);
        this.gameDiscard = new GameDeck();
        this.spaceDeck = new SpaceDeck(spaceDeck);
        this.spaceDiscard = new SpaceDeck();
        this.gameDeck.shuffle(random);
        this.spaceDeck.shuffle(random);
    }


    public int addPlayer(String player) throws IllegalStateException {
        if (hasStarted || getFullPlayerCount() == 5) {
            throw new IllegalStateException();
        } else {

            ((LinkedList<Astronaut>)activePlayers).addFirst(new Astronaut(player, this));
            return activePlayers.size();
        }

    }

    public int endTurn() {
        startedTurn = false;
        ((LinkedList<Astronaut>)activePlayers).addFirst(currentPlayer);
        currentPlayer = null;
        return activePlayers.size();
    }

    public boolean gameOver() {
        for (Astronaut astronaut : activePlayers) {
            if (astronaut.hasWon()) {
                return true;
            }
        }
        return activePlayers.size() == 0;
    }

    public List<Astronaut> getAllPlayers() {
        return (LinkedList<Astronaut>) activePlayers;
    }

    public Astronaut getCurrentPlayer() {
        return currentPlayer;
    }

    public int getFullPlayerCount() {
        int total = 0;
        if (activePlayers.size() > 0) {
            total += activePlayers.size();
        }
        if (corpses.size() > 0) {
            total += corpses.size();
        }
        return total;
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
        for (Astronaut astronaut : activePlayers) {
            if (astronaut.hasWon()) {
                return astronaut;
            }
        }
        return null;
    }

    public void killPlayer(Astronaut corpse) {
        corpses.add(corpse);
        corpse.die();
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
        for (Card card : deck2.getCards()) {
            deck1.add(card);
        }
        deck2.clear();
        deck1.shuffle(random);
    }

    public Random getRandom() {
        return random;
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

        if (discardCount > 2) {
            oxies = gameDiscard.splitOxygen(dbl);
        } else if (deckCount > 2) {
            oxies = gameDeck.splitOxygen(dbl);
        } else if (discardCount == 1 && deckCount == 1) {
            mergeDecks(gameDeck, gameDiscard);
            oxies = gameDeck.splitOxygen(dbl);
        } else {
            throw new IllegalStateException();
        }

        return oxies;
    }

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

    public Collection<Astronaut> getActivePlayers() {
        return activePlayers;
    }

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

    public Card travel(Astronaut traveller) {
        if (traveller.getOxygens().size() < 2) {
            throw new IllegalStateException();
        } else {
            traveller.breathe();
            traveller.breathe();
            Card card = this.spaceDeck.draw();
            traveller.addToTrack(card);
            return card;

        }


    }

}
