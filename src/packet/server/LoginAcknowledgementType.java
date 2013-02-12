package packet.server;

public enum LoginAcknowledgementType {
    FAILURE("F"),
    SUCCESS("S");

    private String code;

    LoginAcknowledgementType(String display) {
        this.code = display;
    }

    public String toString() {
        return code;
    }

    public String getCode() {
        return code;
    }
}
