package packet.server;

import packet.Packet;

public class PlayRequestReplyNotificationPacket extends Packet {
	private final String username;
	private final PlayRequestStatus status;
    public static final String PACKET_FORMAT = "ackchoose,%s,%s";

    public PlayRequestReplyNotificationPacket(String username, PlayRequestStatus status) {
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
