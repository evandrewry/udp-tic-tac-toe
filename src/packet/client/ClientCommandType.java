package packet.client;
import java.util.regex.Pattern;

import packet.client.AcceptRequestPacket;
import packet.client.ChoosePlayerPacket;
import packet.client.LoginPacket;
import packet.client.QueryListPacket;
import packet.client.DenyRequestPacket;
import packet.client.PlayGamePacket;
import packet.client.LogoutPacket;

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
		} catch (IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getCommandPattern() {
		try {
			return (String) clazz.getDeclaredField("COMMAND_PATTERN").get(null);
		} catch (IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean isCommandMatch(String inputCommand) {
		return getCommandPattern().matcher(inputCommand).matches();
	}
}
