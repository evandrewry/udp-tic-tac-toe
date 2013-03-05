package game;

import java.util.HashMap;
import java.util.Map;

public enum GameResultType {
    WIN("W"),
    LOSS("L"),
    DRAW("D"),
    IN_PROGRESS("IP");

    private String code;
    private static final Map<String, GameResultType> codeLookup = new HashMap<String, GameResultType>();
    static {
        for (GameResultType t : values()) {
            codeLookup.put(t.getCode(), t);
        }
    }

    private GameResultType(String code) {
        this.code = code;
    }

    public String toString() {
        return code;
    }

    public String getCode() {
        return code;
    }

    public static GameResultType fromCode(String code) {
        return codeLookup.get(code);
    }
}
