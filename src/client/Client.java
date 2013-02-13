package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import server.packet.ServerPacket;
import server.packet.ServerPacketType;
import client.packet.ClientPacket;
import exception.BadPacketException;

public class Client implements Runnable {
	private static String username;
	private ClientPacket respond(ServerPacket packet) {
		switch (ServerPacketType.fromClass(packet.getClass())) {
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
	private static final String PROMPT = ">>> ";
	private static final String receiverIP = "localhost";
	private static final int receiverPort = 4119;

	private static void loop(DatagramSocket socket) {
		// Begin to send
		BufferedReader input = new BufferedReader(new InputStreamReader(
				System.in));

		byte[] buffer;
		while (true) {
			System.out.print(PROMPT);

			String inputString;
			try {
				inputString = input.readLine();
			} catch (IOException e) {
				socket.close();
				e.printStackTrace();
				continue;
			}

			buffer = inputString.getBytes();
			DatagramPacket sendPacket;
			try {
				sendPacket = new DatagramPacket(buffer, buffer.length,
						InetAddress.getByName(receiverIP), receiverPort);
			} catch (UnknownHostException e) {
				socket.close();
				e.printStackTrace();
				continue;
			}

			try {
				socket.send(sendPacket);
			} catch (IOException e) {
				socket.close();
				e.printStackTrace();
				continue;
			}

			System.out.println("Sent to server: " + inputString);
		}
	}

	@Override
	public void run() {
		// Create DatagramSocket
		DatagramSocket socket;
		try {
			socket = new DatagramSocket();
			loop(socket);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	private ClientPacket ackResponse(ServerPacket packet) {
		return null;
	}

	public static void main(String[] args) {
		new Thread(new Client()).start();
	}

	public static String getUsername() {
		return username;
	}
	
	public void login(String username) {
		
	}

}
