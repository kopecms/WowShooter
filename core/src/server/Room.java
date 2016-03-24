package server;

import components.agents.Player;
import functionsAndStores.DataManager;
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

        data.setBoxes();
        manager = new GameManager(clients, data);
    }
    public void setUp(){
       for(Client c: clients){
           data.players.addElement(new Player(c.getNumber(),0,0));
       }
    }

    public void run() {
        setUp();

        while(true){
            manager.handleData();
            manager.handleGame();
        }
    }

}
