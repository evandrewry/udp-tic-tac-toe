package packet.server;

import packet.Packet;

public class CurrentGameStatePacket extends Packet {
	private String state;
    public static final String PACKET_FORMAT = "play,%s";

    public CurrentGameStatePacket(String state) {
    	this.state = state;
    }
    
	@Override
	public String getPacketFormat() {
		return PACKET_FORMAT;
	}

	@Override
	public Object[] getParameters() {
		return new Object[] { state };
	}

}
