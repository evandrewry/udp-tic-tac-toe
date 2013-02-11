package packet.client;

import java.util.regex.Pattern;

import packet.Packet;

public class ChoosePlayerPacket extends Packet {
	private int packetId;
	private String senderName;
	private String recieverName;

	public static final String PACKET_FORMAT = "choose, %d, %s, %s";
	public static final String COMMAND = "choose";
	public static final Pattern COMMAND_PATTERN = Pattern
			.compile("^choose\\s+(\\w*+)$");

	@Override
	public String getPacketFormat() {
		return PACKET_FORMAT;
	}

	@Override
	public Object[] getParameters() {
		return new Object[] { packetId, senderName, recieverName };
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
