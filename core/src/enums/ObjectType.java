package enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kopec on 2016-03-22.
 */
public enum ObjectType {
    ENEMY(1),
    BULLET(2),
    BLOCK(3),

    NONE(-1);

    private final int id;

    private ObjectType(int id){
        this.id = id;
    }

    private static final Map<Integer, ObjectType> intToTypeMap = new HashMap<Integer, ObjectType>();

    static {
        for (ObjectType type : ObjectType.values()) {
            intToTypeMap.put(type.id, type);
        }
    }

    public int getId(){
        return this.id;
    }

    public static ObjectType fromInt(int i) {
        ObjectType type = intToTypeMap.get(Integer.valueOf(i));
        if (type == null)
            return ObjectType.NONE;
        return type;
    }
}
