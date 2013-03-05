package server.packet;

import java.util.HashMap;
import java.util.Map;

/**
 * Types of acklogin packets
 *
 * @author evan
 *
 */
public enum LoginAcknowledgementType {
    FAILURE("F"),
    SUCCESS("S");

    private String code;
    /**
     * hash map to look up type by code
     */
    private static final Map<String, LoginAcknowledgementType> codeLookup = new HashMap<String, LoginAcknowledgementType>();
    static {
        for (LoginAcknowledgementType t : values()) {
            codeLookup.put(t.getCode(), t);
        }
    }

    public static LoginAcknowledgementType fromCode(String code) {
        return codeLookup.get(code);
    }

    LoginAcknowledgementType(String display) {
        this.code = display;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }
}
