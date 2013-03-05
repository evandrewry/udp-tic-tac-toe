package game;

/**
 * Representation of a tic tac toe board
 * @author evan
 *
 */
public class TicTacToeBoard {
    private static int BOARD_WIDTH = 3;
    private static int BOARD_HEIGHT = 3;
    private TicTacToeCell[][] board = new TicTacToeCell[BOARD_HEIGHT][BOARD_WIDTH];

    /**
     * New blank tic tac toe board
     */
    public TicTacToeBoard() {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                board[i][j] = new TicTacToeCell();
            }
        }
    }

    /**
     * @param x
     * @param y
     * @return cell at x, y
     */
    public TicTacToeCell getCell(int x, int y) {
        assert x < BOARD_WIDTH;
        assert y < BOARD_HEIGHT;
        return board[x][y];
    }

    /**
     * @return true is the game is a draw
     */
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

    /**
     * Attempts to place an X or O on the board at the specified position
     *
     * @param x
     * @param y
     * @param xo
     * @return result of this move
     */
    public GameResultType maybeSetCell(int x, int y, TicTacToeCellState xo) {
        assert xo != TicTacToeCellState._;
        assert x < BOARD_WIDTH;
        assert y < BOARD_HEIGHT;

        board[y][x].maybeSet(xo);
        return result(x, y, xo);
    }

    /**
     * Attempts to place an X or O on the board at the specified position
     *
     * @param cellNo
     * @param xo
     * @return
     */
    public GameResultType maybeSetCell(int cellNo, TicTacToeCellState xo) {
        return maybeSetCell(cellNo % 3, cellNo / 3, xo);
    }

    /**
     * @param x
     * @param y
     * @param xo
     * @return result of a move that placed an x or o at this position
     */
    private GameResultType result(int x, int y, TicTacToeCellState xo) {
        assert xo != TicTacToeCellState._;
        assert x < BOARD_WIDTH;
        assert y < BOARD_HEIGHT;

        if (winsRow(y, xo) || winsColumn(x, xo) || winsDiagonal(x, y, xo)) {
            return GameResultType.WIN;
        } else if (isDraw()) {
            return GameResultType.DRAW;
        } else {
            return GameResultType.IN_PROGRESS;
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                s.append(board[i][j].getState().getCode());
            }
        }
        return s.toString();
    }

    /**
     * @param x
     * @param xo
     * @return true if this column has been filled with the state
     */
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

    /**
     * @param x
     * @param y
     * @param xo
     * @return true if a diagonal has been won by placing a x or o at the specified position
     */
    private boolean winsDiagonal(int x, int y, TicTacToeCellState xo) {
        assert xo != TicTacToeCellState._;
        assert x < BOARD_WIDTH;
        assert y < BOARD_HEIGHT;

        return winsMajorDiagonal(x, y, xo) || winsMinorDiagonal(x, y, xo);
    }

    /**
     * @param x
     * @param y
     * @param xo
     * @return true if the move wins the major diagonal
     */
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

    /**
     * @param x
     * @param y
     * @param xo
     * @return true if the move wins the minor diagonal
     */
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

    /**
     * @param y
     * @param xo
     * @return true if the specified row has been won
     */
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

}
