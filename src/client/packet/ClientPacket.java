package client.packet;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

import client.Client;
import client.Command;

import common.Packet;
import common.Payload;

import exception.BadPacketException;

public abstract class ClientPacket extends Packet {

	public abstract String getCommand();

	public abstract Pattern getCommandPattern();

	public boolean isValidCommmand(String inputCommand) {
		return getCommandPattern().matcher(inputCommand).matches();
	}

	public ClientPacketType getPacketType() {
		return ClientPacketType.fromClass(getClass());
	}

	public static ClientPacket fromCommand(Command command, Client handler) {
		return fromCommand(command, ClientPacketType.fromCommand(command), handler);
	}

	public static ClientPacket fromCommand(Command command,
			ClientPacketType type, Client handler) {
		try {
			return (ClientPacket) type.getPacketClass()
					.getDeclaredMethod("fromCommand", Command.class, Client.class)
					.invoke(null, command, handler);
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof RuntimeException) {
				throw (RuntimeException) e.getCause();
			} else {
				e.printStackTrace();
			}
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

	public static ClientPacket fromPayload(Payload payload) {
		for (ClientPacketType t : ClientPacketType.values()) {
			try {
				return fromPayload(payload, t);
			} catch (BadPacketException e) {
				continue;
			}
		}
		throw new BadPacketException("Could not parse packet.");
	}

	public static ClientPacket fromPayload(Payload payload,
			ClientPacketType type) {
		try {
			return (ClientPacket) type.getPacketClass()
					.getDeclaredMethod("fromPayload", Payload.class)
					.invoke(null, payload);
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof RuntimeException) {
				throw (RuntimeException) e.getCause();
			} else {
				e.printStackTrace();
			}
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
}
