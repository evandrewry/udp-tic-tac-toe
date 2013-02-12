package client.packet.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.packet.ClientPacket;
import exception.BadPacketException;

public class DenyRequestPacket extends ClientPacket {
    private final int packetId;
    private final String sender;
    private final String reciever;

    public static final String PACKET_FORMAT = "ackchoose,%d,%s,%s,D";
    public static final Pattern PACKET_PATTERN = Pattern.compile("^ackchoose,(\\d+),(\\w+),(\\w+),D$");
    public static final String COMMAND = "deny";
    public static final Pattern COMMAND_PATTERN = Pattern.compile("^deny\\s+(\\w+)$");
    public static final String CODE = "ackchoose";

    public DenyRequestPacket(int packetId, String sender, String reciever) {
        this.packetId = packetId;
        this.sender = sender;
        this.reciever = reciever;
    }

    public DenyRequestPacket(String... params) {
        this(Integer.parseInt(params[1]), params[2], params[3]);
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
    public Object[] getParameters() {
        return new Object[] { packetId, sender, reciever };
    }

    @Override
    public Pattern getCommandPattern() {
        return COMMAND_PATTERN;
    }

    @Override
    public String getCommand() {
        return COMMAND;
    }

    @Override
    public DenyRequestPacket fromPayload(String payload) {
        Matcher m = PACKET_PATTERN.matcher(payload);
        if (m.matches()) {
            int packetId = Integer.parseInt(m.group(1));
            String sender = m.group(2);
            String reciever = m.group(3);
            return new DenyRequestPacket(packetId, sender, reciever);
        } else {
            throw new BadPacketException("Could not parse.");
        }
    }
}
