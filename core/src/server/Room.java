package server;

import server.managers.DataManager;
import server.managers.GameManager;

/**
 * Created by kopec on 2016-03-22.
 */
public class Room extends Thread{
    private WowServer server;
    private Client [] clients;
    private int numberOfPlayers;

    private GameManager manager;
    private DataManager data = new DataManager();
    public Room(WowServer server, Client [] clients, int numberOfPlayers){
        this.server = server;
        this.numberOfPlayers = numberOfPlayers;
        this.clients = clients;

        this.manager = new GameManager(clients);
    }
    public void setUp(){
       // for(int i=0; i< numberOfPlayers; i++){
        clients[0].send(data.setIdData(0));

        clients[0].send(data.positionData(400,200));
        clients[0].send(data.setEnemyData(0,98,50));


        //}
    }

    public void run() {
        setUp();

        while(true){
            manager.handleData();
            manager.handleGame();
        }
    }

}
