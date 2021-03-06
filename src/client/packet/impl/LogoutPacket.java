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
 * Packet sent to log out of server
 *
 * @author evan
 *
 */
public class LogoutPacket extends ClientPacket {
    public static LogoutPacket fromCommand(Command command, Client handler) {
        Matcher m = COMMAND_PATTERN.matcher(command.content);
        if (m.matches()) {
            long packetId = Packet.nextId();
            String username = handler.getCurrentUser().getUsername();
            return new LogoutPacket(packetId, username);
        } else {
            throw new InvalidCommandParametersException("Could not parse.");
        }
    }
    public static LogoutPacket fromPayload(Payload payload) {
        Matcher m = PACKET_PATTERN.matcher(payload.content);
        if (m.matches()) {
            int packetId = Integer.parseInt(m.group(1));
            String username = m.group(2);
            return new LogoutPacket(packetId, username);
        } else {
            throw new BadPacketException("Could not parse.");
        }
    }
    private final long packetId;
    private final String username;
    public static final String PACKET_FORMAT = "logout,%d,%s";
    public static final Pattern PACKET_PATTERN = Pattern.compile("^logout,(\\d+),(\\w+)$");

    public static final String COMMAND = "logout";

    public static final Pattern COMMAND_PATTERN = Pattern.compile("^logout$");

    public LogoutPacket(long packetId, String username) {
        this.packetId = packetId;
        this.username = username;
    }

    public LogoutPacket(String... params) {
        this(Integer.parseInt(params[1]), params[2]);
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
        return this.packetId;
    }

    @Override
    public Pattern getPacketPattern() {
        return PACKET_PATTERN;
    }

    @Override
    public Object[] getParameters() {
        return new Object[] { packetId, username };
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
