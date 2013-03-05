package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import client.packet.ClientPacket;
import client.packet.impl.LoginPacket;
import client.packet.impl.LogoutPacket;
import exception.InvalidClientCommandException;
import exception.InvalidCommandParametersException;

/**
 * Client UDP sender that takes input from console, converts it to a packet, and sends it to the server.
 *
 * @author evan
 *
 */
public class UDPSender implements Runnable {
    private final DatagramSocket socket;
    private static final int BUFFER_SIZE = 1024;
    private static final int ACK_TIMEOUT = 1000;
    private final String receiverIP;
    private final int receiverPort;
    private Client handler;

    /**
     * Send from specified client and specified socket to specified ip and port
     *
     * @param socket
     * @param recieverIP
     * @param receiverPort
     * @param handler
     */
    public UDPSender(DatagramSocket socket, String recieverIP, int receiverPort, Client handler) {
        this.socket = socket;
        this.receiverIP = recieverIP;
        this.receiverPort = receiverPort;
        this.handler = handler;
    }

    @Override
    public void run() {
        // Begin to send
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

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
            if (handler.getCurrentUser() == null
                    && !Pattern.matches(LoginPacket.COMMAND_PATTERN.toString(), inputString)) {
                System.out.println("not logged in");
                continue;
            }

            //convert the command to a packet payload
            try {
                buffer = ClientPacket.fromCommand(new Command(inputString), handler).toPayload().getBytes();
            } catch (InvalidCommandParametersException e) {
                System.out.println("invalid command parameters");
                continue;
            } catch (InvalidClientCommandException e) {
                System.out.println("invalid command");
                continue;
            }

            //create packet to send
            DatagramPacket sendPacket;
            try {
                sendPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(receiverIP), receiverPort);
            } catch (UnknownHostException e) {
                socket.close();
                e.printStackTrace();
                break;
            }

            //send the packet to the server
            boolean acked = false;
            do {
                //try to send
                try {
                    socket.send(sendPacket);
                } catch (IOException e) {
                    socket.close();
                    e.printStackTrace();
                    return;
                }

                //wait for ack, or resend packet
                byte[] ackbuffer = new byte[BUFFER_SIZE];
                DatagramPacket ackPacket = new DatagramPacket(ackbuffer, ackbuffer.length);
                try {
                    socket.setSoTimeout(ACK_TIMEOUT);
                    socket.receive(ackPacket);
                    /*
                     * String ip = ackPacket.getAddress().getHostAddress(); int port = ackPacket.getPort(); Payload
                     * payload = new Payload(new String(ackbuffer, 0, ackPacket.getLength())); System.out.println("[" +
                     * Calendar.getInstance().getTimeInMillis() + "] Receive from sender (IP: " + ip + ", Port: " +
                     * String.valueOf(port) + "): " + payload);
                     */
                    acked = true;
                } catch (SocketTimeoutException e) {
                    //no ack was received, need to try again.
                    acked = false;
                    System.out.println("no ack received! resending packet......");
                } catch (IOException e) {
                    socket.close();
                    e.printStackTrace();
                    return;
                }
            } while (!acked);

            //print log out message if user has logged out
            if (handler.getCurrentUser() != null
                    && Pattern.matches(LogoutPacket.COMMAND_PATTERN.toString(), inputString)) {
                handler.handleLogout();
            }
            /*
             * System.out.println("[" + Calendar.getInstance().getTimeInMillis() + "] Sent to server: (IP: " +
             * receiverIP + ", Port: " + String.valueOf(receiverPort) + "): " + inputString);
             */
        }

        socket.close();

    }

}
