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
                else if( data == DataType.LAGERRORCORRECTION ){
                    for(Client c: clients){
                        if(c != client) {
                            c.send(recv);
                        }
                    }
                }
                else if( data == DataType.COLLISION){
                    for(Client c: clients){
                        if(c != client) {
                            c.send(recv);
                        }
                    }
                }
                else if( data == DataType.HIT){
                    for(Client c: clients){
                        if(c != client) {
                            c.send(recv);
                        }
                    }
                }
                else if( data == DataType.SHOOT){
                    for(Client c: clients){
                        if(c != client){
                            c.send(setBulletData(client.getNumber(),Arrays.copyOfRange(recv, 1, recv.length)));
                        }
                    }
                }
                else if( data == DataType.MSG){

                }
                else if( data == DataType.MOVE){
                    for(Client c: clients) {
                        if(c != client) {
                            // USTAWIC POZYCJE PLAYEROW
                            c.send(setDestinationData(client.getNumber(), Arrays.copyOfRange(recv, 1, recv.length)));
                        }
                    }
                }
                else if( data == DataType.GETSTATE){
                    // ustawienia poczatkowe
                    client.send(setIdData(client.getNumber()));
                    client.send(positionData((int)dataStore.players.elementAt(client.getNumber()).position.x,
                            (int)dataStore.players.elementAt(client.getNumber()).position.y));

                    for(Player p: dataStore.players) {
                        if(p.getId() != client.getNumber()){
                            client.send(setHitData(p.id,p.getHealth()));
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
