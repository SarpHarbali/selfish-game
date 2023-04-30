import java.io.Console;
import java.util.ArrayList;

import selfish.Astronaut;
import selfish.GameEngine;
import selfish.GameException;
import selfish.deck.Deck;

public class GameDriver {

    /**
     * A helper function to centre text in a longer String.
     * @param width The length of the return String.
     * @param s The text to centre.
     * @return A longer string with the specified text centred.
     */
    public static String centreString (int width, String s) {
        return String.format("%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

    public GameDriver() {
    }

    public static void main(String[] args) throws GameException {
        GameEngine ge = new GameEngine(22234142, "D:\\Documents\\Manchester\\Programming 2\\comp16412-coursework-2__j68216uh\\io\\ActionCards.txt", "D:\\Documents\\Manchester\\Programming 2\\comp16412-coursework-2__j68216uh\\io\\SpaceCards.txt");
        ge.addPlayer("sarp");
        ge.addPlayer("selin");
        ge.addPlayer("sena");

        /*
        Console con = System.console();
        ArrayList<Astronaut> players = new ArrayList<Astronaut>();
        ge.addPlayer(con.readLine("Player 1 name: "));
        ge.addPlayer(con.readLine("Player 2 name: "));

        while (true) {
            String another = con.readLine("Add another? Y or N: ");
            if (another.equals("N")) {
                break;
            }
            ge.addPlayer(con.readLine("Player name: "));
        }

         */
        ge.startGame();
        int k = 0;
        while (k<20) {
            ge.startTurn();
            System.out.println(ge.getCurrentPlayer());
            ge.endTurn();
            k++;
        }

        for (int i = 0; i< ge.fullPlayerCount(); i++) {
            System.out.printf("Player %d cards: %n \n", i+1);
            System.out.println(ge.getCurrentPlayer().getHandStr());
        }

    }

}