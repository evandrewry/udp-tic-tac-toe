package packet.server;


public class LoginAcknowledgementPacket extends ServerPacket {
	private LoginAcknowledgementType success;
    public static final String PACKET_FORMAT = "acklogin,%s";

	@Override
	public String getPacketFormat() {
		return PACKET_FORMAT;
	}

	@Override
	public Object[] getParameters() {
		return new Object[] { success.toString() };
	}
}
