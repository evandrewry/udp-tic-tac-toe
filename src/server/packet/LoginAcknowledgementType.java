package server.packet;

import java.util.HashMap;
import java.util.Map;

public enum LoginAcknowledgementType {
	FAILURE("F"), SUCCESS("S");

	private String code;
	private static final Map<String, LoginAcknowledgementType> codeLookup = new HashMap<String, LoginAcknowledgementType>();
	static {
		for (LoginAcknowledgementType t : values()) {
			codeLookup.put(t.getCode(), t);
		}
	}
	
	LoginAcknowledgementType(String display) {
		this.code = display;
	}

	public String toString() {
		return code;
	}

	public String getCode() {
		return code;
	}

	public static LoginAcknowledgementType fromCode(String code) {
		return codeLookup.get(code);
	}
}
