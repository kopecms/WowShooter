package server.managers;

import server.logic.Client;

import java.util.Vector;

/**
 * Created by kopec on 2016-04-14.
 */
public class RoomManager extends Thread{
    private Vector<Client> clients;
    public RoomManager(Vector<Client> c){
        clients = c;
    }
}
