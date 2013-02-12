package packet.server;

import common.GameResultType;

import packet.Packet;

public class GameResultPacket extends Packet {
	private final GameResultType result;
	public static final String PACKET_FORMAT = "result,%s";

	public GameResultPacket(GameResultType result) {
		this.result = result;
	}

	@Override
	public String getPacketFormat() {
		return PACKET_FORMAT;
	}

	@Override
	public Object[] getParameters() {
		return new Object[] { result.getCode() };
	}

}
