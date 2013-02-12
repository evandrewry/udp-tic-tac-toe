package packet.server;

public class IllegalMovePacket extends ServerPacket {
    private final IllegalMoveType type;
    public static final String PACKET_FORMAT = "ackplay,%s";

    public IllegalMovePacket(IllegalMoveType type) {
        this.type = type;
    }

    @Override
    public String getPacketFormat() {
        return PACKET_FORMAT;
    }

    @Override
    public Object[] getParameters() {
        return new Object[] { type.getCode() };
    }

}
