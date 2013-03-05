package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.regex.Pattern;

import common.Payload;

import client.packet.ClientPacket;
import client.packet.impl.LoginPacket;
import client.packet.impl.LogoutPacket;
import exception.InvalidClientCommandException;

public class UDPSender implements Runnable {
	private final DatagramSocket socket;
	private static final String PROMPT = ">>> ";
	private static final int BUFFER_SIZE = 1024;
	private static final int ACK_TIMEOUT = 1000;
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
			try {
				buffer = ClientPacket.fromCommand(new Command(inputString), handler).toPayload().getBytes();
			} catch (InvalidClientCommandException e) {
				System.out.println("invalid command");
				continue;
			}
			
			DatagramPacket sendPacket;
			try {
				sendPacket = new DatagramPacket(buffer, buffer.length,
						InetAddress.getByName(receiverIP), receiverPort);
			} catch (UnknownHostException e) {
				socket.close();
				e.printStackTrace();
				continue;
			}
			
			boolean acked = false;
			do{
				try {
					socket.send(sendPacket);
				} catch (IOException e) {
					socket.close();
					e.printStackTrace();
					continue;
				}

				byte[] ackbuffer = new byte[BUFFER_SIZE];
				DatagramPacket ackPacket = new DatagramPacket(ackbuffer,
						ackbuffer.length);
				try {
					socket.setSoTimeout(ACK_TIMEOUT);
					socket.receive(ackPacket);
					String ip = ackPacket.getAddress().getHostAddress();
					/*int port = ackPacket.getPort();
					Payload payload = new Payload(new String(ackbuffer, 0,
							ackPacket.getLength()));
					System.out.println("["
							+ Calendar.getInstance().getTimeInMillis()
							+ "] Receive from sender (IP: " + ip + ", Port: "
							+ String.valueOf(port) + "): " + payload);*/
					acked = true;
				} catch (SocketTimeoutException e) {
					acked = false;
					System.out.println("no ack received! resending packet......");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} while (!acked);

			if(handler.getCurrentUser() != null && Pattern.matches(LogoutPacket.COMMAND_PATTERN.toString(), inputString)) {
				System.out.println(handler.getCurrentUser().getUsername() + " logout");
			}
			/*System.out.println("["
					+ Calendar.getInstance().getTimeInMillis()
					+ "] Sent to server: (IP: " + receiverIP + ", Port: "
					+ String.valueOf(receiverPort) + "): " + inputString);*/
		}
	}

}
