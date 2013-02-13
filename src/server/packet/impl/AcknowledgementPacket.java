package server.packet.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import server.packet.ServerPacket;
import exception.BadPacketException;

public class AcknowledgementPacket extends ServerPacket {
	private int packetId;
	public static final String PACKET_FORMAT = "ack,%d";
	public static final Pattern PACKET_PATTERN = Pattern
			.compile("^ack,(\\d+)$");

	public AcknowledgementPacket(int packetId) {
		this.packetId = packetId;
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
		return new Object[] { packetId };
	}

	public static AcknowledgementPacket fromPayload(String payload) {
		Matcher m = PACKET_PATTERN.matcher(payload);
		if (m.matches()) {
			int packetId = Integer.parseInt(m.group(1));
			return new AcknowledgementPacket(packetId);
		} else {
			throw new BadPacketException("Could not parse.");
		}
	}

}
