package client;

import server.packet.ServerPacket;
import server.packet.ServerPacketType;
import client.packet.ClientPacket;
import exception.BadPacketException;

public class Client {

    private ClientPacket respond(ServerPacket packet) {
        switch (ServerPacketType.fromClass(packet.getClass())) {
            case ACK:
                return null;
            case CURRENT_GAME_STATE:
                return null;
            case CURRENT_USERS_LIST:
                return null;
            case GAME_RESULT:
                return null;
            case ILLEGAL_MOVE:
                return null;
            case LOGIN_ACK:
                return null;
            case PLAY_REQUEST_ACK:
                return null;
            case PLAY_REQUEST:
                return null;
            default:
                throw new BadPacketException("Unrecognized packet format");
        }
    }

    private ClientPacket ackResponse(ServerPacket packet) {
        return null;
    }

    public static void main(String[] args) {
        new Thread(new ClientThread()).start();
    }

}
