package packet.client;

import java.util.regex.Pattern;

import packet.Packet;

public class LogoutPacket extends Packet {
	private int packetId;
	private String username;
	public static final String PACKET_FORMAT = "logout,%d,%s";
	public static final String COMMAND = "logout";
	public static final Pattern COMMAND_PATTERN = Pattern
			.compile("^logout$");

	public LogoutPacket(int packetId, String username) {
		this.packetId = packetId;
		this.username = username;
	}

	@Override
	public String getPacketFormat() {
		return PACKET_FORMAT;
	}

	@Override
	public Object[] getParameters() {
		return new Object[] { packetId, username };
	}

	public Pattern getCommandPattern() {
		return COMMAND_PATTERN;
	}

	@Override
	public String getCommand() {
		return COMMAND;
	}

}
