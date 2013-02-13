package server.packet.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.Payload;

import server.packet.ServerPacket;
import exception.BadPacketException;

public class CurrentGameStatePacket extends ServerPacket {
	private String state;
	public static final String PACKET_FORMAT = "play,%s";
	public static final Pattern PACKET_PATTERN = Pattern
			.compile("^play,(\\d+)$");

	public CurrentGameStatePacket(String state) {
		this.state = state;
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
		return new Object[] { state };
	}

	public static CurrentGameStatePacket fromPayload(Payload payload) {
		Matcher m = PACKET_PATTERN.matcher(payload.content);
		if (m.matches()) {
			String state = m.group(1);
			return new CurrentGameStatePacket(state);
		} else {
			throw new BadPacketException("Could not parse.");
		}
	}

}
