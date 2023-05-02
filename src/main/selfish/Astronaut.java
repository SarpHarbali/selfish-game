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

    public int breathe() {
        for (Oxygen oxygen : oxygens) {
            if (oxygen.getValue() == 1) {
                this.game.getGameDiscard().add(oxygen);
                oxygens.remove(oxygen);
                break;
            }
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
        try {
            if (card instanceof Oxygen) {
                oxygens.remove(card);
            } else {
                actions.remove(card);
            }
            if (oxygens.size() == 0) {
                game.killPlayer(this);
            }
        } catch (IllegalStateException e) {
            throw new IllegalStateException();
        }

    }

    public Card hack(String card) {
        try {
            Card ca = null;
            for (Card c : getHand()) {
                if (c.toString().equals(card)) {
                    if (c instanceof Oxygen) {
                        oxygens.remove(c);
                    } else {
                        actions.remove(c);
                    }
                    ca = c;
                    break;

                }
            }
            if (oxygens.size() == 0) {
                game.killPlayer(this);
            }
            return ca;
        } catch (IllegalStateException e) {
            throw new IllegalStateException();
        }

    }

    public int hasCard(String card) {
        return getHand().stream().filter(x -> x.toString().equals(card)).toList().size();
    }

    public boolean hasMeltedEyeballs() {
        return peekAtTrack().toString().equals(SpaceDeck.SOLAR_FLARE);
    }

    public boolean hasWon() {
        return track.size() == 6;
    }

    public boolean isAlive() {
        return oxygens.size() > 0;
    }

    public Card laserBlast() {
        try {
            Card c = peekAtTrack();
            track.remove(peekAtTrack());
            return c;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
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
        return ((LinkedList<Card>) getTrack()).get(getTrack().size() - 1);
    }

    public Oxygen siphon() {
        Oxygen oxy = null;
        for (Oxygen oxygen : oxygens) {
            if (oxygen.getValue() == 1) {
                oxygens.remove(oxygen);
                oxy = oxygen;
                break;
            }
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
        Collection<Card> tempSwaper = track;
        Collection<Card> tempSwapee = swapee.getTrack();

        track.removeAll(getTrack());
        track.addAll(tempSwapee);

        swapee.getTrack().removeAll(swapee.getTrack());
        swapee.getTrack().addAll(tempSwaper);
    }

    public String toString(){
        if (this.isAlive()) {
            return name;
        } else{
            return name + " (is dead)";
        }

    }
}
