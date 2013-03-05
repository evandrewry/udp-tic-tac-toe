package server.packet;

import java.util.HashMap;
import java.util.Map;

/**
 * Types of illegal moves
 *
 * @author evan
 *
 */
public enum IllegalMoveType {
    OCCUPIED("O"),
    OUT_OF_TURN("T");

    private String code;

    /**
     * hash map to look up type by code
     */
    private static final Map<String, IllegalMoveType> codeLookup = new HashMap<String, IllegalMoveType>();
    static {
        for (IllegalMoveType t : values()) {
            codeLookup.put(t.getCode(), t);
        }
    }

    public static IllegalMoveType fromCode(String code) {
        return codeLookup.get(code);
    }

    private IllegalMoveType(String code) {
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
