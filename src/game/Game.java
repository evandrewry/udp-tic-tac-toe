package game;

import common.Pair;
import common.User;
import common.UserState;

public class Game {
	private Pair<User, User> players;
	private TicTacToeBoard state;
	private boolean turn;

	public Game(User p1, User p2) {
		this.players = new Pair<User, User>(p1, p2);
		this.state = new TicTacToeBoard();
		p1.setCurrentGame(this);
		p2.setCurrentGame(this);

		this.turn = true;
	}

	public Pair<User, User> getPlayers() {
		return players;
	}

	public String toString() {
		return state.toString();
	}

	public GameResultType play(int position) {
		GameResultType ret = state.maybeSetCell(position - 1, 
				turn ? TicTacToeCellState.X : TicTacToeCellState.O);
		nextTurn();
		return ret;
	}

	private void nextTurn() {
		turn = !turn;
	}

	public boolean isTurn(User u) {
		return turn ? u == players.getLeft() : u == players.getRight();
	}
	
	public void terminate() {
		players.getLeft().setCurrentGame(null);
		players.getLeft().setState(UserState.FREE);
		players.getRight().setCurrentGame(null);
		players.getRight().setState(UserState.FREE);

	}

	public User otherPlayer(User u) {
		return u == players.getLeft() ? players.getRight() : players.getLeft();
	}
}
