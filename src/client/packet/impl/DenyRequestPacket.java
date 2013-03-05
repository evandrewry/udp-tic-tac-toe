package client.packet.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.Client;
import client.Command;
import client.packet.ClientPacket;

import common.Packet;
import common.Payload;

import exception.BadPacketException;
import exception.InvalidCommandParametersException;

public class DenyRequestPacket extends ClientPacket {
    public long getPacketId() {
        return this.packetId;
    }

    @Override
    public String getUsername() {
        return this.sender;
    }

    public String getSender() {
        return this.sender;
    }

    public String getReciever() {
        return this.reciever;
    }

    private final long packetId;
    private final String sender;
    private final String reciever;

    public static final String PACKET_FORMAT = "ackchoose,%d,%s,%s,D";
    public static final Pattern PACKET_PATTERN = Pattern.compile("^ackchoose,(\\d+),(\\w+),(\\w+),D$");
    public static final String COMMAND = "deny";
    public static final Pattern COMMAND_PATTERN = Pattern.compile("^deny\\s+(\\w+)$");
    public static final String CODE = "ackchoose";

    public DenyRequestPacket(long packetId, String sender, String reciever) {
        this.packetId = packetId;
        this.sender = sender;
        this.reciever = reciever;
    }

    public DenyRequestPacket(String... params) {
        this(Integer.parseInt(params[1]), params[2], params[3]);
    }

    @Override
    public String getPacketFormat() {
        return PACKET_FORMAT;
    }

    @Override
    public Pattern getPacketPattern() {
        return PACKET_PATTERN;
    }

    @Override
    public Object[] getParameters() {
        return new Object[] { packetId, sender, reciever };
    }

    @Override
    public Pattern getCommandPattern() {
        return COMMAND_PATTERN;
    }

    @Override
    public String getCommand() {
        return COMMAND;
    }

    public static DenyRequestPacket fromPayload(Payload payload) {
        Matcher m = PACKET_PATTERN.matcher(payload.content);
        if (m.matches()) {
            int packetId = Integer.parseInt(m.group(1));
            String sender = m.group(2);
            String reciever = m.group(3);
            return new DenyRequestPacket(packetId, sender, reciever);
        } else {
            throw new BadPacketException("Could not parse.");
        }
    }

    public static DenyRequestPacket fromCommand(Command command, Client handler) {
        Matcher m = COMMAND_PATTERN.matcher(command.content);
        if (m.matches()) {
            long packetId = Packet.nextId();
            String sender = handler.getCurrentUser().getUsername();
            String reciever = m.group(1);
            return new DenyRequestPacket(packetId, sender, reciever);
        } else {
            throw new InvalidCommandParametersException("Could not parse.");
        }
    }
}
