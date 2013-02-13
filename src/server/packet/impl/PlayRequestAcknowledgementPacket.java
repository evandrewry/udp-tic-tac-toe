package server.packet.impl;

import java.util.regex.Pattern;

import server.packet.PlayRequestAcknowledgementStatus;
import server.packet.ServerPacket;

public class PlayRequestAcknowledgementPacket extends ServerPacket {
	private final String username;
	private final PlayRequestAcknowledgementStatus status;
	public static final String PACKET_FORMAT = "ackchoose,%s,%s";
	public static final Pattern PACKET_PATTERN = Pattern
			.compile("^ackchoose,(\\w+),(\\w+)$");

	public PlayRequestAcknowledgementPacket(String username,
			PlayRequestAcknowledgementStatus status) {
		this.username = username;
		this.status = status;
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
		return new Object[] { username, status.getCode() };
	}

}
