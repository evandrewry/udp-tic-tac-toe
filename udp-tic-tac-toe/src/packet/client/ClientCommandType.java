package packet.client;
import java.util.regex.Pattern;

import packet.client.AcceptRequestPacket;
import packet.client.ChoosePlayerPacket;
import packet.client.LoginPacket;
import packet.client.QueryListPacket;


public enum ClientCommandType {
	LOGIN(LoginPacket.class, LoginPacket.COMMAND, LoginPacket.COMMAND_PATTERN, LoginPacket.PACKET_FORMAT),
	QUERY_LIST(QueryListPacket.class, QueryListPacket.COMMAND, QueryListPacket.COMMAND_PATTERN, QueryListPacket.PACKET_FORMAT),
	CHOOSE_PLAYER(ChoosePlayerPacket.class, ChoosePlayerPacket.COMMAND, ChoosePlayerPacket.COMMAND_PATTERN, ChoosePlayerPacket.PACKET_FORMAT),
	ACCEPT_REQUEST(AcceptRequestPacket.class, AcceptRequestPacket.COMMAND, AcceptRequestPacket.COMMAND_PATTERN, AcceptRequestPacket.PACKET_FORMAT),
	DENY_REQUEST(DenyRequestPacket.class, DenyRequestPacket.COMMAND, DenyRequestPacket.COMMAND_PATTERN, DenyRequestPacket.PACKET_FORMAT),
	PLAY_GAME(PlayGamePacket.class, PlayGamePacket.COMMAND, PlayGamePacket.COMMAND_PATTERN, PlayGamePacket.PACKET_FORMAT),
	LOGOUT(LogoutPacket.class, LogoutPacket.COMMAND, LogoutPacket.COMMAND_PATTERN, LogoutPacket.PACKET_FORMAT);
	
	private Class<?> clazz;
	private String command;
	private Pattern commandPattern;
	private String packetFormat;
	ClientCommandType(Class clazz, String command, Pattern commandPattern, String packetFormat) {
		this.clazz = clazz;
		this.command = command;
		this.commandPattern = commandPattern;
		this.packetFormat = packetFormat;
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
}
