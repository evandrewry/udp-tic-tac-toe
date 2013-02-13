package exception;

public class InvalidCommandParametersException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidCommandParametersException() {
		super();
	}

	public InvalidCommandParametersException(String arg0) {
		super(arg0);
	}

	public InvalidCommandParametersException(Throwable arg0) {
		super(arg0);
	}

	public InvalidCommandParametersException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
