package wow.shooter.managers;

import com.badlogic.gdx.math.Vector2;
import components.entities.Box;
import components.entities.Bullet;
import components.entities.Player;
import components.enums.DataType;
import components.enums.ObjectType;
import components.funstore.DataStore;
import components.funstore.fun;
import static components.funstore.fun.stringFromBytes;
import components.entities.Enemy;
import wow.shooter.logic.BulletLogic;
import wow.shooter.logic.Client;

import java.util.Timer;
import java.util.TimerTask;

import static components.funstore.DataSetter.*;
/**
 * Created by kopec on 2016-03-22.
 */
public class GameManager extends Thread{
    private Player player = new Player(0,0,0);
    private Client client;

    private DataStore data;

    private BulletLogic bulletLogic;;
    int mausex;
    int mausey;

    // flagi bulletow
    boolean tooFar = true;
    boolean hit = false;

    boolean getState = false;

    Timer t = new Timer();

    private State state;
    // trzeba to ogarnac ladniej
    private int objectsSize = 100;

    public GameManager(DataStore data) {
        this.data = data;

        bulletLogic = new BulletLogic(data, player);
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if( client!= null)
                    client.send(setErrorCorrection(player.id, player.position, player.destination));
            }
        }, 0, 1000);
    }

    public void updateGame(float dt){
        player.move(dt);

        for(Enemy enemy: data.enemies){
            enemy.move(dt);

        }
        // kolizje
        for(Box box: data.boxes) {
            if (player.position.x + objectsSize > box.position.x &&
                    player.position.x < box.position.x + objectsSize &&
                    player.position.y < box.position.y + objectsSize &&
                    player.position.y + objectsSize > box.position.y) {
                Vector2 v = new Vector2(player.position.x + player.velocity.y,
                        player.position.y - player.velocity.x);
                player.destination.set(v);
                client.send(setCollisionData(player.id,v));
            }
            for (Enemy enemy : data.enemies) {
                if (enemy.position.x + objectsSize > box.position.x &&
                        enemy.position.x < box.position.x + objectsSize &&
                        enemy.position.y < box.position.y + objectsSize &&
                        enemy.position.y + objectsSize > box.position.y) {
                    enemy.collisionHandler();
                }
            }
        }

        for(Bullet bullet: data.bullets) {
            bullet.move(dt);
            if(bulletLogic.notNeeded(bullet)){
                break;
            }
            if(bulletLogic.gotHit(bullet)){
                player.setHealth(player.getHealth()-10);
                client.send(setHitData(player.id,player.getHealth()));
                break;
            }
        }

    }


    public void handleData(byte [] recv){
        DataType dataType = DataType.fromInt((int)recv[0]);
        System.out.println(dataType);
        if(dataType == DataType.NAME){
            for(Enemy enemy: data.enemies){
                if(enemy.getId() == (int) recv[1] ) {
                    enemy.name = stringFromBytes(recv,2,recv.length-2);
                }
            }
        }
        else if(dataType == DataType.LAGERRORCORRECTION){
            for(Enemy enemy: data.enemies){
                if(enemy.getId() == (int) recv[1] ) {
                    Vector2 p = new Vector2(fun.bytesToInt(recv, 2, 4),fun.bytesToInt(recv, 6, 4));
                    Vector2 d = new Vector2(fun.bytesToInt(recv, 10, 4),fun.bytesToInt(recv, 14, 4));
                    System.out.println(p);
                    System.out.println(enemy.position);
                    System.out.println(p.sub(enemy.position).len());
                    if(p.sub(enemy.position).len()>200){
                        enemy.position.set(fun.bytesToInt(recv, 2, 4),fun.bytesToInt(recv, 6, 4));
                        enemy.destination.set(fun.bytesToInt(recv, 10, 4),fun.bytesToInt(recv, 14, 4));
                    }
                }
            }
        }
        else if(dataType == DataType.COLLISION){
            for(Enemy enemy: data.enemies){
                if(enemy.getId() == (int) recv[1] )
                {
                    enemy.afterCollision.set(fun.bytesToInt(recv, 2, 4),fun.bytesToInt(recv, 6, 4));
                }
            }
        }
        else if(dataType == DataType.HIT){
            for(Enemy enemy: data.enemies){
                if(player.id == (int) recv[1]){
                    player.setHealth(fun.bytesToInt(recv, 2, 4));
                }
                else if(enemy.getId() == (int) recv[1] )
                {
                    enemy.setHealth(fun.bytesToInt(recv, 2, 4));
                }
            }
        }
        else if(dataType == DataType.SHOOT){
            for(Enemy enemy: data.enemies){
                if(enemy.getId() == (int) recv[1] ) {
                    data.bullets.addElement(new Bullet(enemy.position,
                            new Vector2(fun.bytesToInt(recv, 2, 4), fun.bytesToInt(recv, 6, 4)).setLength(Bullet.speed)));
                }
            }
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
                data.boxes.addElement(new Box(fun.bytesToInt(recv,3,4),fun.bytesToInt(recv,7,4)));
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

    public void close(){
        t.cancel();
    }

}

