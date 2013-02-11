package packet.client;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

import packet.Packet;
import except.InvalidClientCommandException;

public abstract class ClientPacket extends Packet {

    public abstract String getCommand();

    public abstract Pattern getCommandPattern();

    @SuppressWarnings("unchecked")
    public static Packet parse(String inputCommand) {
        String[] cmd = inputCommand.trim().split(" ");
        String action = cmd[0];
        for (ClientCommandType t : ClientCommandType.values()) {
            if (action.equals(t.getCommand())) {
                if (t.isValidCommand(inputCommand)) {
                    try {
                        return (Packet) t.getPacketClass().getConstructor(String[].class).newInstance((Object[]) cmd);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    return null;
                } else {
                    throw new InvalidClientCommandException(action);
                }
            }
        }
        throw new InvalidClientCommandException(action);
    }

    public boolean isCommandMatch(String inputCommand) {
        return getCommandPattern().matcher(inputCommand).matches();
    }
}
