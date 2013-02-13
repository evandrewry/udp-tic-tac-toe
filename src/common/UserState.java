package common;

import java.util.HashMap;
import java.util.Map;

public enum UserState {
	FREE("F"), BUSY("B"), DECISION("D"), OFFLINE("O");

	private final String code;

	private static final Map<String, UserState> codeLookup = new HashMap<String, UserState>();
	static {
		for (UserState t : values()) {
			codeLookup.put(t.getCode(), t);
		}
	}

	UserState(String code) {
		this.code = code;
	}

	public String toString() {
		return this.code;
	}

	public String getCode() {
		return this.code;
	}

	public static UserState fromCode(String code) {
		return codeLookup.get(code);
	}
}
