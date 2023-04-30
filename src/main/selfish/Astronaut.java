package selfish;
import selfish.deck.Card;
import selfish.deck.Oxygen;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Astronaut implements Serializable {
    @Serial
    private static final long serialVersionUID = -6268567598864260237L;
    private String name;
    private GameEngine game;
    private List<Card> actions = new ArrayList<>();
    private List<Oxygen> oxygens = new ArrayList<>();
    private Collection<Card> track = new ArrayList<>();

    public Astronaut(String name, GameEngine game) {
        this.name = name;
        this.game = game;
    }

    public void addToHand(Card card) {
        if (card instanceof Oxygen) {
            this.oxygens.add((Oxygen) card);
        } else {
            actions.add(card);
        }

        oxygens.sort(Comparator.comparing(Oxygen::getValue));
        actions.sort(Comparator.comparing(Card::toString));
    }

    public void addToTrack(Card card) {

    }

    public int breathe() {
        return 0;
    }

    public int distanceFromShip() {
        return 0;
    }

    public List<Card> getActions() {
        return actions;
    }

    public String getActionsStr(boolean enumerated, boolean excludeShields) {
        List<String> actionString = new ArrayList<>();
        for(Card card : getActions()) {
            actionString.add(card.toString());
        }
        return String.join(",", actionString);
    }

    public List<Card> getHand() {
        List<Card> hand = new ArrayList<>();
        hand.addAll(oxygens);
        hand.addAll(actions);
        return hand;
    }

    public String getHandStr() {
        List<String> handString = new ArrayList<>();
        for(Card card : getHand()) {
            handString.add(card.toString());
        }
        return String.join(",", handString);
    }

    public Collection<Card> getTrack() {
        return null;
    }

    public void hack(Card card) {

    }

    public Card hack(String card) {
        return null;
    }

    public int hasCard(String card) {
        return 0;
    }

    public boolean hasMeltedEyeballs() {
        return false;
    }

    public boolean hasWon() {
        return false;
    }

    public boolean isAlive() {
        return false;
    }

    public Card laserBlast() {
        return null;
    }

    public int oxygenRemaining() {
        return 0;
    }

    public Card peekAtTrack() {
        return null;
    }

    public Oxygen siphon() {
        return null;
    }

    public Card steal() {
        return null;
    }

    public void swapTrack(Astronaut swapee) {

    }

    public String toString(){
        return name;
    }
}
