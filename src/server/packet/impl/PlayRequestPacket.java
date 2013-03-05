package server.packet.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import server.packet.ServerPacket;

import common.Payload;

import exception.BadPacketException;

/**
 * Packet sent to tell user that someone has requested to play with them
 *
 * @author evan
 *
 */
public class PlayRequestPacket extends ServerPacket {

    public static PlayRequestPacket fromPayload(Payload payload) {
        Matcher m = PACKET_PATTERN.matcher(payload.content);
        if (m.matches()) {
            String username = m.group(1);
            return new PlayRequestPacket(username);
        } else {
            throw new BadPacketException("Could not parse.");
        }
    }
    private String username;
    public static final String PACKET_FORMAT = "request,%s";

    public static final Pattern PACKET_PATTERN = Pattern.compile("^request,(\\w+)$");

    public PlayRequestPacket(String username) {
        this.username = username;
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
        return new Object[] { username };
    }

    public String toFormattedString() {
        return "request from " + username;
    }
}
