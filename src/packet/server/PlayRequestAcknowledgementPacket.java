package packet.server;

import packet.Packet;

public class PlayRequestAcknowledgementPacket extends Packet {
	private final String username;
	private final PlayRequestAcknowledgementStatus status;
    public static final String PACKET_FORMAT = "ackchoose,%s,%s";

    public PlayRequestAcknowledgementPacket(String username, PlayRequestAcknowledgementStatus status) {
    	this.username = username;
    	this.status = status;
    }
	@Override
	public String getPacketFormat() {
		return PACKET_FORMAT;
	}

	@Override
	public Object[] getParameters() {
		return new Object[] { username, status.getCode() };
	}

}
