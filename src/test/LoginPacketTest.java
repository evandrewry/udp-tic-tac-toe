package test;

import org.junit.Test;

import packet.client.ClientPacket;
import packet.client.LoginPacket;

import common.Packet;

public class LoginPacketTest {

    @Test
    public void test() {
        Packet x = new LoginPacket(0, "ehi", 9);
        ClientPacket.parseCommand("loginhi");
    }

}
