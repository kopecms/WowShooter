package components.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kopec on 2016-03-22.
 */
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

        private DataType(int id){
            this.id = id;
        }

        private static final Map<Integer, DataType> intToTypeMap = new HashMap<Integer, DataType>();

        static {
            for (DataType type : DataType.values()) {
                intToTypeMap.put(type.id, type);
            }
        }

        public int getId(){
            return this.id;
        }

        public static DataType fromInt(int i) {
            DataType type = intToTypeMap.get(Integer.valueOf(i));
            if (type == null)
                return DataType.NONE;
            return type;
        }
}
