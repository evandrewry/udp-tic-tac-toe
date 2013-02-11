package test;

import static org.junit.Assert.*;

import org.junit.Test;

import packet.client.ClientCommandType;

public class ClientCommandTypeTest {

	@Test
	public void test() {
		System.out.println(ClientCommandType.ACCEPT_REQUEST.getCommand());
		System.out.println(ClientCommandType.CHOOSE_PLAYER.getCommand());
		System.out.println(ClientCommandType.DENY_REQUEST.getCommand());
	}

}
