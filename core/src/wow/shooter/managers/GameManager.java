package wow.shooter.managers;

import components.Box;
import components.agents.Bullet;
import components.agents.Player;
import enums.DataType;
import enums.ObjectType;
import functionsAndStores.DataManager;
import functionsAndStores.ObjectsManager;
import functionsAndStores.fun;
import components.agents.Enemy;
import wow.shooter.main.Client;

/**
 * Created by kopec on 2016-03-22.
 */
public class GameManager extends Thread{
    private Player player = new Player(0,0,0);
    private Client client;

    private DataManager data;

    int mausex;
    int mausey;

    boolean getState = false;

    private State state;
    public GameManager(DataManager data){
        this.data = data;
    }
    public void updateGame(float dt){
        player.move(dt);
        for(Enemy enemy: data.enemies){
            enemy.move(dt);
        }
        for(Bullet bullet: data.bullets){
            bullet.move(dt);
        }
    }


    public void handleData(byte [] recv){
        DataType dataType = DataType.fromInt((int)recv[0]);
        if(dataType == DataType.NUMBER){

        }
        else if(dataType == DataType.POSITION){
            player.position.set(fun.bytesToInt(recv,1,4),fun.bytesToInt(recv,5,4));
            player.destination.set(player.position);
            getState = true;
        }
        else if(dataType == DataType.MOVE){
            for(Enemy e: data.enemies) {
                if(e.getId() == (int)recv[1]) {
                    e.destination.set(fun.bytesToInt(recv, 2, 4), fun.bytesToInt(recv, 6, 4));
                }
            }
        }
        else if(dataType == DataType.OBJECT){
            ObjectType obj = ObjectType.fromInt(recv[1]);
            if(obj == ObjectType.ENEMY){
                // 0 - datatyp , 1 - objecttyp , 2 - id, 3,4,5,6 - posx, 7,8,9,10 - posy...
                Enemy enemy = new Enemy();
                enemy.setId(recv[2]);
                enemy.position.set(fun.bytesToInt(recv,3,4),fun.bytesToInt(recv,7,4));
                data.enemies.addElement(enemy);
            }
            else if(obj == ObjectType.BOX){
                data.boxes.addElement(new Box(recv[2],fun.bytesToInt(recv,3,4),fun.bytesToInt(recv,7,4)));
            }
        }
        else if(dataType == DataType.ID){
            player.setId(recv[1]);
        }
        else if(dataType == DataType.NONE){

        }
    }

    public Player getPlayer(){
        return player;
    }
    public void setClient(Client client){
        this.client = client;
    }

}

