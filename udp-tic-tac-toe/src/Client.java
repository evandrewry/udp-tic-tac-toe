import packet.*;
import packet.client.*;


public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Packet x = new LoginPacket(0, "hiiii", 0);
		String y = x.toString();
		System.out.println(y);
		
		
		
	}

}
