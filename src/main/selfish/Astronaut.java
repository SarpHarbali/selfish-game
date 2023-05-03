package selfish;
import selfish.deck.Card;
import selfish.deck.GameDeck;
import selfish.deck.Oxygen;
import selfish.deck.SpaceDeck;

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
    private Collection<Card> track = new LinkedList<>();

    public Astronaut(String name, GameEngine game) {
        this.name = name;
        this.game = game;
    }

    public List<Oxygen> getOxygens() {
        return oxygens;
    }

    public void addToHand(Card card) {
        if (card instanceof Oxygen) {
            this.oxygens.add((Oxygen) card);
        } else {
            this.actions.add(card);
        }

        oxygens.sort(Comparator.comparing(Oxygen::getValue, Comparator.reverseOrder()));
        actions.sort(Comparator.comparing(Card::toString));
    }

    public void addToTrack(Card card) {
        track.add(card);
    }

    public void die() {
        actions.clear();
        oxygens.clear();
    }


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

    public int distanceFromShip() {
        return 6 - getTrack().size();
    }

    public List<Card> getActions() {
        return actions;
    }

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

    public List<Card> getHand() {
        List<Card> hand = new ArrayList<>();
        hand.addAll(oxygens);
        hand.addAll(actions);
        hand.sort(Comparator.comparing(Card::toString));
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
        return track;
    }

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

    public void clearTrack() {
        track.clear();
    }

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

    public int hasCard(String card) {
        return getHand().stream().filter(x -> x.toString().equals(card)).toList().size();
    }

    public boolean hasMeltedEyeballs() {

        if (track.size() == 0) {
            throw new IllegalArgumentException();
        }
        return peekAtTrack().toString().equals("Solar flare");
    }

    public boolean hasWon() {
        return track.size() == 6 && oxygens.size() > 0;
    }

    public boolean isAlive() {
        return oxygens.size() > 0;
    }

    public Card laserBlast() {
        if (track.size() < 1) {
            throw new IllegalArgumentException();
        } else {
            Card c = peekAtTrack();
            track.remove(peekAtTrack());
            return c;
        }



    }

    public int oxygenRemaining() {
        int total = 0;
        for (Oxygen oxygen : oxygens) {
            total += oxygen.getValue();
        }
        return total;
    }

    public Card peekAtTrack() {
        if (getTrack().size() > 0) {
            Card c = ((LinkedList<Card>) getTrack()).get(getTrack().size() - 1);
            return c;
        } else {
            return null;
        }
    }

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

    public String toString(){
        if (this.isAlive()) {
            return name;
        } else{
            return name + " (is dead)";
        }

    }
}
