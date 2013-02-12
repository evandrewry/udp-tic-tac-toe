package packet.client;

import java.util.regex.Pattern;

public class ChoosePlayerPacket extends ClientPacket {
    private int packetId;
    private String sender;
    private String reciever;

    public static final String PACKET_FORMAT = "choose,%d,%s,%s";
    public static final String COMMAND = "choose";
    public static final Pattern COMMAND_PATTERN = Pattern.compile("^choose\\s+(\\w*+)$");

    public ChoosePlayerPacket(int packetId, String sender, String reciever) {
        this.packetId = packetId;
        this.sender = sender;
        this.reciever = reciever;
    }

    public ChoosePlayerPacket(String... params) {
        this(Integer.parseInt(params[1]), params[2], params[3]);
    }

    @Override
    public String getPacketFormat() {
        return PACKET_FORMAT;
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

}
