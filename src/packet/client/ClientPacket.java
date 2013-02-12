package packet.client;

import java.util.regex.Pattern;

import packet.Packet;

import exception.InvalidClientCommandException;

public abstract class ClientPacket extends Packet {

    public abstract String getCommand();

    public abstract Pattern getCommandPattern();

    public static ClientPacket parseCommand(String inputCommand) throws InvalidClientCommandException {
        ClientCommandType t = ClientCommandType.fromCommand(inputCommand);
        return t.getInstance(inputCommand);
    }

    public boolean isValidCommmand(String inputCommand) {
        return getCommandPattern().matcher(inputCommand).matches();
    }
}
