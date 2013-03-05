package server;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import server.packet.ServerPacket;
import server.packet.impl.AcknowledgementPacket;
import server.packet.impl.CurrentGameStatePacket;
import server.packet.impl.CurrentUsersListPacket;
import server.packet.impl.GameResultPacket;
import server.packet.impl.IllegalMovePacket;
import server.packet.impl.LoginAcknowledgementPacket;
import server.packet.impl.PlayRequestAcknowledgementPacket;
import server.packet.impl.PlayRequestPacket;

import common.Payload;

public class ServerPacketTest {

    @Test
    public void testFromPayload_ack() {
        ServerPacket p = ServerPacket.fromPayload(new Payload("ack,5"));
        assertTrue(p.getClass().equals(AcknowledgementPacket.class));
    }

    @Test
    public void testFromPayload_ackchoose() {
        ServerPacket p = ServerPacket.fromPayload(new Payload("ackchoose,evan,D"));
        assertTrue(p.getClass().equals(PlayRequestAcknowledgementPacket.class));
    }

    @Test
    public void testFromPayload_acklogin() {
        ServerPacket p = ServerPacket.fromPayload(new Payload("acklogin,F"));
        assertTrue(p.getClass().equals(LoginAcknowledgementPacket.class));
    }

    @Test
    public void testFromPayload_ackls() {
        ServerPacket p = ServerPacket.fromPayload(new Payload("ackls,john,D,fred,F,foo,D"));
        assertTrue(p.getClass().equals(CurrentUsersListPacket.class));
    }

    @Test
    public void testFromPayload_ackplay() {
        ServerPacket p = ServerPacket.fromPayload(new Payload("ackplay,O"));
        assertTrue(p.getClass().equals(IllegalMovePacket.class));
    }

    @Test
    public void testFromPayload_play() {
        ServerPacket p = ServerPacket.fromPayload(new Payload("play,012012000"));
        assertTrue(p.getClass().equals(CurrentGameStatePacket.class));
    }

    @Test
    public void testFromPayload_request() {
        ServerPacket p = ServerPacket.fromPayload(new Payload("request,evan"));
        assertTrue(p.getClass().equals(PlayRequestPacket.class));
    }

    @Test
    public void testFromPayload_result() {
        ServerPacket p = ServerPacket.fromPayload(new Payload("result,W"));
        assertTrue(p.getClass().equals(GameResultPacket.class));
    }
}
