package common;
public class User implements Comparable<User> {
	private String username;
	private UserState state;

	public User(String username) {
		this.username = username;
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
}
