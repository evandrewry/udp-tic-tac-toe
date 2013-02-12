package packet.server;

public class AcknowledgementPacket extends ServerPacket {
    private int packetId;
    public static final String PACKET_FORMAT = "ack,%d";

    public AcknowledgementPacket(int packetId) {
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