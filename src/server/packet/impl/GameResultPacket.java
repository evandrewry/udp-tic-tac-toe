package server.packet.impl;

import exception.BadPacketException;
import game.GameResultType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import server.packet.ServerPacket;

public class GameResultPacket extends ServerPacket {
	private final GameResultType result;
	public static final String PACKET_FORMAT = "result,%s";
	public static final Pattern PACKET_PATTERN = Pattern
			.compile("^result,(\\w+)$");

	public GameResultPacket(GameResultType result) {
		this.result = result;
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
		return new Object[] { result.getCode() };
	}

	@Override
	public CurrentUsersListPacket fromPayload(String payload) {
		Matcher m = PACKET_PATTERN.matcher(payload);
		if (m.matches()) {
			GameResultType result = GameResultType.fromCode(m.group(1));
			return new CurrentUsersListPacket(users);
		} else {
			throw new BadPacketException("Could not parse.");
		}
	}
}
