package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPSender implements Runnable {
	private final DatagramSocket socket;
	private static final String PROMPT = ">>> ";
	private final String receiverIP;
	private final int receiverPort;
	
	public UDPSender(DatagramSocket socket, String recieverIP, int receiverPort) {
		this.socket = socket;
		this.receiverIP = recieverIP;
		this.receiverPort = receiverPort;
	}
	@Override
	public void run() {
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

}
