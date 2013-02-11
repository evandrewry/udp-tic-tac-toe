package test;

import org.junit.Test;

import packet.Packet;
import packet.client.ClientPacket;
import except.InvalidClientCommandException;

public class ClientPacketTest {

    @Test(expected = InvalidClientCommandException.class)
    public void testLoginFail() {
        ClientPacket.parseCommand("login");
    }

    @Test
    public void testLoginSuccess() {
        Packet p = ClientPacket.parseCommand("login evan");
        System.out.println(p.toString());
    }

}
