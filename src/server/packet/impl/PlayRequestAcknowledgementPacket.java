package server.packet.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import server.packet.PlayRequestAcknowledgementStatus;
import server.packet.ServerPacket;

import common.Payload;

import exception.BadPacketException;

/**
 * ackplay packet
 *
 * @author evan
 *
 */
public class PlayRequestAcknowledgementPacket extends ServerPacket {
    public static PlayRequestAcknowledgementPacket fromPayload(Payload payload) {
        Matcher m = PACKET_PATTERN.matcher(payload.content);
        if (m.matches()) {
            String username = m.group(1);
            PlayRequestAcknowledgementStatus status = PlayRequestAcknowledgementStatus.fromCode(m.group(2));
            return new PlayRequestAcknowledgementPacket(username, status);
        } else {
            throw new BadPacketException("Could not parse.");
        }
    }
    private final String username;
    private final PlayRequestAcknowledgementStatus status;
    public static final String PACKET_FORMAT = "ackchoose,%s,%s";

    public static final Pattern PACKET_PATTERN = Pattern.compile("^ackchoose,(\\w+),(\\w+)$");

    public PlayRequestAcknowledgementPacket(String username, PlayRequestAcknowledgementStatus status) {
        this.username = username;
        this.status = status;
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
        return new Object[] { username, status.getCode() };
    }

    public PlayRequestAcknowledgementStatus getStatus() {
        return this.status;
    }

    public String getUsername() {
        return this.username;
    }

    public String toFormattedString() {
        switch (status) {
            case ACCEPTED:
                return "request accepted from " + this.username;
            case DENY:
                return "request denied by " + this.username;
            default:
                return "request to " + this.username + " failed";
        }
    }

}
