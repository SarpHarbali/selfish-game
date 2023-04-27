package selfish;

public class Astronaut {
    private String name;
    private GameEngine game;

    public Astronaut(String name, GameEngine game) {
        this.name = name;
        this.game = game;
    }

    public String toString(){
        return name;
    }
}
