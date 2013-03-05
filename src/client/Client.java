package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.packet.LoginAcknowledgementType;
import server.packet.ServerPacket;
import server.packet.impl.CurrentGameStatePacket;
import server.packet.impl.CurrentUsersListPacket;
import server.packet.impl.GameResultPacket;
import server.packet.impl.LoginAcknowledgementPacket;
import server.packet.impl.PlayRequestAcknowledgementPacket;
import server.packet.impl.PlayRequestPacket;
import client.packet.ClientPacket;

import common.Payload;
import common.User;

import exception.BadPacketException;

public class Client {
	private final DatagramSocket socket;
	private final ExecutorService pool;
	private User currentUser;
	private final String receiverIP;
	private final int receiverPort;

	public Client(String serverIp, int serverPort) throws IOException {
		this.receiverIP = serverIp;
		this.receiverPort = serverPort;
		socket = new DatagramSocket();
		pool = Executors.newFixedThreadPool(2);
	}

	private ClientPacket handle(ServerPacket packet) {
		switch (packet.getPacketType()) {
		case ACK:
			return null;
		case CURRENT_GAME_STATE:
			return handleCurrentGameState((CurrentGameStatePacket) packet);
		case CURRENT_USERS_LIST:
			return handleUserList((CurrentUsersListPacket) packet);
		case GAME_RESULT:
			return handleGameResult((GameResultPacket) packet);
		case ILLEGAL_MOVE:
			return null;
		case LOGIN_ACK:
			return handleLoginAck((LoginAcknowledgementPacket) packet);
		case PLAY_REQUEST_ACK:
			return handlePlayRequestAck((PlayRequestAcknowledgementPacket) packet);
		case PLAY_REQUEST:
			return handlePlayRequest((PlayRequestPacket) packet);
		default:
			throw new BadPacketException("Unrecognized packet format");
		}
	}
	
	private ClientPacket handleGameResult(GameResultPacket packet) {
		String message = currentUser.getUsername();
		switch (packet.getResult()) {
		case DRAW: message += " draw"; break;
		case LOSS: message += " lose"; break;
		case WIN: message += " win"; break;
		default:
			break;
		}
		System.out.println(message);
		return null;
	}

	private ClientPacket handleCurrentGameState(CurrentGameStatePacket packet) {
		System.out.println(packet.toFormattedString());
		return null;
	}

	private ClientPacket handlePlayRequest(PlayRequestPacket packet) {
		System.out.println(packet.toFormattedString());
		return null;
	}

	private ClientPacket handlePlayRequestAck(
			PlayRequestAcknowledgementPacket packet) {
		System.out.println(packet.toFormattedString());
		return null;
	}

	private ClientPacket handleUserList(CurrentUsersListPacket packet) {
		System.out.println(packet.getUsers().toFormattedString());
		return null;
	}

	private ClientPacket handleLoginAck(LoginAcknowledgementPacket packet) {
		String message = "login ";
		message += packet.getAcktype() == LoginAcknowledgementType.SUCCESS ? "success " : "failure ";
		message += currentUser.getUsername();
		System.out.println(message);
		return null;
	}

	public void respond(DatagramPacket p) throws UnknownHostException {
		Payload payload = new Payload(new String(p.getData(), 0, p.getLength()));
		ServerPacket sp = ServerPacket.fromPayload(payload);
		handle(sp);
	}

	private void run() throws SocketException {
	    send();
	    recieve();
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
    
    
	public static void main(String[] args) throws IOException {
		Client c = new Client("localhost", 4312);
		c.run();
	}
}
