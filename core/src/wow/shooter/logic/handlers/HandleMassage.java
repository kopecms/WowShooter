package wow.shooter.logic.handlers;

import com.badlogic.gdx.math.Vector2;
import components.data.Massage;
import components.data.enums.DataType;
import components.data.enums.ObjectType;
import components.data.functions.ByteFunctions;
import components.entities.Box;
import components.entities.Bullet;
import components.entities.Enemy;
import components.data.GameData;
import static components.data.functions.ByteFunctions.stringFromBytes;

/**
 * Created by kopec on 2016-04-14.
 */
public class HandleMassage {
    GameData g = GameData.getInstance();

    public void setPlayerName(Massage massage){
        for(Enemy enemy: g.enemies){
            if(enemy.getId() == massage.getId()) {
                enemy.name = massage.getName();
            }
        }
    }

    public void correctPositionError(Massage massage){
        for(Enemy enemy: g.enemies){
            if(enemy.getId() == massage.getId() ) {
                Vector2 p = massage.getFirstVector();
                if(p.sub(enemy.position).len()>200){
                    enemy.position.set(massage.getFirstVector());
                    enemy.destination.set(massage.getSecondVector());
                }
            }
        }
    }
/*
    else if(dataType == DataType.COLLISION){
        for(Enemy enemy: data.enemies){
            if(enemy.getId() == (int) recv[1] )
            {
                enemy.afterCollision.set(ByteFunctions.bytesToInt(recv, 2, 4), ByteFunctions.bytesToInt(recv, 6, 4));
            }
        }
    }
    else if(dataType == DataType.HIT){
        for(Enemy enemy: data.enemies){
            if(player.id == (int) recv[1]){
                player.setHealth(ByteFunctions.bytesToInt(recv, 2, 4));
            }
            else if(enemy.getId() == (int) recv[1] )
            {
                enemy.setHealth(ByteFunctions.bytesToInt(recv, 2, 4));
            }
        }
    }
    else if(dataType == DataType.SHOOT){
        for(Enemy enemy: data.enemies){
            if(enemy.getId() == (int) recv[1] ) {
                data.bullets.addElement(new Bullet(enemy.position,
                        new Vector2(ByteFunctions.bytesToInt(recv, 2, 4), ByteFunctions.bytesToInt(recv, 6, 4)).setLength(Bullet.speed)));
            }
        }
    }
    else if(dataType == DataType.POSITION){
        player.position.set(ByteFunctions.bytesToInt(recv,1,4), ByteFunctions.bytesToInt(recv,5,4));
        player.destination.set(player.position);
        getState = true;
    }
    else if(dataType == DataType.MOVE){
        for(Enemy e: data.enemies) {
            if(e.getId() == (int)recv[1]) {
                e.destination.set(ByteFunctions.bytesToInt(recv, 2, 4), ByteFunctions.bytesToInt(recv, 6, 4));
            }
        }
    }
    else if(dataType == DataType.OBJECT){
        ObjectType obj = ObjectType.fromInt(recv[1]);
        if(obj == ObjectType.ENEMY){
            // 0 - datatyp , 1 - objecttyp , 2 - id, 3,4,5,6 - posx, 7,8,9,10 - posy...
            Enemy enemy = new Enemy();
            enemy.setId(recv[2]);
            enemy.position.set(ByteFunctions.bytesToInt(recv,3,4), ByteFunctions.bytesToInt(recv,7,4));
            data.enemies.addElement(enemy);
        }
        else if(obj == ObjectType.BOX){
            data.boxes.addElement(new Box(ByteFunctions.bytesToInt(recv,3,4), ByteFunctions.bytesToInt(recv,7,4)));
        }
    }
    else if(dataType == DataType.ID){
        player.setId(recv[1]);
    }
    else if(dataType == DataType.NONE){

    }*/
}
