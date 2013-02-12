package client.packet.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.packet.ClientPacket;
import exception.BadPacketException;

public class QueryListPacket extends ClientPacket {
    private int packetId;
    private String username;

    public static final String PACKET_FORMAT = "list,%d,%s";
    public static final Pattern PACKET_PATTERN = Pattern.compile("^list,(\\d+),(\\w+)$");
    public static final String COMMAND = "list";
    public static final Pattern COMMAND_PATTERN = Pattern.compile("^ls$");

    public QueryListPacket(int packetId, String username) {
        this.packetId = packetId;
        this.username = username;
    }

    public QueryListPacket(String... params) {
        this(Integer.parseInt(params[1]), params[2]);
    }

    @Override
    public String getPacketFormat() {
        return PACKET_FORMAT;
    }

    @Override
    public Pattern getPacketPattern() {
        return PACKET_PATTERN;
    }

    @Override
    public Pattern getCommandPattern() {
        return COMMAND_PATTERN;
    }

    @Override
    public Object[] getParameters() {
        return new Object[] { packetId, username };
    }

    @Override
    public String getCommand() {
        return COMMAND;
    }

    @Override
    public QueryListPacket fromPayload(String payload) {
        Matcher m = PACKET_PATTERN.matcher(payload);
        if (m.matches()) {
            int packetId = Integer.parseInt(m.group(1));
            String username = m.group(2);
            return new QueryListPacket(packetId, username);
        } else {
            throw new BadPacketException("Could not parse.");
        }
    }
}
