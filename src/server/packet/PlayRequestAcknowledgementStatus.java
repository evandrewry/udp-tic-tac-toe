package server.packet;

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

	public static PlayRequestAcknowledgementStatus fromCode(String group) {
		// TODO Auto-generated method stub
		return null;
	}

}
