package common;

public enum UserState {
    FREE("A"),
    BUSY("B"),
    DECISION("D");

    private final String code;

    UserState(String code) {
        this.code = code;
    }

    public String toString() {
        return this.code;
    }

    public String getCode() {
        return this.code;
    }
}
