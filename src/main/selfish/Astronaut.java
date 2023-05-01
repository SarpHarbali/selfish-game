package selfish;
import selfish.deck.Card;
import selfish.deck.GameDeck;
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

        oxygens.sort(Comparator.comparing(Oxygen::getValue, Comparator.reverseOrder()));
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
        StringBuilder sb = new StringBuilder();

        int count = 0;
        while (count < actions.size()) {
            if (hasCard(actions.get(count).toString()) > 1) {
                sb.append(hasCard(actions.get(count).toString())).append("x ");
            }
            sb.append(actions.get(count).toString());
            count += hasCard(actions.get(count).toString());
            if (count != actions.size()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public List<Card> getHand() {
        List<Card> hand = new ArrayList<>();
        hand.addAll(oxygens);
        hand.addAll(actions);
        return hand;
    }

    public String getHandStr() {
        StringBuilder sb = new StringBuilder();

        int hop = 0;
        while (hop < oxygens.size()) {
            if (hasCard(oxygens.get(hop).toString()) > 1) {
                sb.append(hasCard(oxygens.get(hop).toString())).append("x ");
            }
            sb.append(oxygens.get(hop).toString());
            hop += hasCard(oxygens.get(hop).toString());
            if (hop != oxygens.size()) {
                sb.append(", ");
            } else {
                sb.append("; ");
            }
        }

        int count = 0;
        while (count < actions.size()) {
            if (hasCard(actions.get(count).toString()) > 1) {
                sb.append(hasCard(actions.get(count).toString())).append("x ");
            }
            sb.append(actions.get(count).toString());
            count += hasCard(actions.get(count).toString());
            if (count != actions.size()) {
                sb.append(", ");
            }
        }
        return sb.toString();
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
        return getHand().stream().filter(x -> x.toString().equals(card)).toList().size();
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
