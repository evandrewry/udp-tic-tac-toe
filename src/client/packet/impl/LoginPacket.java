package client.packet.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.Client;
import client.Command;
import client.packet.ClientPacket;

import common.Packet;
import common.Payload;

import exception.BadPacketException;
import exception.InvalidCommandParametersException;

/**
 * Packet sent to log into server
 *
 * @author evan
 *
 */
public class LoginPacket extends ClientPacket {
    public static final String PACKET_FORMAT = "login,%d,%s,%d";
    public static final Pattern PACKET_PATTERN = Pattern.compile("^login,(\\d+),(\\w+),(\\d+)$");
    public static final String COMMAND = "login";
    public static final Pattern COMMAND_PATTERN = Pattern.compile("^login\\s+(\\w+)$");

    public static LoginPacket fromCommand(Command command, Client handler) {
        Matcher m = COMMAND_PATTERN.matcher(command.content);
        if (m.matches()) {
            long packetId = Packet.nextId();
            String username = m.group(1);
            handler.login(username, handler.getIP(), handler.getPort());
            return new LoginPacket(packetId, username, handler.getPort());
        } else {
            throw new InvalidCommandParametersException("Could not parse.");
        }
    }
    public static LoginPacket fromPayload(Payload payload) {
        Matcher m = PACKET_PATTERN.matcher(payload.content);
        if (m.matches()) {
            int packetId = Integer.parseInt(m.group(1));
            String username = m.group(2);
            int port = Integer.parseInt(m.group(3));
            return new LoginPacket(packetId, username, port);
        } else {
            throw new BadPacketException("Could not parse.");
        }
    }
    private final long packetId;

    private final String username;

    private final int port;

    public LoginPacket(long packetId, String username, int port) {
        this.packetId = packetId;
        this.username = username;
        this.port = port;
    }

    @Override
    public String getCommand() {
        return COMMAND;
    }

    @Override
    public Pattern getCommandPattern() {
        return COMMAND_PATTERN;
    }

    @Override
    public String getPacketFormat() {
        return PACKET_FORMAT;
    }

    @Override
    public long getPacketId() {
        return packetId;
    }

    @Override
    public Pattern getPacketPattern() {
        return PACKET_PATTERN;
    }

    @Override
    public Object[] getParameters() {
        return new Object[] { packetId, username, port };
    }

    public int getPort() {
        return port;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
