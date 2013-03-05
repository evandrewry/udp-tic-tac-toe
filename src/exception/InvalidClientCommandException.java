package exception;

public class InvalidClientCommandException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidClientCommandException() {
        super();
    }

    public InvalidClientCommandException(String arg0) {
        super(arg0);
    }

    public InvalidClientCommandException(Throwable arg0) {
        super(arg0);
    }

    public InvalidClientCommandException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
