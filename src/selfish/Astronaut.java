package selfish;

public class Astronaut {
    private String name;
    private GameEngine game;

    public Astronaut(String nameIn, GameEngine gameIn) {
        name = nameIn;
        game = gameIn;
    }

    public String toString(){
        return name;
    }
}
