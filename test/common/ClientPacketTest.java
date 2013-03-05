package common;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import client.Client;
import client.Command;
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

    private Client client;

    @Before
    public void setUp() throws IOException {
        client = new Client("localhost", 4119);
        client.login("evan", "localhost", 4119);
    }

    @Test(expected = BadPacketException.class)
    public void testFromPayload() {
        ClientPacket.fromPayload(new Payload("poop"));
    }

    @Test
    public void testFromPayload_accept() {
        Packet p = ClientPacket.fromPayload(new Payload("ackchoose,2,matt,john,A"));
        assertTrue(p.getClass().equals(AcceptRequestPacket.class));
    }

    @Test
    public void testFromPayload_choose() {
        Packet p = ClientPacket.fromPayload(new Payload("choose,3,john,matt"));
        assertTrue(p.getClass().equals(ChoosePlayerPacket.class));
    }

    @Test
    public void testFromPayload_deny() {
        Packet p = ClientPacket.fromPayload(new Payload("ackchoose,3,matt,john,D"));
        assertTrue(p.getClass().equals(DenyRequestPacket.class));
    }

    @Test
    public void testFromPayload_login() {
        Packet p = ClientPacket.fromPayload(new Payload("login,1,john,2134"));
        assertTrue(p.getClass().equals(LoginPacket.class));
    }

    @Test
    public void testFromPayload_logout() {
        Packet p = ClientPacket.fromPayload(new Payload("logout,3,john"));
        assertTrue(p.getClass().equals(LogoutPacket.class));
    }

    @Test
    public void testFromPayload_ls() {
        ClientPacket p = ClientPacket.fromPayload(new Payload("list,2,john"));
        assertTrue(p.getClass().equals(QueryListPacket.class));
    }

    @Test
    public void testFromPayload_play() {
        Packet p = ClientPacket.fromPayload(new Payload("play,2,john,9"));
        assertTrue(p.getClass().equals(PlayGamePacket.class));
    }

    @Test(expected = InvalidCommandParametersException.class)
    public void testLoginFail() {
        ClientPacket.fromCommand(new Command("login"), client);
    }

    @Test
    public void testParseCommand_accept() {
        Packet p = ClientPacket.fromCommand(new Command("accept evan"), client);
        assertTrue(p.getClass().equals(AcceptRequestPacket.class));
    }

    @Test
    public void testParseCommand_choose() {
        Packet p = ClientPacket.fromCommand(new Command("choose poop"), client);
        assertTrue(p.getClass().equals(ChoosePlayerPacket.class));
    }

    @Test
    public void testParseCommand_deny() {
        Packet p = ClientPacket.fromCommand(new Command("deny evan"), client);
        assertTrue(p.getClass().equals(DenyRequestPacket.class));
    }

    @Test
    public void testParseCommand_login() {
        Packet p = ClientPacket.fromCommand(new Command("login evan"), client);
        assertTrue(p.getClass().equals(LoginPacket.class));
    }

    @Test
    public void testParseCommand_logout() {
        Packet p = ClientPacket.fromCommand(new Command("logout"), client);
        assertTrue(p.getClass().equals(LogoutPacket.class));
    }

    @Test
    public void testParseCommand_ls() {
        ClientPacket p = ClientPacket.fromCommand(new Command("ls"), client);
        assertTrue(p.getClass().equals(QueryListPacket.class));
    }

    @Test
    public void testParseCommand_play() {
        Packet p = ClientPacket.fromCommand(new Command("play 3"), client);
        assertTrue(p.getClass().equals(PlayGamePacket.class));
    }
}
