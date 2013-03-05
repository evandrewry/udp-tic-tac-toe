package game;

import exception.IllegalMoveException;

/**
 * represents a cell in a tic tac toe board
 *
 * @author evan
 *
 */
public class TicTacToeCell {
    private TicTacToeCellState state = TicTacToeCellState._;

    public TicTacToeCellState getState() {
        return this.state;
    }

    public boolean isSet() {
        return state != TicTacToeCellState._;
    }

    /**
     * @param state
     * @return true if move succeeds, otherwise throws exception.
     */
    public boolean maybeSet(TicTacToeCellState state) {
        if (this.state == TicTacToeCellState._) {
            this.state = state;
            return true;
        } else {
            throw new IllegalMoveException();
        }
    }

}
