package packet.server;
public enum LoginAcknowledgementType {
	F("F"),
	S("S");

	private String display;

	LoginAcknowledgementType(String display) {
		this.display = display;
	}

	public String toString() {
		return display;
	}
}
