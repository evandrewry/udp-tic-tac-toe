package server.packet;

import java.util.HashMap;
import java.util.Map;

public enum PlayRequestAcknowledgementStatus {
	ACCEPTED("A"), DENY("D"), FAILURE("F");
	private String code;
	private static final Map<String, PlayRequestAcknowledgementStatus> codeLookup = new HashMap<String, PlayRequestAcknowledgementStatus>();
	static {
		for (PlayRequestAcknowledgementStatus t : values()) {
			codeLookup.put(t.getCode(), t);
		}
	}
	private PlayRequestAcknowledgementStatus(String code) {
		this.code = code;
	}

	public String toString() {
		return code;
	}

	public String getCode() {
		return code;
	}

	public static PlayRequestAcknowledgementStatus fromCode(String code) {
		return codeLookup.get(code);
	}

}
