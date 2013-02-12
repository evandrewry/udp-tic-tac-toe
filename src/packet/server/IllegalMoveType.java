package packet.server;

public enum IllegalMoveType {
	OCCUPIED("O"), OUT_OF_TURN("T");

	private String code;

	private IllegalMoveType(String display) {
		this.code = display;
	}

	public String toString() {
		return code;
	}

	public String getCode() {
		return code;
	}
}
