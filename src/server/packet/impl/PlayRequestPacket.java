package server.packet.impl;

import java.util.regex.Pattern;

import server.packet.ServerPacket;

public class PlayRequestPacket extends ServerPacket {

    private String username;
    public static final String PACKET_FORMAT = "request,%s";
    public static final Pattern PACKET_PATTERN = Pattern.compile("^request,(\\w+)$");

    @Override
    public String getPacketFormat() {
        return null;
    }

    @Override
    public Pattern getPacketPattern() {
        return PACKET_PATTERN;
    }

    @Override
    public Object[] getParameters() {
        return new Object[] { username };
    }

}
