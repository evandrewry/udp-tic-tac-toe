package server.packet;

import java.util.HashMap;
import java.util.Map;

/**
 * types of ackplay packets
 *
 * @author evan
 *
 */
public enum PlayRequestAcknowledgementStatus {
    ACCEPTED("A"),
    DENY("D"),
    FAILURE("F");
    private String code;
    /**
     * hash map to look up status by code
     */
    private static final Map<String, PlayRequestAcknowledgementStatus> codeLookup = new HashMap<String, PlayRequestAcknowledgementStatus>();
    static {
        for (PlayRequestAcknowledgementStatus t : values()) {
            codeLookup.put(t.getCode(), t);
        }
    }

    public static PlayRequestAcknowledgementStatus fromCode(String code) {
        return codeLookup.get(code);
    }

    private PlayRequestAcknowledgementStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }

}
