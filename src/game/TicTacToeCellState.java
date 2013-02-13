package game;

public enum TicTacToeCellState {
	_(0), X(1), O(2);
	private int code;

	TicTacToeCellState(int code) {
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}
}
