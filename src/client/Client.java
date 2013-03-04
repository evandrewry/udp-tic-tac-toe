package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;

import server.UDPReciever;
import server.packet.ServerPacket;
import client.packet.ClientPacket;

import common.User;

import exception.BadPacketException;

public class Client {
	private final DatagramSocket socket;
	private final ExecutorService pool;
	private static User currentUser;
	private static final String PROMPT = ">>> ";
	private static final String receiverIP = "localhost";
	private static final int receiverPort = 4119;
	
	private Client() throws IOException {
		socket = new DatagramSocket(4119);
		pool = Executors.newFixedThreadPool(1);
	}

	private ClientPacket respond(ServerPacket packet) {
		switch (packet.getPacketType()) {
		case ACK:
			return null;
		case CURRENT_GAME_STATE:
			return null;
		case CURRENT_USERS_LIST:
			return null;
		case GAME_RESULT:
			return null;
		case ILLEGAL_MOVE:
			return null;
		case LOGIN_ACK:
			return null;
		case PLAY_REQUEST_ACK:
			return null;
		case PLAY_REQUEST:
			return null;
		default:
			throw new BadPacketException("Unrecognized packet format");
		}
	}

	private void loop(DatagramSocket socket) {

	}

	private ClientPacket ackResponse(ServerPacket packet) {
		return null;
	}

	public static void main(String[] args) throws IOException {
		Client c = new Client();
		c.loop(socket);
	}
	
	public void recieve() throws SocketException {
		pool.execute(new UDPReciever(new DatagramSocket()));
	}
	
	public void send() {
		pool.execute(new UDPSender());
	}

	public static void mocklogin(String username, int port) {
		currentUser = new User(username, port);
	}

	public static User getCurrentUser() {
		return currentUser;
	}

}
