package server.packet;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import server.packet.impl.AcknowledgementPacket;
import server.packet.impl.CurrentGameStatePacket;
import server.packet.impl.CurrentUsersListPacket;
import server.packet.impl.GameResultPacket;
import server.packet.impl.IllegalMovePacket;
import server.packet.impl.LoginAcknowledgementPacket;
import server.packet.impl.PlayRequestAcknowledgementPacket;
import server.packet.impl.PlayRequestPacket;

import common.Payload;

import exception.BadPacketException;

/**
 * Types of packets sent by the server to the client
 *
 * @author evan
 *
 */
public enum ServerPacketType {
    ACK(AcknowledgementPacket.class),
    CURRENT_GAME_STATE(CurrentGameStatePacket.class),
    CURRENT_USERS_LIST(CurrentUsersListPacket.class),
    GAME_RESULT(GameResultPacket.class),
    ILLEGAL_MOVE(IllegalMovePacket.class),
    LOGIN_ACK(LoginAcknowledgementPacket.class),
    PLAY_REQUEST_ACK(PlayRequestAcknowledgementPacket.class),
    PLAY_REQUEST(PlayRequestPacket.class);

    public static ServerPacketType fromClass(Class<? extends ServerPacket> clazz) {
        return classLookup.get(clazz);
    }

    public static ServerPacketType fromPacketPayload(String payload) {
        for (ServerPacketType t : values()) {
            try {
                if (t.getPacketPattern().matcher(payload).matches()) {
                    return t;
                }
            } catch (NoSuchFieldException e) {
                throw new IllegalStateException();
            }
        }
        throw new BadPacketException(payload);
    }
    private final Class<? extends ServerPacket> clazz;

    private static final Map<Class<? extends ServerPacket>, ServerPacketType> classLookup = new HashMap<Class<? extends ServerPacket>, ServerPacketType>();

    static {
        for (ServerPacketType t : values()) {
            classLookup.put(t.getPacketClass(), t);
        }
    }

    private ServerPacketType(Class<? extends ServerPacket> clazz) {
        this.clazz = clazz;
    }

    public ServerPacket fromPayload(Payload payload) {
        try {
            return clazz.getDeclaredConstructor(Payload.class).newInstance(payload);
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
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }

    public Class<? extends ServerPacket> getPacketClass() {
        return clazz;
    }

    private Pattern getPacketPattern() throws NoSuchFieldException {
        try {
            return (Pattern) clazz.getDeclaredField("PACKET_PATTERN").get(null);
        } catch (Exception e) {
            throw new NoSuchFieldException("Could not find packet pattern for " + clazz.getSimpleName());
        }
    }

}
