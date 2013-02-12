package common;

public class TicTacToeCell {
	private TicTacToeCellState val = null;

	public TicTacToeCellState get() {
		return this.val;
	}

	public boolean maybeSet(TicTacToeCellState val) {
		if (this.val == null) {
			this.val = val;
			return true;
		} else {
			return false;
		}
	}
}
