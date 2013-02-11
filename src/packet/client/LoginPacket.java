package packet.client;

import java.util.regex.Pattern;

import packet.Packet;

public class LoginPacket extends Packet {
	private int packetId;
	private String username;
	private int port;
	public static final String PACKET_FORMAT = "login, %d, %s, %d";
	public static final String COMMAND = "login";
	public static final Pattern COMMAND_PATTERN = Pattern
			.compile("^login\\s+(\\w+)$");

	public LoginPacket(int packetId, String username, int port) {
		this.packetId = packetId;
		this.username = username;
		this.port = port;
	}

	@Override
	public String getPacketFormat() {
		return PACKET_FORMAT;
	}

	@Override
	public Object[] getParameters() {
		return new Object[] { packetId, username, port };
	}

    @Override
	public Pattern getCommandPattern() {
		return COMMAND_PATTERN;
	}

	@Override
	public String getCommand() {
		return COMMAND;
	}

}
