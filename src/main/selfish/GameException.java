package selfish;

import java.io.Serial;
import java.io.Serializable;

/**
 * class for game exception
 * @author Sarp
 * @version 03/05
 */
public class GameException extends Exception implements Serializable {

    @Serial
    private static final long serialVersionUID = -1427492151881752877L;

    /**
     * exception const
     * @param message exception message
     * @param cause cause
     */
    public GameException(String message, Throwable cause) {
        super(message, cause);
    }
}
