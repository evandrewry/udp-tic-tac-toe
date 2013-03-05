package common;

import java.util.regex.Pattern;

import server.packet.ServerPacket;
import client.packet.ClientPacket;
import exception.BadPacketException;

public abstract class Packet {
    private static long id = 0L;

    public abstract String getPacketFormat();

    public abstract Pattern getPacketPattern();

    public abstract Object[] getParameters();

    public static Packet fromPayload(Payload payload) {
        try {
            return ServerPacket.fromPayload(payload);
        } catch (BadPacketException e) {
            return ClientPacket.fromPayload(payload);
        }
    }

    public String toPayload() {
        return this.toString();
    }

    public String toString() {
        return String.format(getPacketFormat(), getParameters());
    }

    public static long nextId() {
        return id++;
    }

}
