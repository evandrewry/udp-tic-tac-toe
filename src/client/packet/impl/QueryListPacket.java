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
 * Packet sent to retrieve to list of users from the server
 *
 * @author evan
 *
 */
public class QueryListPacket extends ClientPacket {

    public static final String PACKET_FORMAT = "list,%d,%s";
    public static final Pattern PACKET_PATTERN = Pattern.compile("^list,(\\d+),(\\w+)$");
    public static final String COMMAND = "ls";
    public static final Pattern COMMAND_PATTERN = Pattern.compile("^ls$");

    public static QueryListPacket fromCommand(Command command, Client handler) {
        Matcher m = COMMAND_PATTERN.matcher(command.content);
        if (m.matches()) {
            long packetId = Packet.nextId();
            String username = handler.getCurrentUser().getUsername();
            return new QueryListPacket(packetId, username);
        } else {
            throw new InvalidCommandParametersException("Could not parse.");
        }
    }
    public static QueryListPacket fromPayload(Payload payload) {
        Matcher m = PACKET_PATTERN.matcher(payload.content);
        if (m.matches()) {
            int packetId = Integer.parseInt(m.group(1));
            String username = m.group(2);
            return new QueryListPacket(packetId, username);
        } else {
            throw new BadPacketException("Could not parse.");
        }
    }

    private final long packetId;

    private String username;

    public QueryListPacket(long packetId, String username) {
        this.packetId = packetId;
        this.username = username;
    }

    public QueryListPacket(String... params) {
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
