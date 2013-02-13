package common;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import client.packet.ClientPacket;
import client.packet.impl.AcceptRequestPacket;
import client.packet.impl.ChoosePlayerPacket;
import client.packet.impl.DenyRequestPacket;
import client.packet.impl.LoginPacket;
import client.packet.impl.LogoutPacket;
import client.packet.impl.PlayGamePacket;
import client.packet.impl.QueryListPacket;
import exception.BadPacketException;
import exception.InvalidCommandParametersException;

public class ClientPacketTest {

	@Test(expected = InvalidCommandParametersException.class)
	public void testLoginFail() {
		ClientPacket.fromCommand("login");
	}

	@Test
	public void testParseCommand_ls() {
		ClientPacket p = ClientPacket.fromCommand("ls");
		assertTrue(p.getClass().equals(QueryListPacket.class));
	}

	@Test
	public void testParseCommand_login() {
		Packet p = ClientPacket.fromCommand("login evan");
		assertTrue(p.getClass().equals(LoginPacket.class));
	}

	@Test
	public void testParseCommand_choose() {
		Packet p = ClientPacket.fromCommand("choose poop");
		assertTrue(p.getClass().equals(ChoosePlayerPacket.class));
	}

	@Test
	public void testParseCommand_accept() {
		Packet p = ClientPacket.fromCommand("accept evan");
		assertTrue(p.getClass().equals(AcceptRequestPacket.class));
	}

	@Test
	public void testParseCommand_deny() {
		Packet p = ClientPacket.fromCommand("deny evan");
		assertTrue(p.getClass().equals(DenyRequestPacket.class));
	}

	@Test
	public void testParseCommand_play() {
		Packet p = ClientPacket.fromCommand("play 3");
		assertTrue(p.getClass().equals(PlayGamePacket.class));
	}

	@Test
	public void testParseCommand_logout() {
		Packet p = ClientPacket.fromCommand("deny evan");
		assertTrue(p.getClass().equals(LogoutPacket.class));
	}
	
	@Test(expected = BadPacketException.class)
	public void testFromPayload() {
		Packet p = ClientPacket.fromPayload(new Payload("poop"));
	}

}
