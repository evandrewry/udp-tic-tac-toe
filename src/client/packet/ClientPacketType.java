package client.packet;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import client.Command;
import client.packet.impl.AcceptRequestPacket;
import client.packet.impl.ChoosePlayerPacket;
import client.packet.impl.DenyRequestPacket;
import client.packet.impl.LoginPacket;
import client.packet.impl.LogoutPacket;
import client.packet.impl.PlayGamePacket;
import client.packet.impl.QueryListPacket;

import common.Payload;

import exception.BadPacketException;
import exception.InvalidClientCommandException;

/**
 * types of packets sent by the client
 * 
 * @author evan
 * 
 */
public enum ClientPacketType {
    LOGIN(LoginPacket.class),
    QUERY_LIST(QueryListPacket.class),
    CHOOSE_PLAYER(ChoosePlayerPacket.class),
    ACCEPT_REQUEST(AcceptRequestPacket.class),
    DENY_REQUEST(DenyRequestPacket.class),
    PLAY_GAME(PlayGamePacket.class),
    LOGOUT(LogoutPacket.class);

    /**
     * @param clazz
     * @return packet type of input class
     */
    public static ClientPacketType fromClass(Class<? extends ClientPacket> clazz) {
        return classLookup.get(clazz);
    }

    /**
     * @param command
     * @return packet type of input command
     * @throws InvalidClientCommandException
     */
    public static ClientPacketType fromCommand(Command command) throws InvalidClientCommandException {
        String cmd = command.content.trim().split(" ")[0];
        ClientPacketType ret = commandLookup.get(cmd);
        if (ret == null) {
            throw new InvalidClientCommandException(command.content);
        } else {
            return ret;
        }
    }

    /**
     * @param payload
     * @return packet type of input payload
     */
    public static ClientPacketType fromPayload(Payload payload) {
        for (ClientPacketType t : values()) {
            try {
                if (t.getPacketPattern().matcher(payload.content).matches()) {
                    return t;
                }
            } catch (NoSuchFieldException e) {
                throw new IllegalStateException();
            }
        }
        throw new BadPacketException(payload.content);
    }
    private Class<? extends ClientPacket> clazz;

    /**
     * map to look up type by packet class
     */
    private static final Map<Class<? extends ClientPacket>, ClientPacketType> classLookup = new HashMap<Class<? extends ClientPacket>, ClientPacketType>();
    static {
        for (ClientPacketType t : values()) {
            classLookup.put(t.getPacketClass(), t);
        }
    }

    /**
     * map to look up type by command string
     */
    private static final Map<String, ClientPacketType> commandLookup = new HashMap<String, ClientPacketType>();

    static {
        for (ClientPacketType t : values()) {
            try {
                commandLookup.put(t.getCommand(), t);
            } catch (NoSuchFieldException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private ClientPacketType(Class<? extends ClientPacket> clazz) {
        this.clazz = clazz;
    }

    /**
     * @return command for this packet type
     * @throws NoSuchFieldException
     */
    public String getCommand() throws NoSuchFieldException {
        try {
            return (String) clazz.getDeclaredField("COMMAND").get(null);
        } catch (Exception e) {
            throw new NoSuchFieldException("Could not find command " + clazz.getSimpleName());
        }
    }

    /**
     * @return command pattern for this packet type
     * @throws NoSuchFieldException
     */
    public Pattern getCommandPattern() throws NoSuchFieldException {
        try {
            return (Pattern) clazz.getDeclaredField("COMMAND_PATTERN").get(null);
        } catch (Exception e) {
            throw new NoSuchFieldException("Could not find command pattern for " + clazz.getSimpleName());
        }
    }

    /**
     * @return class of this packet type
     */
    public Class<? extends ClientPacket> getPacketClass() {
        return clazz;
    }

    /**
     * @return pattern for this packet type
     * @throws NoSuchFieldException
     */
    private Pattern getPacketPattern() throws NoSuchFieldException {
        try {
            return (Pattern) clazz.getDeclaredField("PACKET_PATTERN").get(null);
        } catch (Exception e) {
            throw new NoSuchFieldException("Could not find packet pattern for " + clazz.getSimpleName());
        }
    }

    /**
     * @param inputCommand
     * @return true if command is valid for this packet type
     */
    public boolean isValidCommand(String inputCommand) {
        try {
            return getCommandPattern().matcher(inputCommand).matches();
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException();
        }
    }
}
