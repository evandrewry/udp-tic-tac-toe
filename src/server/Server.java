package server;

import game.Game;

import java.util.ArrayList;

import packet.server.ServerPacket;

import common.UserList;

public class Server {
    private UserList currentUsers = new UserList();
    private ArrayList<Game> currentGames = new ArrayList<Game>();
    private long packetIdCounter = 0L;

    private ServerPacket respond(String clientPacket) {
        return null;//TODO
    }

    public static void main(String[] args) {
        new Thread(new ServerThread()).start();
    }

}
