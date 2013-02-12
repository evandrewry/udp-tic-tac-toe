package common;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class UserList extends TreeSet<User> {

	private static final long serialVersionUID = 1L;

	public UserList() {
		super();
	}

	public UserList(Comparator<? super User> comparator) {
		super(comparator);
	}

	public UserList(Collection<? extends User> c) {
		super(c);
	}

	public UserList(SortedSet<User> s) {
		super(s);
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		boolean fst = true;
		for (User u : this) {
		    if(!fst || (fst = false)) s.append(",");
			s.append(u.getUsername()).append(",").append(u.getState().toString());
		}
		return s.toString();
	}
}
