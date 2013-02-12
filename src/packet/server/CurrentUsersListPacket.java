package packet.server;


import common.UserList;

import packet.Packet;

public class CurrentUsersListPacket extends Packet {
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
