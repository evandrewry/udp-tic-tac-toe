package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Calendar;

public class ServerThread implements Runnable {
	public static final int PORT = 4119;
	public static final int bufferSize = 1024;

	@Override
	public void run() {
		/*
		 * Create a DatagramSocket DatagramSocket is used to receive UDP packets
		 * Do forget to bind the receiving port here
		 */
		DatagramSocket socket;
		try {
			socket = new DatagramSocket(PORT);

			System.out.println("Receiving at port " + String.valueOf(PORT)
					+ " ...");

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
