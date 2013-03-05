package common;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserListTest {

    private static final String csv = "max,B,evan,F,fred,D";

    @Test
    public void testFromString() {
        UserList l = UserList.fromString(csv);
        assertTrue(l.size() == 3);
        User u = l.pollFirstEntry().getValue();
        assertTrue(u.getUsername().equals("evan"));
        assertTrue(u.getState() == UserState.FREE);

        u = l.pollFirstEntry().getValue();
        assertTrue(u.getUsername().equals("fred"));
        assertTrue(u.getState() == UserState.DECISION);

        u = l.pollFirstEntry().getValue();
        assertTrue(u.getUsername().equals("max"));
        assertTrue(u.getState() == UserState.BUSY);

        assertTrue(l.isEmpty());
    }

}
