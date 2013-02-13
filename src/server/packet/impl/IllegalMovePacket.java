package server.packet.impl;

import java.util.regex.Pattern;

import server.packet.IllegalMoveType;
import server.packet.ServerPacket;

public class IllegalMovePacket extends ServerPacket {
	private final IllegalMoveType type;
	public static final String PACKET_FORMAT = "ackplay,%s";
	public static final Pattern PACKET_PATTERN = Pattern
			.compile("^ackplay,(\\w+)$");

	public IllegalMovePacket(IllegalMoveType type) {
		this.type = type;
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
		return new Object[] { type.getCode() };
	}

}
