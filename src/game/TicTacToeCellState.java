package game;

import java.util.HashMap;
import java.util.Map;

/**
 * Possible states of a tic tac toe cell
 *
 * @author evan
 *
 */
public enum TicTacToeCellState {
    _(0),
    X(1),
    O(2);
    private int code;


    /**
     * hash map to look up state by code
     */
    private static final Map<Integer, TicTacToeCellState> codeLookup = new HashMap<Integer, TicTacToeCellState>();
    static {
        for (TicTacToeCellState t : values()) {
            codeLookup.put(t.getCode(), t);
        }
    }

    public static TicTacToeCellState fromCode(String code) {
        return codeLookup.get(code);
    }

    TicTacToeCellState(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}
