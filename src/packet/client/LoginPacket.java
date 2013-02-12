package packet.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exception.InvalidCommandParametersException;

public class LoginPacket extends ClientPacket {
    private int packetId;
    private String username;
    private int port;
    public static final String PACKET_FORMAT = "login,%d,%s,%d";
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

}
