package server;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by kopec on 2016-03-22.
 */
public class Room extends Thread{
    private WowServer server;
    private Client [] clients;
    private int numberOfPlayers;

    public Room(WowServer server, Client [] clients, int numberOfPlayers){
        this.server = server;
        this.numberOfPlayers = numberOfPlayers;
        this.clients = clients;
    }

    public void run() {

        for(int i=0; i< numberOfPlayers; i++){
            System.out.println("?");
            clients[i].send("HEEEJ");
        }
    }

}
