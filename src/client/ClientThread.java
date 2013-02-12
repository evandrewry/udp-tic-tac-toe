package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientThread implements Runnable {
    private static final String PROMPT = ">>> ";
    private static final String receiverIP = "localhost";
    private static final int receiverPort = 4119;

    private static void loop(DatagramSocket socket) {
        // Begin to send
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

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
                sendPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(receiverIP), receiverPort);
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

    @Override
    public void run() {
        // Create DatagramSocket
        DatagramSocket socket;
        try {
            socket = new DatagramSocket();
            loop(socket);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
