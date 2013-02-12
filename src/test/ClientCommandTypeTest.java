package test;

import org.junit.Test;

import client.packet.ClientCommandType;


public class ClientCommandTypeTest {

    @Test
    public void test() {
        try {
            System.out.println(ClientCommandType.ACCEPT_REQUEST.getCommand());
            System.out.println(ClientCommandType.CHOOSE_PLAYER.getCommand());
            System.out.println(ClientCommandType.DENY_REQUEST.getCommand());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

}
