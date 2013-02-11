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

    ClientCommandType(Class clazz) {
        this.clazz = clazz;
    }

    public Class getPacketClass() {
        return clazz;
    }

    public String getCommand() {
        try {
            return (String) clazz.getDeclaredField("COMMAND").get(null);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public Pattern getCommandPattern() {
        try {
            return (Pattern) clazz.getDeclaredField("COMMAND_PATTERN").get(null);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public boolean isValidCommand(String inputCommand) {
        return getCommandPattern().matcher(inputCommand).matches();
    }
}
