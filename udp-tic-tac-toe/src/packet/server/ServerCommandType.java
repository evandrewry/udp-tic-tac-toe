package packet.server;

public enum ServerCommandType {
	LOGIN_ACK,
	RETURN_LIST,
	PLAY_REQUEST,
	PLAY_REQUEST_OUTCOME,
	ACK,
	GAME_STATE,
	MOVE_NOT_ALLOWED,
}
