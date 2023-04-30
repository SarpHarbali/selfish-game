package selfish.deck;

import selfish.Astronaut;
import selfish.GameException;

import java.io.*;
import java.util.*;

public abstract class Deck implements Serializable {
    @Serial
    private static final long serialVersionUID = 8079217286616866017L;
    private Collection<Card> cards = new LinkedList<>();

    protected Deck() {}

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

    public int add(Card card) {

        ((LinkedList<Card>)cards).addFirst(card);
        return cards.size();
    }

    protected int add(List<Card> cards) {
        for (Card card : cards) {
            ((LinkedList<Card>)cards).addFirst(card);
        }
        return cards.size();
    }

    public Card draw() {
        return ((LinkedList<Card>)cards).removeFirst();
    }

    public void remove(Card card) {
        cards.remove(card);
    }

    public void shuffle(Random random) {
        Collections.shuffle(((LinkedList<Card>)cards), random);
    }

    public LinkedList<Card> getCards() {
        return ((LinkedList<Card>)cards);
    }

    public int size() {
        return cards.size();
    }

}