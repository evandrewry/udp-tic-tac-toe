package common;

public class TicTacToeBoard {
	private int BOARD_WIDTH = 3;
	private int BOARD_HEIGHT = 3;
	private TicTacToeCell[][] board = new TicTacToeCell[BOARD_HEIGHT][BOARD_WIDTH];
	
	public TicTacToeBoard() {
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				board[i][j] = new TicTacToeCell();
			}
		}
	}
	
	public String toString() {
		return null;//TODO
	}
}
