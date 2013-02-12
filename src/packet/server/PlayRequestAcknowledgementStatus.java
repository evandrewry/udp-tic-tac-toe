package packet.server;

public enum PlayRequestAcknowledgementStatus {
	ACCEPTED("A"), DENY("D"), FAILURE("F");
	private String code;

	private PlayRequestAcknowledgementStatus(String code) {
		this.code = code;
	}
	
	public String toString() {
		return code;
	}

	public String getCode() {
		return code;
	}
}
