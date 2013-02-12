package game;

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

    public boolean maybeSetCell(int cellNo, TicTacToeCellState xo) {
        return maybeSetCell(cellNo % 3, cellNo / 3, xo);
    }

    public boolean maybeSetCell(int x, int y, TicTacToeCellState xo) {
        assert xo != TicTacToeCellState._;
        assert x < 3;
        assert y < 3;
        return wins(x, y, xo) || board[y][x].maybeSet(xo);
    }

    private boolean wins(int x, int y, TicTacToeCellState xo) {
        return false;//TODO
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
