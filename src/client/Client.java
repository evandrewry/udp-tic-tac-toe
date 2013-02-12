package client;

import client.packet.ClientPacket;
import server.packet.ServerPacket;
import server.packet.ServerPacketType;

public class Client {

    private ClientPacket respond(String serverPacketPayload) {
        ServerPacket sp = ServerPacketType.fromPacketPayload(serverPacketPayload)
    }

    public static void main(String[] args) {
        new Thread(new ClientThread()).start();
    }

}
