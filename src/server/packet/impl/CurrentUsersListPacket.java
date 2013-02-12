package server.packet.impl;

import server.packet.ServerPacket;
import common.UserList;

public class CurrentUsersListPacket extends ServerPacket {
    private UserList currentUsers;
    public static final String PACKET_FORMAT = "ackls,%s";

    public CurrentUsersListPacket(UserList currentUsers) {
        this.currentUsers = currentUsers;
    }

    @Override
    public String getPacketFormat() {
        return PACKET_FORMAT;
    }

    @Override
    public Object[] getParameters() {
        return new Object[] { currentUsers.toString() };
    }

}
