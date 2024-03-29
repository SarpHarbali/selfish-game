package selfish;
import selfish.deck.Card;
import selfish.deck.GameDeck;
import selfish.deck.Oxygen;
import selfish.deck.SpaceDeck;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * astronaut class
 * @author Sarp
 * @version 03/05
 */
public class Astronaut implements Serializable {

    @Serial
    private static final long serialVersionUID = -6268567598864260237L;
    private String name;
    private GameEngine game;
    private List<Card> actions = new ArrayList<>();
    private List<Oxygen> oxygens = new ArrayList<>();
    private Collection<Card> track = new LinkedList<>();

    /**
     * constructor for astronaut
     * @param name name
     * @param game game
     */
    public Astronaut(String name, GameEngine game) {
        this.name = name;
        this.game = game;
    }

    /**
     * get oxygens
     * @return returns oxygens
     */
    public List<Oxygen> getOxygens() {
        return oxygens;
    }

    /**
     * adds card to hand
     * @param card which card
     */
    public void addToHand(Card card) {
        if (card instanceof Oxygen) {
            this.oxygens.add((Oxygen) card);
        } else {
            this.actions.add(card);
        }

        oxygens.sort(Comparator.comparing(Oxygen::getValue, Comparator.reverseOrder()));
        actions.sort(Comparator.comparing(Card::toString));
    }

    /**
     * adds to track
     * @param card which card
     */
    public void addToTrack(Card card) {
        track.add(card);
    }

    /**
     * breaths
     * @return oxygen remaining
     */
    public int breathe() {
        for (Oxygen oxygen : oxygens) {
            if (oxygen.getValue() == 1) {
                if (oxygens.size() == 1) {
                    game.killPlayer(this);
                }
                this.game.getGameDiscard().add(oxygen);
                oxygens.remove(oxygen);
            } else {
                oxygens.add(new Oxygen(1));
                game.getGameDiscard().add(oxygen);
                oxygens.remove(oxygen);
            }
            break;
        }
        return oxygenRemaining();
    }

    /**
     * distance from ship
     * @return int distance
     */
    public int distanceFromShip() {
        return 6 - getTrack().size();
    }

    /**
     * get all the actions
     * @return actions
     */
    public List<Card> getActions() {
        return actions;
    }

    /**
     * get actions to string
     * @param enumerated bool
     * @param excludeShields bool
     * @return String
     */
    public String getActionsStr(boolean enumerated, boolean excludeShields) {
        List<Card> newAction = actions;
        if (excludeShields) {
            newAction.removeIf(act -> act.toString().equals(GameDeck.SHIELD));
        }

        StringBuilder sb = new StringBuilder();

        char c = 'A';
        int count = 0;
        while (count < newAction.size()) {
            if (hasCard(newAction.get(count).toString()) > 1 && !enumerated) {
                sb.append(hasCard(newAction.get(count).toString())).append("x ");
            }

            if (enumerated) {
                sb.append("[").append(c).append("] ");
            }
            sb.append(newAction.get(count).toString());
            count += hasCard(newAction.get(count).toString());
            if (count != newAction.size()) {
                sb.append(", ");
            }
            c++;
        }
        return sb.toString();
    }

    /**
     * get the hand
     * @return list of cards
     */
    public List<Card> getHand() {
        List<Card> hand = new ArrayList<>();
        hand.addAll(oxygens);
        hand.addAll(actions);
        hand.sort(Comparator.comparing(Card::toString));
        return hand;
    }

    /**
     * get hand string
     * @return String of hand
     */
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

    /**
     * gets track
     * @return collection
     */
    public Collection<Card> getTrack() {
        return track;
    }

    /**
     * hacks
     * @param card which card
     */
    public void hack(Card card) {
        if (!getHand().contains(card)) {
            throw new IllegalArgumentException();
        } else if (card == null) {
            throw new IllegalArgumentException();
        }
        else {
            if (card instanceof Oxygen) {
                oxygens.remove(card);
            } else {
                actions.remove(card);
            }
            if (oxygens.size() == 0) {
                game.killPlayer(this);
            }
        }


    }

