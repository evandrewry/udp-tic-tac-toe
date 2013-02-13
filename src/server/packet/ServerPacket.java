package server.packet;

import java.lang.reflect.InvocationTargetException;

import common.Packet;
import common.Payload;

import exception.BadPacketException;

public abstract class ServerPacket extends Packet {
	public static Packet fromPayload(Payload payload) {
		for (ServerPacketType t : ServerPacketType.values()) {
			try {
				return fromPayload(payload, t);
			} catch (BadPacketException e) {
				continue;
			}
		}
		throw new BadPacketException("Could not parse packet.");
	}

	public static ServerPacket fromPayload(Payload payload,
			ServerPacketType type) {
		try {
			return (ServerPacket) type.getPacketClass()
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
