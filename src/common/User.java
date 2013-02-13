package common;

import game.Game;

public class User implements Comparable<User> {
	private String username;
	private UserState state;
	private Game currentGame;

	public User(String username, UserState state) {
		this.username = username;
		this.state = state;
	}

	public User(String username) {
		this(username, UserState.OFFLINE);
	}

	public String getUsername() {
		return username;
	}

	public int compareTo(User other) {
		return getUsername().compareToIgnoreCase(other.getUsername());
	}

	public UserState getState() {
		return state;
	}

	public void setState(UserState state) {
		this.state = state;
	}

	public String toString() {
		return username + "," + state.getCode();
	}

	public boolean equals(User other) {
		return other != null && other.getUsername().equals(username);
	}

	public Game getCurrentGame() {
		return currentGame;
	}

	public void setCurrentGame(Game currentGame) {
		assert this.currentGame == null;
		this.currentGame = currentGame;
	}
}
