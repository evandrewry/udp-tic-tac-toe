package packet.server;

import packet.Packet;

public class AckPacket extends Packet {
	private int packetId;
    public static final String PACKET_FORMAT = "ack,%d";
	
	public AckPacket(int packetId) {
		this.packetId = packetId;
	}
	
	@Override
	public String getPacketFormat() {
		return PACKET_FORMAT;
	}

	@Override
	public Object[] getParameters() {
		return new Object[] { packetId };
	}

}
