package packet.client;

import java.util.regex.Pattern;

public enum ClientCommandType {
    LOGIN(LoginPacket.class),
    QUERY_LIST(QueryListPacket.class),
    CHOOSE_PLAYER(ChoosePlayerPacket.class),
    ACCEPT_REQUEST(AcceptRequestPacket.class),
    DENY_REQUEST(DenyRequestPacket.class),
    PLAY_GAME(PlayGamePacket.class),
    LOGOUT(LogoutPacket.class);

    private Class<?> clazz;

    ClientCommandType(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getPacketClass() {
        return clazz;
    }

    public String getCommand() throws NoSuchFieldException {
        try {
            return (String) clazz.getDeclaredField("COMMAND").get(null);
        } catch (Exception e) {
        	throw new NoSuchFieldException("Could not find command name for " + clazz.getSimpleName());
        }
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
}
