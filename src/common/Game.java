package common;

public class Game {
	private Pair<User,User> players;
	private TicTacToeBoard state;
	
	public Game(User p1, User p2) {
		this.players = new Pair<User,User>(p1, p2);
		p1.setState(UserState.BUSY);
		p2.setState(UserState.BUSY);
	}
	
	public Pair<User,User> getPlayers() {
		return players;
	}
}
