package selfish.deck;

import selfish.GameException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Deck {

    private Collection<Card> cards;
    protected Deck() {

    }

    protected static List<Card> loadCards(String path) throws GameException {
        List<Card> card_list = new ArrayList<>();
        try {
            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));

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
}