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

    public static UserList fromString(String csv) {
        String[] values = csv.split(",");
        assert values.length % 2 == 0;

        UserList out = new UserList();
        for (int i = 0; i < values.length; i++) {
            out.add(new User(values[i], UserState.fromCode(values[++i])));
        }
        return out;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        boolean fst = true;
        for (User u : this) {
            if (!fst || (fst = false))
                s.append(",");
            s.append(u.getUsername()).append(",").append(u.getState().toString());
        }
        return s.toString();
    }
}
