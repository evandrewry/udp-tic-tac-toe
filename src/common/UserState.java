package common;

public enum UserState {
	AVAILABLE("A"), BUSY("B");
	private final String code;
	UserState(String code) {
		this.code = code;
	}
	public String toString() {
		return this.code;
	}
}
