package selfish;

import java.io.Serial;
import java.io.Serializable;

public class GameException extends Exception implements Serializable {

    @Serial
    private static final long serialVersionUID = -1427492151881752877L;

    public GameException(String message, Throwable cause) {
        super(message, cause);
    }
}
