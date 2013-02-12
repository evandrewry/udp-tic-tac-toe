package common;
import java.util.ArrayList;
import java.util.TreeSet;

import packet.client.ClientPacket;
import packet.server.ServerPacket;

import common.Game;
import common.User;
import common.UserList;


public class Server {
	private UserList currentUsers = new UserList();
	private ArrayList<Game> currentGames = new ArrayList<Game>();
	private long packetIdCounter = 0L;
	
	private ServerPacket respond(String clientPacket) {
		return null;//TODO
	}
	
	public static void main(String[] args) {

	}

}
