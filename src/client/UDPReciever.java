package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Client UDP reciever that receives packets from the server and notifies the client
 *
 * @author evan
 *
 */
public class UDPReciever implements Runnable {
    private final DatagramSocket socket;
    private final Client handler;
    private static final int BUFFER_SIZE = 1024;
    private static final String SUCCESS_MSG_FMT = "Receiving at port %d ...\n";

    /**
     * Receive for specified client at specified socket.
     *
     * @param socket
     * @param handler
     */
    public UDPReciever(DatagramSocket socket, Client handler) {
        this.socket = socket;
        this.handler = handler;
    }

    @Override
    public void run() {
        System.out.printf(SUCCESS_MSG_FMT, socket.getLocalPort());

        /*
         * Begin to receive UDP packet (no connection is set up for UDP)
         */
        byte[] buffer = new byte[BUFFER_SIZE];
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                /*
                 * String ip = packet.getAddress().getHostAddress(); int port = packet.getPort(); Payload cmd = new
                 * Payload(new String(buffer, 0, packet.getLength())); System.out.println("[" +
                 * Calendar.getInstance().getTimeInMillis() + "] Receive from sender (IP: " + ip + ", Port: " +
                 * String.valueOf(port) + "): " + cmd);
                 */
                handler.respond(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
