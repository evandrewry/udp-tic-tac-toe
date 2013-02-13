package client.packet.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.Payload;

import client.packet.ClientPacket;
import exception.BadPacketException;

public class PlayGamePacket extends ClientPacket {
	private int packetId;
	private String username;
	private int number;
	public static final String PACKET_FORMAT = "play,%d,%s,%d";
	public static final Pattern PACKET_PATTERN = Pattern
			.compile("^play,(\\d+),(\\w+),(\\d+)$");
	public static final String COMMAND = "play";
	public static final Pattern COMMAND_PATTERN = Pattern
			.compile("^play\\s+(\\d+)$");

	public PlayGamePacket(int packetId, String username, int number) {
		this.packetId = packetId;
		this.username = username;
		this.number = number;
	}

	public PlayGamePacket(String... params) {
		this(Integer.parseInt(params[1]), params[2], Integer
				.parseInt(params[3]));
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
		return new Object[] { packetId, username, number };
	}

	@Override
	public Pattern getCommandPattern() {
		return COMMAND_PATTERN;
	}

	@Override
	public String getCommand() {
		return COMMAND;
	}

	public static PlayGamePacket fromPayload(Payload payload) {
		Matcher m = PACKET_PATTERN.matcher(payload.content);
		if (m.matches()) {
			int packetId = Integer.parseInt(m.group(1));
			String username = m.group(2);
			int port = Integer.parseInt(m.group(3));
			return new PlayGamePacket(packetId, username, port);
		} else {
			throw new BadPacketException("Could not parse.");
		}
	}
}
