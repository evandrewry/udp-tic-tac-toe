package game;

import java.util.HashMap;
import java.util.Map;

/**
 * Possible results of a turn in a tic tac toe game
 *
 * @author evan
 *
 */
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

    public static GameResultType fromCode(String code) {
        return codeLookup.get(code);
    }

    private GameResultType(String code) {
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
