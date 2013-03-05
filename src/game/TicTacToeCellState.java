package game;

import java.util.HashMap;
import java.util.Map;

public enum TicTacToeCellState {
	_(0), X(1), O(2);
	private int code;
	private static final Map<Integer, TicTacToeCellState> codeLookup = new HashMap<Integer, TicTacToeCellState>();
	static {
		for (TicTacToeCellState t : values()) {
			codeLookup.put(t.getCode(), t);
		}
	}
	TicTacToeCellState(int code) {
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}
	
	public static TicTacToeCellState fromCode(String code) {
		return codeLookup.get(code);
	}
	
	
}
