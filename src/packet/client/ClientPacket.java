package packet.client;

import java.util.regex.Pattern;

public abstract class ClientPacket {
	public abstract String getPacketFormat();
	public abstract Object[] getParameters();
	public abstract String getCommand();
	public abstract Pattern getCommandPattern();

	public static Packet parse(String inputCommand) {
        String[] cmd = inputCommand.trim().split(" ");
        String action = cmd[0];
		for (ClientCommandType t : ClientCommandType.values()) {
            if (action.equals(t.getCommand())) {
                if (t.isValidCommand(inputCommand)) {
                    return t.getPacketClass().getConstructors(
                } else {
                    throw new InvalidClientCommandException();
                }
            }
        }
	}
    
	public boolean isCommandMatch(String inputCommand) {
		return getCommandPattern().matcher(inputCommand).matches();
	}
    
    private ClientPacket() { }
}
