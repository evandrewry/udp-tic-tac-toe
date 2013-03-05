package exception;

/**
 * Thrown when a packet cannot be parsed, etc.
 *
 * @author evan
 *
 */
public class BadPacketException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BadPacketException() {
        super();
    }

    public BadPacketException(String message) {
        super(message);
    }

    public BadPacketException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadPacketException(Throwable cause) {
        super(cause);
    }

}
