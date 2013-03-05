package server;

import exception.BadPacketException;
import game.Game;
import game.GameResultType;
import game.IllegalMoveException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.packet.IllegalMoveType;
import server.packet.LoginAcknowledgementType;
import server.packet.PlayRequestAcknowledgementStatus;
import server.packet.ServerPacket;
import server.packet.impl.AcknowledgementPacket;
import server.packet.impl.CurrentGameStatePacket;
import server.packet.impl.CurrentUsersListPacket;
import server.packet.impl.GameResultPacket;
import server.packet.impl.IllegalMovePacket;
import server.packet.impl.LoginAcknowledgementPacket;
import server.packet.impl.PlayRequestAcknowledgementPacket;
import server.packet.impl.PlayRequestPacket;
import client.packet.ClientPacket;
import client.packet.impl.AcceptRequestPacket;
import client.packet.impl.ChoosePlayerPacket;
import client.packet.impl.DenyRequestPacket;
import client.packet.impl.LoginPacket;
import client.packet.impl.PlayGamePacket;

import common.Packet;
import common.Payload;
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

	private ServerPacket getResponse(ClientPacket packet, DatagramPacket pkt) {
		switch (packet.getPacketType()) {
		case LOGIN:
			return handleLogin((LoginPacket) packet, pkt.getAddress()
					.getHostAddress());
		case QUERY_LIST:
			return new CurrentUsersListPacket(currentUsers);
		case CHOOSE_PLAYER:
			return handlePlayRequest((ChoosePlayerPacket) packet);
		case ACCEPT_REQUEST:
			return handleAcceptRequest((AcceptRequestPacket) packet);
		case DENY_REQUEST:
			return handleDenyRequest((DenyRequestPacket) packet);
		case PLAY_GAME:
			return handlePlay((PlayGamePacket) packet);
		case LOGOUT:
			return null;
		default:
			throw new BadPacketException("Unrecognized packet format");
		}
	}

	private ServerPacket handlePlay(PlayGamePacket packet) {
		User u = currentUsers.get(packet.getUsername());

		// make sure user is playing a game and it's their turn
		if (u == null || u.getCurrentGame() == null
				|| !u.getCurrentGame().isTurn(u)) {
			return new IllegalMovePacket(IllegalMoveType.OUT_OF_TURN);
		}

		// check for illegal move
		if (packet.getCellNumber() < 1 || packet.getCellNumber() > 9) {
			return new IllegalMovePacket(IllegalMoveType.OCCUPIED);
		}

		Game g = u.getCurrentGame();
		try {
			switch (g.play(packet.getCellNumber())) {
			case WIN:
				g.terminate();
				currentGames.remove(g);
				try {
					sendPacket(new GameResultPacket(GameResultType.LOSS),
							g.otherPlayer(u));
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				return new GameResultPacket(GameResultType.WIN);
			case DRAW:
				g.terminate();
				currentGames.remove(g);
				try {
					sendPacket(new GameResultPacket(GameResultType.DRAW),
							g.otherPlayer(u));
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				return new GameResultPacket(GameResultType.DRAW);	
			case IN_PROGRESS:
				try {
					sendPacket(new CurrentGameStatePacket(g),
							g.otherPlayer(u));
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				return null;
			}
		} catch (IllegalMoveException e) {
			return new IllegalMovePacket(IllegalMoveType.OCCUPIED);
		}
		
		return null;

	}

	private ServerPacket handleDenyRequest(DenyRequestPacket packet) {
		User sender = currentUsers.get(packet.getSender());
		User receiver = currentUsers.get(packet.getReciever());

		sender.setState(UserState.FREE);
		receiver.setState(UserState.FREE);

		try {
			sendPacket(
					new PlayRequestAcknowledgementPacket(sender.getUsername(),
							PlayRequestAcknowledgementStatus.DENY), receiver);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ServerPacket handleAcceptRequest(AcceptRequestPacket packet) {
		User sender = currentUsers.get(packet.getSender());
		User receiver = currentUsers.get(packet.getReciever());

		sender.setState(UserState.BUSY);
		receiver.setState(UserState.BUSY);

		try {
			sendPacket(
					new PlayRequestAcknowledgementPacket(sender.getUsername(),
							PlayRequestAcknowledgementStatus.ACCEPTED),
					receiver);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		Game g = new Game(sender, receiver);
		currentGames.add(g);

		try {
			sendPacket(new CurrentGameStatePacket(g), receiver);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ServerPacket handlePlayRequest(ChoosePlayerPacket packet) {
		// check state of sender
		User sender = currentUsers.get(packet.getSender());
		if (sender == null || sender.getState() != UserState.FREE) {
			return new PlayRequestAcknowledgementPacket(sender == null ? ""
					: sender.getUsername(),
					PlayRequestAcknowledgementStatus.FAILURE);
		}

		// sender is free, check state of receiver
		User receiver = currentUsers.get(packet.getReciever());
		if (receiver == null || receiver.getState() != UserState.FREE) {
			return new PlayRequestAcknowledgementPacket(receiver == null ? ""
					: receiver.getUsername(),
					PlayRequestAcknowledgementStatus.FAILURE);
		}

		// receiver is free, set states into decision
		sender.setState(UserState.DECISION);
		receiver.setState(UserState.DECISION);

		// send choose message to client two
		try {
			sendPacket(new PlayRequestPacket(sender.getUsername()), receiver);
		} catch (UnknownHostException e) {
			return new PlayRequestAcknowledgementPacket(receiver.getUsername(),
					PlayRequestAcknowledgementStatus.FAILURE);
		}

		return null;
	}

	/**
	 * 
	 * @param packet
	 * @return
	 */
	private ServerPacket handleLogin(LoginPacket packet, String ip) {
		// try to find a user with the input username
		User u = currentUsers.get(packet.getUsername());

		// register user if no user exists with this username
		if (u == null) {
			u = new User(packet.getUsername(), UserState.OFFLINE, ip,
					packet.getPort());
			currentUsers.put(u.getUsername(), u);
		}

		if (u.getState() == UserState.OFFLINE) {
			// login success for offline/newly registered user
			u.setState(UserState.FREE);
			return new LoginAcknowledgementPacket(
					LoginAcknowledgementType.SUCCESS);

		} else if (u.getState() == UserState.BUSY
				|| u.getState() == UserState.DECISION
				|| u.getState() == UserState.FREE) {
			// login failure for online user
			return new LoginAcknowledgementPacket(
					LoginAcknowledgementType.FAILURE);

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

	public void sendPacket(ServerPacket p, InetAddress addr, int port) {
		byte[] payload = p.toPayload().getBytes();
		DatagramPacket sendPacket;
		sendPacket = new DatagramPacket(payload, payload.length, addr, port);

		try {
			socket.send(sendPacket);
		} catch (IOException e) {
			socket.close();
			e.printStackTrace();
			return;
		}

		System.out.println("[" + Calendar.getInstance().getTimeInMillis()
				+ "] Sent to client: (IP: " + addr.getHostName() + ", Port: "
				+ String.valueOf(port) + "): " + p.toPayload());
	}

	public void respond(DatagramPacket p) throws UnknownHostException {
		Payload payload = new Payload(new String(p.getData(), 0, p.getLength()));
		ClientPacket cp = ClientPacket.fromPayload(payload);

		ack(cp, p);

		ServerPacket response = getResponse(cp, p);
		sendPacket(response, currentUsers.get(cp.getUsername()));
	}

	private void sendPacket(ServerPacket response, User u)
			throws UnknownHostException {
		sendPacket(response, InetAddress.getByName(u.getIp()), u.getPort());
	}

	private void ack(ClientPacket cp, DatagramPacket p) {
		AcknowledgementPacket ack = new AcknowledgementPacket(Packet.nextId());
		sendPacket(ack, p.getAddress(), p.getPort());
	}

}
