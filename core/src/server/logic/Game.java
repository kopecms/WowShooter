package server.logic;

import server.managers.GameManager;

import java.util.ConcurrentModificationException;
import java.util.Vector;

/**
 * Created by kopec on 2016-03-22.
 */
public class Game extends Thread{
    private Vector<Client> clients = new Vector<Client>();
    private GameManager manager = new GameManager(clients);

    public Game(){
    }
    public void addClient(Client client){
        clients.addElement(client);
    }
    public void removeClient(Client client){
        clients.remove(client);
    }

    public void run() {
        while(true){
            try {
                manager.handleData();
            } catch (ConcurrentModificationException e) {}
        }
    }

}
