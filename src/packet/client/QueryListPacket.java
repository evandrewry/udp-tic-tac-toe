package packet.client;

import java.util.regex.Pattern;

public class QueryListPacket extends ClientPacket {
    private int packetId;
    private String name;

    public static final String PACKET_FORMAT = "list,%d,%s";
    public static final String COMMAND = "list";
    public static final Pattern COMMAND_PATTERN = Pattern.compile("^ls$");

    QueryListPacket(int packetId, String name) {
        this.packetId = packetId;
        this.name = name;
    }

    @Override
    public String getPacketFormat() {
        return PACKET_FORMAT;
    }

    @Override
    public Pattern getCommandPattern() {
        return COMMAND_PATTERN;
    }

    @Override
    public Object[] getParameters() {
        return new Object[] { packetId, name };
    }

    @Override
    public String getCommand() {
        return COMMAND;
    }

}
