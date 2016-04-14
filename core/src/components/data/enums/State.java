package components.data.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kopec on 2016-03-23.
 */
public enum State {
    BEGIN(1),
    PLAY(2),
    ERROR(3),
    WAIT(4),
    END(5),
    RESTART(6),

    NONE(-1);

    private final int id;

    private State(int id){
        this.id = id;
    }

    private static final Map<Integer, State> intToTypeMap = new HashMap<Integer, State>();

    static {
        for (State type : State.values()) {
            intToTypeMap.put(type.id, type);
        }
    }

    public int getId(){
        return this.id;
    }

    public static State fromInt(int i) {
        State type = intToTypeMap.get(Integer.valueOf(i));
        if (type == null)
            return State.NONE;
        return type;
    }
}
