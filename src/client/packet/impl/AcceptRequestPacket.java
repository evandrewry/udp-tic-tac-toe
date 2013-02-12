package client.packet.impl;

import java.util.regex.Pattern;

import client.packet.ClientPacket;

public class AcceptRequestPacket extends ClientPacket {
    private int packetId;
    private String sender;
    private String reciever;
    public static final String PACKET_FORMAT = "ackchoose,%d,%s,%s,A";
    public static final Pattern PACKET_PATTERN = Pattern.compile("^ackchoose,(\\d+),(\\w+),(\\w+),A$");
    public static final String COMMAND = "accept";
    public static final Pattern COMMAND_PATTERN = Pattern.compile("^accept\\s+(\\w+)$");
    public static final String CODE = "accept";

    public AcceptRequestPacket(int packetId, String sender, String reciever) {
        this.packetId = packetId;
        this.sender = sender;
        this.reciever = reciever;
    }

    public AcceptRequestPacket(String... params) {
        this(Integer.parseInt(params[1]), params[2], params[3]);
    }

    @Override
    public String getPacketFormat() {
        return PACKET_FORMAT;
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
}
