package common;

import game.Game;

public class User implements Comparable<User> {
	private String username;
	private UserState state;
	private Game currentGame;
	private int port;

	public User(String username, UserState state, int port) {
		this.username = username;
		this.state = state;
		this.port = port;
	}

	public User(String username, int port) {
		this(username, UserState.OFFLINE, port);
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

	public int getPort() {
		return port;
	}
}
