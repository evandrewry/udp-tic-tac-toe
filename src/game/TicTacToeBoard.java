package game;

public class TicTacToeBoard {
	private static int BOARD_WIDTH = 3;
	private static int BOARD_HEIGHT = 3;
	private TicTacToeCell[][] board = new TicTacToeCell[BOARD_HEIGHT][BOARD_WIDTH];

	public TicTacToeBoard() {
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				board[i][j] = new TicTacToeCell();
			}
		}
	}

	public TicTacToeCell getCell(int x, int y) {
		assert x < BOARD_WIDTH;
		assert y < BOARD_HEIGHT;
		return board[x][y];
	}

	public GameResultType maybeSetCell(int cellNo, TicTacToeCellState xo) {
		return maybeSetCell(cellNo % 3, cellNo / 3, xo);
	}

	public GameResultType maybeSetCell(int x, int y, TicTacToeCellState xo) {
		assert xo != TicTacToeCellState._;
		assert x < BOARD_WIDTH;
		assert y < BOARD_HEIGHT;

		board[y][x].maybeSet(xo);
		return result(x, y, xo);
	}

	private GameResultType result(int x, int y, TicTacToeCellState xo) {
		assert xo != TicTacToeCellState._;
		assert x < BOARD_WIDTH;
		assert y < BOARD_HEIGHT;

		if (winsRow(y, xo) || winsColumn(x, xo) || winsDiagonal(x, y, xo)){
			return GameResultType.WIN;
		} else if (isDraw()) {
			return GameResultType.DRAW;
		} else {
			return GameResultType.IN_PROGRESS;
		}
	}

	private boolean isDraw() {
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				if (board[i][j].getState() == TicTacToeCellState._) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean winsDiagonal(int x, int y, TicTacToeCellState xo) {
		assert xo != TicTacToeCellState._;
		assert x < BOARD_WIDTH;
		assert y < BOARD_HEIGHT;

		return winsMajorDiagonal(x, y, xo) || winsMinorDiagonal(x, y, xo);
	}

	private boolean winsMinorDiagonal(int x, int y, TicTacToeCellState xo) {
		assert xo != TicTacToeCellState._;
		assert x < BOARD_WIDTH;
		assert y < BOARD_HEIGHT;
		
		if (x != -y + 2) { // not on minor diagonal
			return false;
		}
		
		for (int i = 0; i < BOARD_WIDTH; i++) {
			if (board[2 - i][i].getState() != xo) {
				return false;
			}
		}
		
		return true;
		
	}

	private boolean winsMajorDiagonal(int x, int y, TicTacToeCellState xo) {
		assert xo != TicTacToeCellState._;
		assert x < BOARD_WIDTH;
		assert y < BOARD_HEIGHT;

		if (x != y) { // not on major diagonal
			return false;
		}

		for (int i = 0; i < BOARD_WIDTH; i++) {
			if (board[i][i].getState() != xo) {
				return false;
			}
		}
		
		return true;
	}

	private boolean winsColumn(int x, TicTacToeCellState xo) {
		assert xo != TicTacToeCellState._;
		assert x < BOARD_WIDTH;

		// check column
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			if (board[i][x].getState() != xo) {
				return false;
			}
		}

		return true;
	}

	private boolean winsRow(int y, TicTacToeCellState xo) {
		assert xo != TicTacToeCellState._;
		assert y < BOARD_HEIGHT;

		boolean wins = true;

		// check row
		for (int i = 0; i < BOARD_WIDTH; i++) {
			if (board[y][i].getState() != xo) {
				wins = false;
			}
		}

		return wins;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				s.append(board[i][j].getState().getCode());
			}
		}
		return s.toString();
	}

}
