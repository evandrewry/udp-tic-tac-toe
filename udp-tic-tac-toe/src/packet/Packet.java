package packet;

import java.util.regex.Pattern;

import packet.client.LoginPacket;

public abstract class Packet {
	public abstract String getPacketFormat();
	public abstract Object[] getParameters();
	public abstract String getCommand();
	public abstract Pattern getCommandPattern();
	
	public String render() {
		return this.toString();
	}
	public String toString() {
		return String.format(getPacketFormat(), getParameters());
	}
		
	public static Packet parse(String inputCommand) {
		return null;//TODO
	}
	
	public boolean isCommandMatch(String inputCommand) {
		return getCommandPattern().matcher(inputCommand).matches();
	}
	
}
