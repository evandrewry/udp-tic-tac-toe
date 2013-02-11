package test;

import org.junit.Test;

import packet.Packet;
import packet.client.ClientPacket;
import packet.client.LoginPacket;

public class LoginPacketTest {

    @Test
    public void test() {
        Packet x = new LoginPacket(0, "ehi", 9);
        ClientPacket.parse("loginhi");
    }

}
