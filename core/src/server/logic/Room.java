package server.logic;

import com.badlogic.gdx.math.Vector2;
import components.entities.Player;
import components.data.GameData;
import server.main.WowServer;
import server.managers.GameManager;
import java.util.*;
/**
 * Created by kopec on 2016-03-22.
 */
public class Room extends Thread{
    private WowServer server;
    private Vector<Client> clients = new Vector<Client>();
    private int numberOfPlayers;

    private GameManager manager;
    private GameData data = new GameData();

    public Room(){
        data.setBoxes();
        manager = new GameManager(clients);
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
        }
    }

}
