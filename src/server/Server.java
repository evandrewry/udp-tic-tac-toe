package server;

import exception.BadPacketException;
import game.Game;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.packet.LoginAcknowledgementType;
import server.packet.ServerPacket;
import server.packet.impl.LoginAcknowledgementPacket;
import server.packet.impl.PlayRequestAcknowledgementPacket;
import client.packet.ClientPacket;
import client.packet.ClientPacketType;
import client.packet.impl.ChoosePlayerPacket;
import client.packet.impl.LoginPacket;

import common.User;
import common.UserList;
import common.UserState;

public class Server {
    private final DatagramSocket socket;
    private final ExecutorService pool;
    private UserList currentUsers = new UserList();
    private ArrayList<Game> currentGames = new ArrayList<Game>();

    private Server() throws IOException {
        socket = new DatagramSocket(4119);
        pool = Executors.newFixedThreadPool(2);
    }

    public ServerPacket respond(ClientPacket packet) {
        switch (packet.getPacketType()) {
            case LOGIN:
                return handleLogin((LoginPacket) packet);
            case QUERY_LIST:
                return null;
            case CHOOSE_PLAYER:
                return null;
            case ACCEPT_REQUEST:
                return null;
            case DENY_REQUEST:
                return null;
            case PLAY_GAME:
                return null;
            case LOGOUT:
                return null;
            default:
                throw new BadPacketException("Unrecognized packet format");
        }
    }

    /**
     *
     * @param packet
     * @return
     */
    private ServerPacket handleLogin(LoginPacket packet) {
        // try to find a user with the input username
        User u = currentUsers.get(packet.getUsername());

        // register user if no user exists with this username
        if (u == null) {
            u = new User(packet.getUsername(), UserState.OFFLINE, packet.getPort());
            currentUsers.put(u.getUsername(), u);
        }

        if (u.getState() == UserState.OFFLINE) {
            // login success for offline/newly registered user
            u.setState(UserState.FREE);
            return new LoginAcknowledgementPacket(LoginAcknowledgementType.SUCCESS);

        } else if (u.getState() == UserState.BUSY || u.getState() == UserState.DECISION
                || u.getState() == UserState.FREE) {
            // login failure for online user
            return new LoginAcknowledgementPacket(LoginAcknowledgementType.FAILURE);

        } else {
            // this should not be reachable
            throw new IllegalStateException();
        }
    }

    public void recieve() throws SocketException {
        pool.execute(new UDPReciever(socket, this));
    }

    public static void main(String[] args) throws SocketException, IOException {
        (new Server()).recieve();
    }

}
