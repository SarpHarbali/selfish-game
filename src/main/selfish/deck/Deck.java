package selfish.deck;

import selfish.Astronaut;
import selfish.GameException;

import java.io.*;
import java.util.*;

/**
 * @author Sarp
 * @version 03/05
 */
public abstract class Deck implements Serializable {
    @Serial
    private static final long serialVersionUID = 8079217286616866017L;
    private Collection<Card> cards = new LinkedList<>();

    /**
     * empty const
     */
    protected Deck() {}

    /**
     * load all the cards
     * @param path from where
     * @return list of cards
     * @throws GameException except
     */
    protected static List<Card> loadCards(String path) throws GameException {
        List<Card> card_list = new ArrayList<>();
        try {
            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                int length = stringToCards(line).length;
                for (int i=0; i < length; i++) {
                    card_list.add(stringToCards(line)[i]);
                }
            }
        } catch (IOException e) {
            throw new GameException("Error opening the file!", e);
        }

        return card_list;
    }

    /**
     * convert string to cards
     * @param str which string
     * @return list of cards
     */
    protected static Card[] stringToCards(String str) {
        String[] parts = str.split(";", 0);
        String card_name = parts[0].trim();
        String card_desc = parts[1].trim();
        int card_count = Integer.parseInt(parts[2].trim());
        Card[] card_array = new Card[card_count];
        for (int i=0; i<card_count; i++) {
            card_array[i] = new Card(card_name, card_desc);
        }
        return card_array;
    }

    /**
     * add card
     * @param card which card
     * @return no of cards
     */
    public int add(Card card) {

        ((LinkedList<Card>)cards).addLast(card);
        return cards.size();
    }

    /**
     * add list of cards
     * @param cards card list
     * @return no of cards
     */
    protected int add(List<Card> cards) {
        for (Card card : cards) {
            ((LinkedList<Card>)this.cards).addLast(card);
        }
        return cards.size();
    }

    /**
     * draw card
     * @return card drawn
     */
    public Card draw() {
        if (cards.size() == 0) {
            throw new IllegalStateException();
        }
        return ((LinkedList<Card>)cards).removeLast();
    }

    /**
     * remove card
     * @param card which card to remove
     */
    public void remove(Card card) {
        cards.remove(card);
    }

    /**
     * clear the cards
     */
    public void clear() {
        cards.clear();
    }

    /**
     * shuffle the cards
     * @param random random
     */
    public void shuffle(Random random) {
        Collections.shuffle(((LinkedList<Card>)cards), random);
    }

    /**
     * get the cards
     * @return list of cards
     */
    public LinkedList<Card> getCards() {
        return ((LinkedList<Card>)cards);
    }

    /**
     * size of cards
     * @return size
     */
    public int size() {
        return cards.size();
    }

}