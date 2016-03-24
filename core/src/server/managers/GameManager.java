package server.managers;

/**
 * Created by kopec on 2016-03-22.
 */


import components.Box;
import components.agents.Player;
import enums.DataType;
import functionsAndStores.DataManager;
import server.Client;

import java.util.Arrays;

import static functionsAndStores.Data.*;

public class GameManager {
    private Client [] clients;

    public DataManager dataStore;

    public GameManager(Client [] clients, DataManager dataStore){
        this.clients = clients;
        this.dataStore = dataStore;
    }


    public void handleGame(){

    }

    public void handleData() {
        for(Client client: clients){
            if(client.peek()){
                byte [] recv = client.poll();
                DataType data = DataType.fromInt((int)recv[0]);
                System.out.println(data);
                if( data == DataType.NAME){

                }
                else if( data == DataType.SHOOT){

                }
                else if( data == DataType.MSG){

                }
                else if( data == DataType.MOVE){
                    for(Client c: clients) {
                        if(c != client) {

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
