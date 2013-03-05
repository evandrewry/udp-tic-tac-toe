package game;

import common.Pair;
import common.User;
import common.UserState;

/**
 * Representation of a tic tac toe game between two users
 * @author evan
 *
 */
public class Game {
    private Pair<User, User> players;
    private TicTacToeBoard state;
    private boolean turn;

    /**
     * create a new game between these two players
     * @param p1
     * @param p2
     */
    public Game(User p1, User p2) {
        this.players = new Pair<User, User>(p1, p2);
        this.state = new TicTacToeBoard();
        p1.setCurrentGame(this);
        p2.setCurrentGame(this);

        this.turn = true;
    }

    /**
     * @return the players
     */
    public Pair<User, User> getPlayers() {
        return players;
    }

    /**
     * @param u
     * @return true if it is the users turn
     */
    public boolean isTurn(User u) {
        return turn ? u == players.getLeft() : u == players.getRight();
    }

    /**
     * toggles the turn variable
     */
    private void nextTurn() {
        turn = !turn;
    }

    /**
     * @param u
     * @return the other player in the game
     */
    public User otherPlayer(User u) {
        return u == players.getLeft() ? players.getRight() : players.getLeft();
    }

    /**
     * @param position
     * @return the result of this move
     */
    public GameResultType play(int position) {
        GameResultType ret = state.maybeSetCell(position - 1, turn ? TicTacToeCellState.X : TicTacToeCellState.O);
        nextTurn();
        return ret;
    }

    /**
     * terminates the game and sets the users' states to FREE
     */
    public void terminate() {
        players.getLeft().setCurrentGame(null);
        players.getLeft().setState(UserState.FREE);
        players.getRight().setCurrentGame(null);
        players.getRight().setState(UserState.FREE);

    }

    @Override
    public String toString() {
        return state.toString();
    }
}
