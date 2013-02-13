package client.packet;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import common.Payload;

import client.Command;
import client.packet.impl.AcceptRequestPacket;
import client.packet.impl.ChoosePlayerPacket;
import client.packet.impl.DenyRequestPacket;
import client.packet.impl.LoginPacket;
import client.packet.impl.LogoutPacket;
import client.packet.impl.PlayGamePacket;
import client.packet.impl.QueryListPacket;
import exception.BadPacketException;
import exception.InvalidClientCommandException;

public enum ClientPacketType {
	LOGIN(LoginPacket.class), QUERY_LIST(QueryListPacket.class), CHOOSE_PLAYER(
			ChoosePlayerPacket.class), ACCEPT_REQUEST(AcceptRequestPacket.class), DENY_REQUEST(
			DenyRequestPacket.class), PLAY_GAME(PlayGamePacket.class), LOGOUT(
			LogoutPacket.class);

	private Class<? extends ClientPacket> clazz;

	private static final Map<Class<? extends ClientPacket>, ClientPacketType> classLookup = new HashMap<Class<? extends ClientPacket>, ClientPacketType>();
	static {
		for (ClientPacketType t : values()) {
			classLookup.put(t.getPacketClass(), t);
		}
	}

	private static final Map<String, ClientPacketType> commandLookup = new HashMap<String, ClientPacketType>();
	static {
		for (ClientPacketType t : values()) {
			try {
				commandLookup.put(t.getCommand(), t);
			} catch (NoSuchFieldException e) {
				throw new IllegalStateException(e);
			}
		}
	}

	private ClientPacketType(Class<? extends ClientPacket> clazz) {
		this.clazz = clazz;
	}

	private Pattern getPacketPattern() throws NoSuchFieldException {
		try {
			return (Pattern) clazz.getDeclaredField("PACKET_PATTERN").get(null);
		} catch (Exception e) {
			throw new NoSuchFieldException("Could not find packet pattern for "
					+ clazz.getSimpleName());
		}
	}

	public Class<? extends ClientPacket> getPacketClass() {
		return clazz;
	}

	public String getCommand() throws NoSuchFieldException {
		try {
			return (String) clazz.getDeclaredField("COMMAND").get(null);
		} catch (Exception e) {
			throw new NoSuchFieldException("Could not find command "
					+ clazz.getSimpleName());
		}
	}

	public ClientPacket getInstance(String inputCommand) {
		try {
			return (ClientPacket) clazz.getConstructor(String.class)
					.newInstance(inputCommand);
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof RuntimeException) {
				throw (RuntimeException) e.getCause();
			} else {
				e.printStackTrace();
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		throw new IllegalStateException();
	}

	public Pattern getCommandPattern() throws NoSuchFieldException {
		try {
			return (Pattern) clazz.getDeclaredField("COMMAND_PATTERN")
					.get(null);
		} catch (Exception e) {
			throw new NoSuchFieldException(
					"Could not find command pattern for "
							+ clazz.getSimpleName());
		}
	}

	public boolean isValidCommand(String inputCommand) {
		try {
			return getCommandPattern().matcher(inputCommand).matches();
		} catch (NoSuchFieldException e) {
			throw new IllegalStateException();
		}
	}

	public static ClientPacketType fromCommand(Command command)
			throws InvalidClientCommandException {
		String cmd = command.content.trim().split(" ")[0];
		ClientPacketType ret = commandLookup.get(cmd);
		if (ret == null) {
			throw new InvalidClientCommandException(command.content);
		} else {
			return ret;
		}
	}

	public static ClientPacketType fromPayload(Payload payload) {
		for (ClientPacketType t : values()) {
			try {
				if (t.getPacketPattern().matcher(payload.content).matches()) {
					return t;
				}
			} catch (NoSuchFieldException e) {
				throw new IllegalStateException();
			}
		}
		throw new BadPacketException(payload.content);
	}

	public static ClientPacketType fromClass(Class<? extends ClientPacket> clazz) {
		return classLookup.get(clazz);
	}
}
