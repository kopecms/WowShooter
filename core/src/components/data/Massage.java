package components.data;

import com.badlogic.gdx.math.Vector2;
import components.data.enums.DataType;
import components.data.functions.ByteFunctions;

import static components.data.functions.ByteFunctions.stringFromBytes;

/**
 * Created by kopec on 2016-04-14.
 */
public class Massage {
    byte [] bytes;
    public Massage(byte [] b){
       bytes = b;
    }

    public DataType getType(){
        return DataType.fromInt((int) bytes[0]);
    }

    public int getId(){
        return (int) bytes[1];
    }

    public String getName(){
        return stringFromBytes(bytes,2,bytes.length-2);
    }

    public Vector2 getFirstVector(){
        return new Vector2(ByteFunctions.bytesToInt(bytes, 2, 4), ByteFunctions.bytesToInt(bytes, 6, 4));
    }

    public Vector2 getSecondVector(){
        return new Vector2(ByteFunctions.bytesToInt(bytes, 10, 4), ByteFunctions.bytesToInt(bytes, 14, 4));

    }
}
