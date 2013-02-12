package common;

import org.junit.Test;

import client.packet.ClientPacket;

import common.Packet;



import exception.InvalidCommandParametersException;

public class ClientPacketTest {

    @Test(expected = InvalidCommandParametersException.class)
    public void testLoginFail() {
        ClientPacket.parseCommand("login");
    }

    @Test
    public void testLoginSuccess() {
        Packet p = ClientPacket.parseCommand("login evan");
        System.out.println(p.toString());
    }

}