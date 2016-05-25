
        package enums;
        import java.util.HashMap;
        import java.util.Map;

public enum DataType {
    NUMBER(1),
    NAME(2),
    MSG(3),
    OBJECT(4),
    POSITION(5),
    ID(6),
    OUT(7),
    STATE(8),
    KILL(9),
    HIT(10),
    MOVE(11),
    SHOOT(12),
    GETSTATE(13),
    STOP(14),
    COLLISION(15),
    LAGERRORCORRECTION(16),
    NONE(-1);

    private final int id;
    private static final Map<Integer, DataType> intToTypeMap;

    private DataType(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static DataType fromInt(int i) {
        DataType type = (DataType)intToTypeMap.get(Integer.valueOf(i));
        return type == null?NONE:type;
    }

    static {
        intToTypeMap = new HashMap();
        DataType[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            DataType type = var0[var2];
            intToTypeMap.put(Integer.valueOf(type.id), type);
        }

    }
}