    /**
     * clears track
     */
    public void clearTrack() {
        track.clear();
    }

    /**
     * hacks string
     * @param card which card string
     * @return hacked card
     */
    public Card hack(String card) {
        Card car = null;
        for (Card c : getHand()) {
            if (c.toString().equals(card)) {
                car = c;
                break;
            }
        }
        if (car==null) {
            throw new IllegalArgumentException();
        } else {
            if (car instanceof Oxygen) {
                oxygens.remove(car);
            } else {
                actions.remove(car);
            }
            if (oxygens.size() == 0) {
                game.killPlayer(this);
            }
        return car;
        }


    }

    /**
     * checks for card
     * @param card which card
     * @return how many
     */
    public int hasCard(String card) {
        return getHand().stream().filter(x -> x.toString().equals(card)).toList().size();
    }

    /**
     * has melted?
     * @return true or false
     */
    public boolean hasMeltedEyeballs() {

        if (track.size() == 0) {
            throw new IllegalArgumentException();
        }
        return peekAtTrack().toString().equals("Solar flare");
    }

    /**
     * has the player won?
     * @return true or false
     */
    public boolean hasWon() {
        return track.size() == 6 && oxygens.size() > 0;
    }

    /**
     * are they alive?
     * @return true or false
     */
    public boolean isAlive() {
        return oxygens.size() > 0;
    }

    /**
     * laser blast card
     * @return removed card
     */
    public Card laserBlast() {
        if (track.size() < 1) {
            throw new IllegalArgumentException();
        } else {
            Card c = peekAtTrack();
            track.remove(peekAtTrack());
            return c;
        }



    }

    /**
     * gets oxygen remaining
     * @return integer
     */
    public int oxygenRemaining() {
        int total = 0;
        for (Oxygen oxygen : oxygens) {
            total += oxygen.getValue();
        }
        return total;
    }

    /**
     * peek at track
     * @return Card
     */
    public Card peekAtTrack() {
        if (getTrack().size() > 0) {
            Card c = ((LinkedList<Card>) getTrack()).get(getTrack().size() - 1);
            return c;
        } else {
            return null;
        }
    }

    /**
     * siphon card
     * @return Oxygen card
     */
    public Oxygen siphon() {
        Oxygen oxy = null;
        for (Oxygen oxygen : oxygens) {
            if (oxygen.getValue() == 1) {
                oxygens.remove(oxygen);
                oxy = oxygen;
            } else {
                Oxygen[] oxyReturns = game.splitOxygen(oxygen);
                for (Oxygen o : oxyReturns) {
                    addToHand(o);
                }
                oxy = oxyReturns[0];
                oxygens.remove(oxygen);
                oxygens.remove(oxy);
            }
            break;
        }
        if (oxygens.size() == 0) {
            game.killPlayer(this);
        }
        return oxy;
    }

    /**
     * steal from hand
     * @return Card
     */
    public Card steal() {
        Card card = getHand().get(this.game.getRandom().nextInt(getHand().size()));
        if (getHand().size() == 1) {
            game.killPlayer(this);
        }
        if (card instanceof Oxygen) {
            oxygens.remove(card);
        } else {
            actions.remove(card);
        }
        return card;
    }

    /**
     * swaps track
     * @param swapee person to swap with
     */
    public void swapTrack(Astronaut swapee) {
        Collection<Card> tempSwaper = new LinkedList<>(track);
        Collection<Card> tempSwapee = swapee.getTrack();

        track.clear();
        track.addAll(tempSwapee);

        swapee.clearTrack();
        for (Card c : tempSwaper) {
            swapee.addToTrack(c);
        }
    }

    /**
     * change to string
     * @return String
     */
    public String toString(){
        if (this.isAlive()) {
            return name;
        } else{
            return name + " (is dead)";
        }

    }
}
