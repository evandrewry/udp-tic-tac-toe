package test;

import static org.junit.Assert.*;

import org.junit.Test;

import packet.Packet;
import packet.client.LoginPacket;

public class LoginPacketTest {

	@Test
	public void test() {
		Packet x = new LoginPacket(0, "ehi", 9);
		Packet.parse("loginhi");
	}

}
