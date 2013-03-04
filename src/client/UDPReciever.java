package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Calendar;

import common.Packet;
import common.Payload;

import server.packet.ServerPacket;

public class UDPReciever implements Runnable {
	private final DatagramSocket socket;
	private final Client handler;
	private int bufferSize = 1024;
	private static final String SUCCESS_MSG_FMT = "Receiving at port %d ...";

	public UDPReciever(DatagramSocket socket, Client handler) {
		this.socket = socket;
		this.handler = handler;
	}

	@Override
	public void run() {
		while (true) {
			System.out.printf(SUCCESS_MSG_FMT, socket.getLocalPort());

			/*
			 * Begin to receive UDP packet No connection is set up for UDP
			 */
			byte[] buffer = new byte[bufferSize];
			while (true) {
				DatagramPacket packet = new DatagramPacket(buffer,
						buffer.length);
				try {
					socket.receive(packet);
					String ip = packet.getAddress().getHostAddress();
					int port = packet.getPort();
					Payload cmd = new Payload(new String(buffer, 0, packet.getLength()));
					System.out.println("["
							+ Calendar.getInstance().getTimeInMillis()
							+ "] Receive from sender (IP: " + ip + ", Port: "
							+ String.valueOf(port) + "): "
							+ cmd);
					ServerPacket p = ServerPacket.fromPayload(cmd);
					handler.respond(p);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}