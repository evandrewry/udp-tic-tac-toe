package common;

import org.junit.Test;

import client.packet.ClientPacket;
import client.packet.impl.LoginPacket;

import common.Packet;


public class LoginPacketTest {

    @Test
    public void test() {
        Packet x = new LoginPacket(0, "ehi", 9);
        ClientPacket.parseCommand("loginhi");
    }

}
