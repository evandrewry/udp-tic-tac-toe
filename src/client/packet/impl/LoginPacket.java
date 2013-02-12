package client.packet.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.packet.ClientPacket;
import exception.BadPacketException;
import exception.InvalidCommandParametersException;

public class LoginPacket extends ClientPacket {
    private final int packetId;
    private final String username;
    private final int port;
    public static final String PACKET_FORMAT = "login,%d,%s,%d";
    public static final Pattern PACKET_PATTERN = Pattern.compile("^login,(\\d+),(\\w+),(\\d+)$");
    public static final String COMMAND = "login";
    public static final Pattern COMMAND_PATTERN = Pattern.compile("^login\\s+(\\w+)$");

    public LoginPacket(int packetId, String username, int port) {
        this.packetId = packetId;
        this.username = username;
        this.port = port;
    }

    public LoginPacket(String inputCommand) {
        Matcher m = getCommandPattern().matcher(inputCommand);
        if (!m.matches()) {
            throw new InvalidCommandParametersException(inputCommand);
        }

        //TODO
        packetId = 5;
        username = m.group(1);
        port = 6;
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
        return new Object[] { packetId, username, port };
    }

    @Override
    public Pattern getCommandPattern() {
        return COMMAND_PATTERN;
    }

    @Override
    public String getCommand() {
        return COMMAND;
    }

    @Override
    public LoginPacket fromPayload(String payload) {
        Matcher m = PACKET_PATTERN.matcher(payload);
        if (m.matches()) {
            int packetId = Integer.parseInt(m.group(1));
            String username = m.group(2);
            int port = Integer.parseInt(m.group(3));
            return new LoginPacket(packetId, username, port);
        } else {
            throw new BadPacketException("Could not parse.");
        }
    }
}
