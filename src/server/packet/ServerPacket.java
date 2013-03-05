package server.packet;

import java.lang.reflect.InvocationTargetException;

import common.Packet;
import common.Payload;

import exception.BadPacketException;

/**
 * A server-side packet
 *
 * @author evan
 *
 */
public abstract class ServerPacket extends Packet {
    /**
     * factory method to create packet objects from udp payload
     * @param payload
     * @return
     */
    public static ServerPacket fromPayload(Payload payload) {
        for (ServerPacketType t : ServerPacketType.values()) {
            try {
                return fromPayload(payload, t);
            } catch (BadPacketException e) {
                continue;
            }
        }
        throw new BadPacketException("Could not parse packet.");
    }

    /**
     * factory method to create packet objects from udp payload
     * @param payload
     * @param type
     * @return
     */
    public static ServerPacket fromPayload(Payload payload, ServerPacketType type) {
        try {
            return (ServerPacket) type.getPacketClass().getDeclaredMethod("fromPayload", Payload.class)
                    .invoke(null, payload);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof RuntimeException) {
                throw (RuntimeException) e.getCause();
            } else {
                e.printStackTrace();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }

    /**
     * @return the {@link ServerPacketType} associated with this packet
     */
    public ServerPacketType getPacketType() {
        return ServerPacketType.fromClass(getClass());
    }
}
