package server;

import components.Player;
import enums.DataType;
import enums.ObjectType;
import functions.fun;

import java.nio.ByteBuffer;
import java.util.Vector;

/**
 * Created by kopec on 2016-03-22.
 */
public class DataManager {
    public Vector<Player> players = new Vector<Player>();

    public byte [] setIdData(int id){
        byte [] dataToSend = { (byte) DataType.ID.getId(), (byte) id};
        return dataToSend;
    }
    public byte [] setEnemyData(int id, int x, int y){
        byte [] dataType = { (byte) DataType.OBJECT.getId(), (byte) ObjectType.ENEMY.getId(), (byte) id};
        byte [] posX = ByteBuffer.allocate(4).putInt(x).array();
        byte [] posY = ByteBuffer.allocate(4).putInt(y).array();
        byte [] position = fun.concat(posX,posY);
        byte [] dataToSend = fun.concat(dataType,position);
        return dataToSend;
    }

    public byte [] setPositionData(int x, int y){
        byte [] dataType = { (byte) DataType.POSITION.getId() };
        byte [] posX = ByteBuffer.allocate(4).putInt(x).array();
        byte [] posY = ByteBuffer.allocate(4).putInt(y).array();
        byte [] position = fun.concat(posX,posY);
        byte [] dataToSend = fun.concat(dataType,position);
        return dataToSend;
    }
}
