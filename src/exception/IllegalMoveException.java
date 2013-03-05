package exception;

/**
 * Thrown when a client makes an illegal move.
 * @author evan
 *
 */
public class IllegalMoveException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public IllegalMoveException() {
        super();
    }

    public IllegalMoveException(String message) {
        super(message);
    }

    public IllegalMoveException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalMoveException(Throwable cause) {
        super(cause);
    }

}
