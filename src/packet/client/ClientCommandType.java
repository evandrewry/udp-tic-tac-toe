package packet.client;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import exception.BadPacketException;
import exception.InvalidClientCommandException;

public enum ClientCommandType {
    LOGIN(LoginPacket.class),
    QUERY_LIST(QueryListPacket.class),
    CHOOSE_PLAYER(ChoosePlayerPacket.class),
    ACCEPT_REQUEST(AcceptRequestPacket.class),
    DENY_REQUEST(DenyRequestPacket.class),
    PLAY_GAME(PlayGamePacket.class),
    LOGOUT(LogoutPacket.class);

    private Class<?> clazz;

    private static final Map<String, ClientCommandType> commandLookup = new HashMap<String, ClientCommandType>();
    static {
        for (ClientCommandType t : values()) {
            try {
                commandLookup.put(t.getCommand(), t);
            } catch (NoSuchFieldException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    ClientCommandType(Class<?> clazz) {
        this.clazz = clazz;
    }

    private Pattern getPacketPattern() throws NoSuchFieldException {
        try {
            return (Pattern) clazz.getDeclaredField("PACKET_PATTERN").get(null);
        } catch (Exception e) {
            throw new NoSuchFieldException("Could not find code " + clazz.getSimpleName());
        }
    }

    public Class<?> getPacketClass() {
        return clazz;
    }

    public String getCommand() throws NoSuchFieldException {
        try {
            return (String) clazz.getDeclaredField("COMMAND").get(null);
        } catch (Exception e) {
            throw new NoSuchFieldException("Could not find command " + clazz.getSimpleName());
        }
    }

    public ClientPacket getInstance(String inputCommand) {
        try {
            return (ClientPacket) clazz.getConstructor(String.class).newInstance(inputCommand);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof RuntimeException) {
                throw (RuntimeException) e.getCause();
            } else {
                e.printStackTrace();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Pattern getCommandPattern() throws NoSuchFieldException {
        try {
            return (Pattern) clazz.getDeclaredField("COMMAND_PATTERN").get(null);
        } catch (Exception e) {
            throw new NoSuchFieldException("Could not find command pattern for " + clazz.getSimpleName());
        }
    }

    public boolean isValidCommand(String inputCommand) {
        try {
            return getCommandPattern().matcher(inputCommand).matches();
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    public static ClientCommandType fromCommand(String inputCommand) throws InvalidClientCommandException {
        String cmd = inputCommand.trim().split(" ")[0];
        ClientCommandType ret = commandLookup.get(cmd);
        if (ret == null) {
            throw new InvalidClientCommandException(inputCommand);
        } else {
            return ret;
        }
    }

    public static ClientCommandType fromPacketPayload(String payload) {
        for (ClientCommandType t : values()) {
            try {
                if (t.getPacketPattern().matcher(payload).matches()) {
                    return t;
                }
            } catch (NoSuchFieldException e) {
                throw new IllegalStateException();
            }
        }
        throw new BadPacketException(payload);

    }
}
