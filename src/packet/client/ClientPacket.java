package packet.client;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

import packet.Packet;
import exception.InvalidClientCommandException;

public abstract class ClientPacket extends Packet {

    public abstract String getCommand();

    public abstract Pattern getCommandPattern();
    
    public static Packet parseCommand(String inputCommand) throws InvalidClientCommandException {
    	String[] cmd = inputCommand.trim().split(" ");
    	String action = cmd[0];
    	for (ClientCommandType t : ClientCommandType.values()) {
    		try {
				if (action.equals(t.getCommand())) {
						try {
							return (ClientPacket) t.getPacketClass().getConstructor(String.class)
									.newInstance(inputCommand);
						} catch (InstantiationException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
						    if (e.getCause() instanceof RuntimeException) {
						        throw (RuntimeException) e.getCause();
						    }					
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						}
						return null;
				}
			} catch (NoSuchFieldException e) {
				continue;
			}
    	}
        throw new InvalidClientCommandException(action);
    }

    public boolean isCommandMatch(String inputCommand) {
        return getCommandPattern().matcher(inputCommand).matches();
    }
}
