package common;

import game.Game;

/**
 * Represents a user in the tic tac toe system
 *
 * @author evan
 *
 */
public class User implements Comparable<User> {
    private String username;
    private UserState state;
    private Game currentGame;
    private String ip;
    private int port;

    public User(String username, String ip, int port) {
        this(username, UserState.OFFLINE, ip, port);
    }

    public User(String username, UserState state, String ip, int port) {
        this.username = username;
        this.state = state;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public int compareTo(User other) {
        return getUsername().compareToIgnoreCase(other.getUsername());
    }

    public boolean equals(User other) {
        return other != null && other.getUsername().equals(username);
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public UserState getState() {
        return state;
    }

    public String getUsername() {
        return username;
    }

    public void setCurrentGame(Game currentGame) {
        assert this.currentGame == null;
        this.currentGame = currentGame;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return username + "," + state.getCode();
    }
}
