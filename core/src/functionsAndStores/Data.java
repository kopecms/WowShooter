package functionsAndStores;

import com.badlogic.gdx.math.Vector2;
import components.Box;
import enums.DataType;
import enums.ObjectType;

import java.nio.ByteBuffer;

/**
 * Created by kopec on 2016-03-24.
 */
public class Data {
    public static byte [] setBulletData(Vector2 velocity)
    {
        byte [] dataType = { (byte) DataType.SHOOT.getId() };
        byte [] posX = ByteBuffer.allocate(4).putInt((int)velocity.x).array();
        byte [] posY = ByteBuffer.allocate(4).putInt((int)velocity.y).array();
        byte [] velocityBytes = fun.concat(posX,posY);
        byte [] dataToSend = fun.concat(dataType,velocityBytes);
        return dataToSend;
    }
    public static byte [] setBulletData(int id, byte [] velocity)
    {
        byte [] dataType = { (byte) DataType.SHOOT.getId(), (byte) id};
        byte [] dataToSend = fun.concat(dataType,velocity);
        return dataToSend;
    }
    public static byte [] setDestinationData(float x, float y){
        byte [] dataType = { (byte) DataType.MOVE.getId() };
        byte [] posX = ByteBuffer.allocate(4).putInt((int)x).array();
        byte [] posY = ByteBuffer.allocate(4).putInt((int)y).array();
        byte [] destination = fun.concat(posX,posY);
        byte [] dataToSend = fun.concat(dataType,destination);
        return dataToSend;
    }
    public static byte [] setDestinationData(int id, byte [] destination){
        byte [] dataType = { (byte) DataType.MOVE.getId(),(byte) id};
        byte [] dataToSend = fun.concat(dataType,destination);
        return dataToSend;
    }

    public static byte [] setIdData(int id){
        byte [] dataToSend = { (byte) DataType.ID.getId(), (byte) id};
        return dataToSend;
    }
    public static byte [] setEnemyData(int id, int x, int y){
        byte [] dataType = { (byte) DataType.OBJECT.getId(), (byte) ObjectType.ENEMY.getId(), (byte) id};
        byte [] posX = ByteBuffer.allocate(4).putInt(x).array();
        byte [] posY = ByteBuffer.allocate(4).putInt(y).array();
        byte [] position = fun.concat(posX,posY);
        byte [] dataToSend = fun.concat(dataType,position);
        return dataToSend;
    }
    public static byte [] setBoxData(Box box){
        byte [] dataType = { (byte) DataType.OBJECT.getId(), (byte) ObjectType.BOX.getId(), (byte) box.id};
        byte [] posX = ByteBuffer.allocate(4).putInt((int)box.position.x).array();
        byte [] posY = ByteBuffer.allocate(4).putInt((int)box.position.y).array();
        byte [] position = fun.concat(posX,posY);
        byte [] dataToSend = fun.concat(dataType,position);
        return dataToSend;
    }
    public static byte [] positionData(int x, int y){
        byte [] dataType = { (byte) DataType.POSITION.getId() };
        byte [] posX = ByteBuffer.allocate(4).putInt(x).array();
        byte [] posY = ByteBuffer.allocate(4).putInt(y).array();
        byte [] position = fun.concat(posX,posY);
        byte [] dataToSend = fun.concat(dataType,position);
        return dataToSend;
    }

    public static byte [] setNameData(int id, String name){
        byte [] dataType = { (byte) DataType.NAME.getId(), (byte) id};
        byte [] dataToSend = fun.concat(dataType,name.getBytes());
        return dataToSend;
    }

    public static byte [] playerOut(int id){
        byte [] dataToSend = { (byte) DataType.OUT.getId(), (byte) id};
        return dataToSend;
    }

    public static byte [] createMsg(int id, byte [] msg){
        byte [] dataType = { (byte) DataType.MSG.getId(), (byte) id};
        byte [] dataToSend = fun.concat(dataType,msg);
        return dataToSend;
    }
}
