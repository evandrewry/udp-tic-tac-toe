package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Calendar;

public class UDPReciever implements Runnable {
	private int port = 4119;
	private int bufferSize = 1024;
	private static final String SUCCESS_MSG_FMT = "Receiving at port %d ...";

	@Override
	public void run() {
		// Create a DatagramSocket to receive UDP packets
		DatagramSocket socket;
		try {
			socket = new DatagramSocket(port);

			System.out.printf(SUCCESS_MSG_FMT, port);

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
					System.out.println("["
							+ Calendar.getInstance().getTimeInMillis()
							+ "] Receive from sender (IP: " + ip + ", Port: "
							+ String.valueOf(port) + "): "
							+ new String(buffer, 0, packet.getLength()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
}
