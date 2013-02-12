package server.packet.impl;

import server.packet.ServerPacket;
import game.GameResultType;

public class GameResultPacket extends ServerPacket {
    private final GameResultType result;
    public static final String PACKET_FORMAT = "result,%s";

    public GameResultPacket(GameResultType result) {
        this.result = result;
    }

    @Override
    public String getPacketFormat() {
        return PACKET_FORMAT;
    }

    @Override
    public Object[] getParameters() {
        return new Object[] { result.getCode() };
    }

}
