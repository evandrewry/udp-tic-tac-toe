package server.packet;

import java.util.HashMap;
import java.util.Map;

public enum IllegalMoveType {
	OCCUPIED("O"), OUT_OF_TURN("T");

	private String code;
	private static final Map<String, IllegalMoveType> codeLookup = new HashMap<String, IllegalMoveType>();
	static {
		for (IllegalMoveType t : values()) {
			codeLookup.put(t.getCode(), t);
		}
	}
	private IllegalMoveType(String code) {
		this.code = code;
	}

	public String toString() {
		return code;
	}

	public String getCode() {
		return code;
	}

	public static IllegalMoveType fromCode(String code) {
		return codeLookup.get(code);
	}
}
