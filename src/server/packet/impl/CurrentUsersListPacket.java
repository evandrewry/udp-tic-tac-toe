package server.packet.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import server.packet.ServerPacket;

import common.Payload;
import common.UserList;

import exception.BadPacketException;

/**
 * Packet with list of users in payload
 *
 * @author evan
 *
 */
public class CurrentUsersListPacket extends ServerPacket {
    public static CurrentUsersListPacket fromPayload(Payload payload) {
        Matcher m = PACKET_PATTERN.matcher(payload.content);
        if (m.matches()) {
            UserList users = UserList.fromString(m.group(1));
            return new CurrentUsersListPacket(users);
        } else {
            throw new BadPacketException("Could not parse.");
        }
    }
    private UserList users;
    public static final String PACKET_FORMAT = "ackls,%s";

    public static final Pattern PACKET_PATTERN = Pattern.compile("^ackls,(((\\w+),?)+)$");

    public CurrentUsersListPacket(UserList currentUsers) {
        this.users = currentUsers;
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
        return new Object[] { users.toString() };
    }

    public UserList getUsers() {
        return this.users;
    }

}
