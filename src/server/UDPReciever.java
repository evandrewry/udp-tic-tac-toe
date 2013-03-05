package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;
import java.util.Calendar;

import common.Payload;

/**
 * server UDP reciever that receives packets from the client and notifies server so it can respond
 *
 * @author evan
 *
 */
public class UDPReciever implements Runnable {
    private final DatagramSocket socket;
    private final Server handler;
    private static final int BUFFER_SIZE = 1024;

    private static final String SUCCESS_MSG_FMT = "Receiving at port %d ...\n";

    public UDPReciever(DatagramSocket socket, Server handler) {
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
            byte[] buffer = new byte[BUFFER_SIZE];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                try {
                    socket.receive(packet);
                    String ip = packet.getAddress().getHostAddress();
                    int port = packet.getPort();
                    Payload payload = new Payload(new String(buffer, 0, packet.getLength()));
                    System.out.println("[" + Calendar.getInstance().getTimeInMillis() + "] Receive from sender (IP: "
                            + ip + ", Port: " + String.valueOf(port) + "): " + payload);
                    handler.respond(packet);
                } catch (UnknownHostException e) {
                    System.out.println("unknown host...");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
