package client.packet;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

import client.Client;
import client.Command;

import common.Packet;
import common.Payload;

import exception.BadPacketException;

/**
 * A client-side packet
 * 
 * @author evan
 * 
 */
public abstract class ClientPacket extends Packet {

    /**
     * factory method to create packets from command
     * 
     * @param command
     * @param handler
     * @return new ClientPacket from the input command
     */
    public static ClientPacket fromCommand(Command command, Client handler) {
        return fromCommand(command, ClientPacketType.fromCommand(command), handler);
    }

    /**
     * factory method to create packets from command
     * 
     * @param command
     * @param type
     * @param handler
     * @return new ClientPacket from the input command
     */
    public static ClientPacket fromCommand(Command command, ClientPacketType type, Client handler) {
        try {
            return (ClientPacket) type.getPacketClass().getDeclaredMethod("fromCommand", Command.class, Client.class)
                    .invoke(null, command, handler);
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
     * factory method to create packet objects from udp payload
     * 
     * @param payload
     * @return
     */
    public static ClientPacket fromPayload(Payload payload) {
        for (ClientPacketType t : ClientPacketType.values()) {
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
     * 
     * @param payload
     * @param type
     * @return
     */
    public static ClientPacket fromPayload(Payload payload, ClientPacketType type) {
        try {
            return (ClientPacket) type.getPacketClass().getDeclaredMethod("fromPayload", Payload.class)
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
     * @return the console command associated with this packet
     */
    public abstract String getCommand();

    /**
     * @return the pattern to match against console commands
     */
    public abstract Pattern getCommandPattern();

    /**
     * @return the unique id of this packet
     */
    public abstract long getPacketId();

    /**
     * @return the {@link ClientPacketType} associated with this packet
     */
    public ClientPacketType getPacketType() {
        return ClientPacketType.fromClass(getClass());
    }

    /**
     * @return user sending the packet
     */
    public abstract String getUsername();

    /**
     * @param inputCommand
     * @return true if console input matches command pattern
     */
    public boolean isValidCommmand(String inputCommand) {
        return getCommandPattern().matcher(inputCommand).matches();
    }
}
