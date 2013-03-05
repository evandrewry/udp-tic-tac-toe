package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.regex.Pattern;

import client.packet.ClientPacket;
import client.packet.impl.LoginPacket;

public class UDPSender implements Runnable {
	private final DatagramSocket socket;
	private static final String PROMPT = ">>> ";
	private final String receiverIP;
	private final int receiverPort;
    private Client handler;

	public UDPSender(DatagramSocket socket, String recieverIP, int receiverPort, Client handler) {
		this.socket = socket;
		this.receiverIP = recieverIP;
		this.receiverPort = receiverPort;
		this.handler = handler;
	}
	@Override
	public void run() {
		// Begin to send
		BufferedReader input = new BufferedReader(new InputStreamReader(
				System.in));

		byte[] buffer;
		while (true) {
			
			//read input from console
			String inputString;
			try {
				inputString = input.readLine();
			} catch (IOException e) {
				socket.close();
				e.printStackTrace();
				continue;
			}
			
			//make sure user is logged in
			if (handler.getCurrentUser() == null && !Pattern.matches(LoginPacket.COMMAND_PATTERN.toString(), inputString)){
				System.out.println("not logged in");
				continue;
			}
			
			//send the packet
			buffer = ClientPacket.fromCommand(new Command(inputString), handler).toPayload().getBytes();
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

			System.out.println("["
					+ Calendar.getInstance().getTimeInMillis()
					+ "] Sent to server: (IP: " + receiverIP + ", Port: "
					+ String.valueOf(receiverPort) + "): " + inputString);
		}
	}

}
