package common;

import java.util.regex.Pattern;

import server.packet.ServerPacket;
import client.packet.ClientPacket;
import exception.BadPacketException;

/**
 * A generic Packet
 *
 * @author evan
 *
 */
public abstract class Packet {
    private static long id = 0L;

    public static Packet fromPayload(Payload payload) {
        try {
            return ServerPacket.fromPayload(payload);
        } catch (BadPacketException e) {
            return ClientPacket.fromPayload(payload);
        }
    }

    /**
     * @return next unique packet id
     */
    public static long nextId() {
        return id++;
    }

    /**
     * @return format of the packet payload
     */
    public abstract String getPacketFormat();

    /**
     * @return pattern to match against payload
     */
    public abstract Pattern getPacketPattern();

    /**
     * @return parameters of packet
     */
    public abstract Object[] getParameters();

    /**
     * @return payload of packet
     */
    public String toPayload() {
        return this.toString();
    }

    @Override
    public String toString() {
        return String.format(getPacketFormat(), getParameters());
    }

}
