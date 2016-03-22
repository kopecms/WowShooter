package wow.shooter.managers;

import components.Player;
import enums.DataType;
import enums.ObjectType;
import functions.fun;
import components.Enemy;
import javax.xml.crypto.Data;

/**
 * Created by kopec on 2016-03-22.
 */
public class GameManager {
    private Player player = new Player();

    public ObjectsManager objects = new ObjectsManager();

    private DataType data;
    public GameManager(){

    }
    public void handleData(byte [] recv){
        DataType data = DataType.fromInt((int)recv[0]);
        System.out.println(data);
        if(data == data.NUMBER){

        }
        else if(data == data.POSITION){
            player.setPosition(fun.bytesToInt(recv,1,4),fun.bytesToInt(recv,5,4));
        }
        else if(data == data.OBJECT){
            ObjectType obj = ObjectType.fromInt(recv[1]);
            if(obj == obj.ENEMY){
                // 0 - datatyp , 1 - objecttyp , 2 - id, 3,4,5,6 - posx, 7,8,9,10 - posy...
                Enemy enemy = new Enemy();
                enemy.setId(recv[2]);
                enemy.setPosition(fun.bytesToInt(recv,3,4),fun.bytesToInt(recv,7,4));
                objects.enemies.addElement(enemy);
            }
        }
        else if(data == data.ID){
            player.setId(recv[1]);
        }
        else if(data == data.NONE){

        }
    }
    public Player getPlayer(){
        return player;
    }

}

