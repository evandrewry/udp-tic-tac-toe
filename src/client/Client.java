package client;
import client.packet.*;
import packet.*;
import server.packet.ServerPacket;


public class Client {


	private ServerPacket respond(String serverPacket) {
		return null;//TODO
	}
	public static void main(String[] args) {
		new Thread(new ClientThread()).start();
	}

}
