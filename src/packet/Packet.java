package packet;

import java.util.regex.Pattern;

public abstract class Packet {
	public abstract String getPacketFormat();
	public abstract Object[] getParameters();
	
	public String render() {
		return this.toString();
	}
	public String toString() {
		return String.format(getPacketFormat(), getParameters());
	}
}
