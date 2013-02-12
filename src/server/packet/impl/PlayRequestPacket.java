package server.packet.impl;

import server.packet.ServerPacket;

public class PlayRequestPacket extends ServerPacket {

    private String requesterUsername;
    public static final String PACKET_FORMAT = "request,%s";

    @Override
    public String getPacketFormat() {
        return null;
    }

    @Override
    public Object[] getParameters() {
        return new Object[] { requesterUsername };
    }

}