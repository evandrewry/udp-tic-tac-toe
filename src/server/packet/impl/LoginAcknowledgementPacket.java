package server.packet.impl;

import java.util.regex.Pattern;

import server.packet.LoginAcknowledgementType;
import server.packet.ServerPacket;

public class LoginAcknowledgementPacket extends ServerPacket {
    private LoginAcknowledgementType acktype;
    public static final String PACKET_FORMAT = "acklogin,%s";
    public static final Pattern PACKET_PATTERN = Pattern.compile("^acklogin,(\\w+)$");

    public LoginAcknowledgementPacket(LoginAcknowledgementType acktype) {
        this.acktype = acktype;
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
