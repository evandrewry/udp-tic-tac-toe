package common;

import game.Game;

public class User implements Comparable<User> {
	private String username;
	private UserState state;
	private Game currentGame;
	private String ip;
	private int port;

	public User(String username, UserState state, String ip, int port) {
		this.username = username;
		this.state = state;
		this.ip = ip;
		this.port = port;
	}

	public User(String username, String ip, int port) {
		this(username, UserState.OFFLINE, ip, port);
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
	public String getIp() {
		return ip;
	}
}
