
        package enums;

        import java.util.HashMap;
        import java.util.Map;

public enum State {
    BEGIN(1),
    PLAY(2),
    ERROR(3),
    WAIT(4),
    END(5),
    RESTART(6),
    NONE(-1);

    private final int id;
    private static final Map<Integer, State> intToTypeMap;

    private State(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static State fromInt(int i) {
        State type = (State)intToTypeMap.get(Integer.valueOf(i));
        return type == null?NONE:type;
    }

    static {
        intToTypeMap = new HashMap();
        State[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            State type = var0[var2];
            intToTypeMap.put(Integer.valueOf(type.id), type);
        }

    }
}
