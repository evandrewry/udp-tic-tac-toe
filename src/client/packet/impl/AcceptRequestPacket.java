package client.packet.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.packet.ClientPacket;

import common.Payload;

import exception.BadPacketException;

public class AcceptRequestPacket extends ClientPacket {
	private final int packetId;
	private final String sender;
	private final String reciever;

	public static final String PACKET_FORMAT = "ackchoose,%d,%s,%s,A";
	public static final Pattern PACKET_PATTERN = Pattern
			.compile("^ackchoose,(\\d+),(\\w+),(\\w+),A$");
	public static final String COMMAND = "accept";
	public static final Pattern COMMAND_PATTERN = Pattern
			.compile("^accept\\s+(\\w+)$");
	public static final String CODE = "accept";

	public AcceptRequestPacket(int packetId, String sender, String reciever) {
		this.packetId = packetId;
		this.sender = sender;
		this.reciever = reciever;
	}

	public AcceptRequestPacket(String... params) {
		this(Integer.parseInt(params[1]), params[2], params[3]);
	}

	@Override
	public String getPacketFormat() {
		return PACKET_FORMAT;
	}

	@Override
	public Pattern getPacketPattern() {
		return PACKET_PATTERN;
	}

	@Override
	public Object[] getParameters() {
		return new Object[] { packetId, sender, reciever };
	}

	@Override
	public Pattern getCommandPattern() {
		return COMMAND_PATTERN;
	}

	@Override
	public String getCommand() {
		return COMMAND;
	}

	public static AcceptRequestPacket fromPayload(Payload payload) {
		Matcher m = PACKET_PATTERN.matcher(payload.content);
		if (m.matches()) {
			int packetId = Integer.parseInt(m.group(1));
			String sender = m.group(2);
			String reciever = m.group(3);
			return new AcceptRequestPacket(packetId, sender, reciever);
		} else {
			throw new BadPacketException("Could not parse.");
		}
	}
}
