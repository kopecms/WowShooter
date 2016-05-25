
        package enums;

        import java.util.HashMap;
        import java.util.Map;

public enum ObjectType {
    ENEMY(1),
    BULLET(2),
    BOX(4),
    NONE(-1);

    private final int id;
    private static final Map<Integer, ObjectType> intToTypeMap;

    private ObjectType(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static ObjectType fromInt(int i) {
        ObjectType type = (ObjectType)intToTypeMap.get(Integer.valueOf(i));
        return type == null?NONE:type;
    }

    static {
        intToTypeMap = new HashMap();
        ObjectType[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            ObjectType type = var0[var2];
            intToTypeMap.put(Integer.valueOf(type.id), type);
        }

    }
}
