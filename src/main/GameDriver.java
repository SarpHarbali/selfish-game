import java.io.Console;
import java.util.ArrayList;

import selfish.Astronaut;
import selfish.GameEngine;
import selfish.GameException;

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
        Console con = System.console();
        ArrayList<Astronaut> players = new ArrayList<Astronaut>();
        players.add(new Astronaut(con.readLine("Player 1 name: "), ge));
        players.add(new Astronaut(con.readLine("Player 2 name: "), ge));
        while (true) {
            String another = con.readLine("Add another? Y or N: ");
            if (another.equals("N")) {
                break;
            }
            players.add(new Astronaut(con.readLine("New player name: "), ge));
        }

        for (Astronaut a:players) {
            System.out.printf(a.toString());
        }
        

    }

}