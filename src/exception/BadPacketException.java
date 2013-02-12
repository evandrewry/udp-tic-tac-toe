package exception;

public class BadPacketException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BadPacketException() {
        super();
    }

    public BadPacketException(String message) {
        super(message);
    }

    public BadPacketException(Throwable cause) {
        super(cause);
    }

    public BadPacketException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadPacketException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
