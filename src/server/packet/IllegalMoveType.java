package server.packet;

public enum IllegalMoveType {
	OCCUPIED("O"), OUT_OF_TURN("T");

	private String code;

	private IllegalMoveType(String code) {
		this.code = code;
	}

	public String toString() {
		return code;
	}

	public String getCode() {
		return code;
	}
}
