package client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.packet.ServerPacket;
import client.packet.ClientPacket;

import common.User;

import exception.BadPacketException;

public class Client {
	private final DatagramSocket socket;
	private final ExecutorService pool;
	private User currentUser;
	private static final String PROMPT = ">>> ";
	private static final String receiverIP = "localhost";
	private static final int receiverPort = 4119;

	public Client() throws IOException {
		socket = new DatagramSocket();
		pool = Executors.newFixedThreadPool(2);
	}

	ClientPacket respond(ServerPacket packet) {
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

	private void run() throws SocketException {
	    send();
	    recieve();
	}

	private ClientPacket ackResponse(ServerPacket packet) {
		return null;
	}

	public static void main(String[] args) throws IOException {
		Client c = new Client();
		c.run();
	}

	public void recieve() throws SocketException {
		pool.execute(new UDPReciever(socket, this));
	}

	public void send() throws SocketException {
		pool.execute(new UDPSender(new DatagramSocket(), receiverIP, receiverPort, this));
	}

	public void login(String username, String ip, int port) {
		currentUser = new User(username, ip, port);
	}

	public User getCurrentUser() {
		return currentUser;
	}

    public int getPort() {
        return socket.getLocalPort();
    }
    
    public String getIP() {
    	return socket.getLocalAddress().getHostAddress();
    }

}
