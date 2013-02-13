package server.packet.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import server.packet.ServerPacket;

import common.Payload;

import exception.BadPacketException;

public class PlayRequestPacket extends ServerPacket {

	private String username;
	public static final String PACKET_FORMAT = "request,%s";
	public static final Pattern PACKET_PATTERN = Pattern
			.compile("^request,(\\w+)$");

	public PlayRequestPacket(String username) {
		this.username = username;
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
		return new Object[] { username };
	}

	public PlayRequestPacket(Payload payload) {
		Matcher m = PACKET_PATTERN.matcher(payload.content);
		if (m.matches()) {
			String username = m.group(1);
			this.username = username;
		} else {
			throw new BadPacketException("Could not parse.");
		}
	}
}
