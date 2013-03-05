package server.packet.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import server.packet.LoginAcknowledgementType;
import server.packet.ServerPacket;

import common.Payload;

import exception.BadPacketException;

/**
 * acklogin packet
 *
 * @author evan
 *
 */
public class LoginAcknowledgementPacket extends ServerPacket {
    public static LoginAcknowledgementPacket fromPayload(Payload payload) {
        Matcher m = PACKET_PATTERN.matcher(payload.content);
        if (m.matches()) {
            LoginAcknowledgementType acktype = LoginAcknowledgementType.fromCode(m.group(1));
            return new LoginAcknowledgementPacket(acktype);
        } else {
            throw new BadPacketException("Could not parse.");
        }
    }
    private LoginAcknowledgementType acktype;
    public static final String PACKET_FORMAT = "acklogin,%s";

    public static final Pattern PACKET_PATTERN = Pattern.compile("^acklogin,(\\w+)$");

    public LoginAcknowledgementPacket(LoginAcknowledgementType acktype) {
        this.acktype = acktype;
    }

    public LoginAcknowledgementType getAcktype() {
        return this.acktype;
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
        return new Object[] { acktype.toString() };
    }
}
