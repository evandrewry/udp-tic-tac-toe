package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientShell implements Runnable {
	private static final String receiverIP = "127.0.0.1";
	private static final int receiverPort = 4119;

	private static void loop(DatagramSocket senderSocket) {
		// Begin to send
		BufferedReader input = new BufferedReader(new InputStreamReader(
				System.in));
		
		byte[] buffer;
		while (true) {
			String inputString;
			try {
				inputString = input.readLine();
			} catch (IOException e) {
				senderSocket.close();
				e.printStackTrace();
				continue;
			}
			buffer = inputString.getBytes();
			DatagramPacket sendPacket;
			try {
				sendPacket = new DatagramPacket(buffer, buffer.length,
						InetAddress.getByName(receiverIP), receiverPort);
			} catch (UnknownHostException e) {
				senderSocket.close();
				e.printStackTrace();
				continue;
			}
			try {
				senderSocket.send(sendPacket);
			} catch (IOException e) {
				senderSocket.close();
				e.printStackTrace();
				continue;
			}
			System.out.println("Sent to server: " + inputString);
		}

	}

	@Override
	public void run() {
		// Create DatagramSocket
		DatagramSocket senderSocket;
		try {
			senderSocket = new DatagramSocket();
			loop(senderSocket);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
	}

}
