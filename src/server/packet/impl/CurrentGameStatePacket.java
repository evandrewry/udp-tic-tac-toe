package server.packet.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import server.packet.ServerPacket;

import common.Payload;

import exception.BadPacketException;
import game.Game;

/**
 * Packet with current game board in payload
 *
 * @author evan
 *
 */
public class CurrentGameStatePacket extends ServerPacket {
    public static CurrentGameStatePacket fromPayload(Payload payload) {
        Matcher m = PACKET_PATTERN.matcher(payload.content);
        if (m.matches()) {
            String state = m.group(1);
            return new CurrentGameStatePacket(state);
        } else {
            throw new BadPacketException("Could not parse.");
        }
    }
    private String state;
    public static final String PACKET_FORMAT = "play,%s";

    public static final Pattern PACKET_PATTERN = Pattern.compile("^play,(\\d+)$");

    public CurrentGameStatePacket(Game g) {
        this.state = g.toString();
    }

    public CurrentGameStatePacket(String state) {
        this.state = state;
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
        return new Object[] { state };
    }

    public String toFormattedString() {
        String s = state.replace('0', '_');
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sb.append(s.substring(i * 3, i * 3 + 3)).append('\n');
        }
        return sb.toString();
    }

}
