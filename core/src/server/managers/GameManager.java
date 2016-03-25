package server.managers;

/**
 * Created by kopec on 2016-03-22.
 */


import com.badlogic.gdx.math.Vector2;
import components.entities.Box;
import components.entities.Bullet;
import components.entities.Player;
import components.enums.DataType;
import components.funstore.DataStore;
import server.logic.Client;

import java.util.Arrays;

import static components.funstore.DataSetter.*;
import static components.funstore.fun.*;

public class GameManager {
    private Client [] clients;

    public DataStore dataStore;

    public GameManager(Client [] clients, DataStore dataStore){
        this.clients = clients;
        this.dataStore = dataStore;
    }


    public void handleGame(float dt){
        for(Player player: dataStore.players){
            player.move(dt);


        }
        try {
            for (Bullet bullet : dataStore.bullets) {
                bullet.move(dt);

                // usuwanie bulletow i sprawdzanie czy ktos trafiony
                boolean tooFar = true;
                for (Player player : dataStore.players) {
                    if (bullet.dist(player.position) < 50) {
                        player.setHealth(player.getHealth()-10);
                        for (Client client : clients) {
                            client.send(setHitData(player.id,player.getHealth()));
                        }
                        dataStore.bullets.remove(bullet);
                        break;
                    }
                    if (bullet.dist(player.position) < 500) {
                        tooFar = false;
                    }
                }
                if (tooFar) {
                    dataStore.bullets.remove(bullet);
                    break;
                }
            }
        } catch (java.util.ConcurrentModificationException e){

        }
    }

    public void handleData() {
        for(Client client: clients){
            if(client.peek()){
                byte [] recv = client.poll();
                DataType data = DataType.fromInt((int)recv[0]);

                if( data == DataType.NAME){
                    for(Client c: clients){
                        if(c != client){
                            c.send(setNameData(client.getNumber(),Arrays.copyOfRange(recv, 1, recv.length)));
                        }
                    }
                }
                else if( data == DataType.SHOOT){
                    dataStore.bullets.addElement(new Bullet(dataStore.players.elementAt(client.getNumber()).position,
                            new Vector2(bytesToInt(recv,1,4),bytesToInt(recv,5,4))));
                    for(Client c: clients){
                        if(c != client){
                            c.send(setBulletData(client.getNumber(),Arrays.copyOfRange(recv, 1, recv.length)));
                        }
                    }
                }
                else if( data == DataType.MSG){

                }
                else if( data == DataType.MOVE){
                    dataStore.players.elementAt(client.getNumber()).destination.set(
                            bytesToInt(recv,1,4),bytesToInt(recv,5,4));
                    for(Client c: clients) {
                        if(c != client) {
                            // USTAWIC POZYCJE PLAYEROW
                            c.send(setDestinationData(client.getNumber(), Arrays.copyOfRange(recv, 1, recv.length)));
                        }
                    }
                }
                else if( data == DataType.GETSTATE){
                    client.send(setIdData(client.getNumber()));

                    client.send(positionData((int)dataStore.players.elementAt(client.getNumber()).position.x,
                            (int)dataStore.players.elementAt(client.getNumber()).position.y));

                    for(Player p: dataStore.players) {
                        if(p.getId() != client.getNumber()){
                            client.send(setEnemyData(p.getId(),(int)p.position.x,
                                    (int)p.position.y));
                        }
                    }

                    for(Box box: dataStore.boxes) {
                        client.send(setBoxData(box));
                    }
                }


            }
        }
    }
}
