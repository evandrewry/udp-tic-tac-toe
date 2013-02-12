package packet.server;

import packet.Packet;

public class PlayRequestPacket extends Packet {

	private String requesterUsername;
    public static final String PACKET_FORMAT = "request,%s";

	@Override
	public String getPacketFormat() {
		return null;
	}

	@Override
	public Object[] getParameters() {
		return new Object[] { requesterUsername };
	}

}
