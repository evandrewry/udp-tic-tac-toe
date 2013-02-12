package packet.server;

import packet.Packet;

public class CurrentGameStatePacket extends Packet {
	
    public static final String PACKET_FORMAT = "play,%s";

	@Override
	public String getPacketFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

}
