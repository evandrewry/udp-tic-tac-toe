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

public class PlayGamePacket extends ClientPacket {
    public long getPacketId() {
        return this.packetId;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public int getCellNumber() {
        return number;
    }

    private final long packetId;
    private final String username;
    private final int number;
    public static final String PACKET_FORMAT = "play,%d,%s,%d";
    public static final Pattern PACKET_PATTERN = Pattern.compile("^play,(\\d+),(\\w+),(\\d+)$");
    public static final String COMMAND = "play";
    public static final Pattern COMMAND_PATTERN = Pattern.compile("^play\\s+(\\d+)$");

    public PlayGamePacket(long packetId, String username, int number) {
        this.packetId = packetId;
        this.username = username;
        this.number = number;
    }

    public PlayGamePacket(String... params) {
        this(Integer.parseInt(params[1]), params[2], Integer.parseInt(params[3]));
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
        return new Object[] { packetId, username, number };
    }

    @Override
    public Pattern getCommandPattern() {
        return COMMAND_PATTERN;
    }

    @Override
    public String getCommand() {
        return COMMAND;
    }

    public static PlayGamePacket fromPayload(Payload payload) {
        Matcher m = PACKET_PATTERN.matcher(payload.content);
        if (m.matches()) {
            int packetId = Integer.parseInt(m.group(1));
            String username = m.group(2);
            int port = Integer.parseInt(m.group(3));
            return new PlayGamePacket(packetId, username, port);
        } else {
            throw new BadPacketException("Could not parse.");
        }
    }

    public static PlayGamePacket fromCommand(Command command, Client handler) {
        Matcher m = COMMAND_PATTERN.matcher(command.content);
        if (m.matches()) {
            long packetId = Packet.nextId();
            String username = handler.getCurrentUser().getUsername();
            int number = Integer.parseInt(m.group(1));
            return new PlayGamePacket(packetId, username, number);
        } else {
            throw new InvalidCommandParametersException("Could not parse.");
        }
    }
}
