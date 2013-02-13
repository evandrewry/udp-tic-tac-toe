package game;

public enum GameResultType {
	WIN("W"), LOSS("L"), DRAW("D");

	private String code;

	private GameResultType(String code) {
		this.code = code;
	}

	public String toString() {
		return code;
	}

	public String getCode() {
		return code;
	}

	public static GameResultType fromCode(String group) {
		// TODO Auto-generated method stub
		return null;
	}
}
