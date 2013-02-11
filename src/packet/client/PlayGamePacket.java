package packet.client;

import java.util.regex.Pattern;

public class PlayGamePacket extends ClientPacket {
    private int packetId;
    private String username;
    private int number;
    public static final String PACKET_FORMAT = "play,%d,%s,%d";
    public static final String COMMAND = "play";
    public static final Pattern COMMAND_PATTERN = Pattern.compile("^play\\s+(\\d+)$");

    public PlayGamePacket(int packetId, String username, int number) {
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

}
