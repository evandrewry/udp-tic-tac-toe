package common;
import client.ClientShell;
import packet.*;
import packet.client.*;
import packet.server.ServerPacket;


public class Client {


	private ServerPacket respond(String serverPacket) {
		return null;//TODO
	}
	public static void main(String[] args) {
		new Thread(new ClientShell()).start();
	}

}
