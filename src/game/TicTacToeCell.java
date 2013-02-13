package game;

public class TicTacToeCell {
	private TicTacToeCellState state = TicTacToeCellState._;

	public TicTacToeCellState getState() {
		return this.state;
	}

	public boolean maybeSet(TicTacToeCellState state) {
		if (this.state == TicTacToeCellState._) {
			this.state = state;
			return true;
		} else {
			return false;
		}
	}

	public boolean isSet() {
		return state != TicTacToeCellState._;
	}

}
