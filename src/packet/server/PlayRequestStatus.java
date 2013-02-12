package packet.server;

public enum PlayRequestStatus {
	ACCEPTED("A"), DENIED("D"), FAILED("F");
	private String code;

	private PlayRequestStatus(String code) {
		this.code = code;
	}
	
	public String toString() {
		return code;
	}

	public String getCode() {
		return code;
	}
}
