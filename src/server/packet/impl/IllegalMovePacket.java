package server.packet.impl;

import exception.BadPacketException;
import game.GameResultType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.Payload;

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

	public static IllegalMovePacket fromPayload(Payload payload) {
		Matcher m = PACKET_PATTERN.matcher(payload.content);
		if (m.matches()) {
			IllegalMoveType type = IllegalMoveType.fromCode(m.group(1));
			return new IllegalMovePacket(type);
		} else {
			throw new BadPacketException("Could not parse.");
		}
	}
}
