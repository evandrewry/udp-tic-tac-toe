package common;

import org.junit.Test;

import client.packet.ClientPacketType;

public class ClientCommandTypeTest {

    @Test
    public void test() {
        try {
            System.out.println(ClientPacketType.ACCEPT_REQUEST.getCommand());
            System.out.println(ClientPacketType.CHOOSE_PLAYER.getCommand());
            System.out.println(ClientPacketType.DENY_REQUEST.getCommand());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

}
