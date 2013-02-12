package server.packet.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import server.packet.ServerPacket;

import common.UserList;

import exception.BadPacketException;

public class CurrentUsersListPacket extends ServerPacket {
    private UserList currentUsers;
    public static final String PACKET_FORMAT = "ackls,%s";
    public static final Pattern PACKET_PATTERN = Pattern.compile("^ackls,(((\\w+),?)+)$");

    public CurrentUsersListPacket(UserList currentUsers) {
        this.currentUsers = currentUsers;
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
        return new Object[] { currentUsers.toString() };
    }

    @Override
    public CurrentUsersListPacket fromPayload(String payload) {
        Matcher m = PACKET_PATTERN.matcher(payload);
        if (m.matches()) {
            UserList currentUsers = new UserList(c);
            return new CurrentUsersListPacket(packetId);
        } else {
            throw new BadPacketException("Could not parse.");
        }
    }

}
