package selfish;

import java.io.Serial;
import java.io.Serializable;

public class Astronaut implements Serializable {
    @Serial
    private static final long serialVersionUID = -6268567598864260237L;
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
