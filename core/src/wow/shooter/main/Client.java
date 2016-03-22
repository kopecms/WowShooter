package wow.shooter.main;

/**
 * Created by kopec on 2016-03-22.
 */
import java.net.*;
import java.io.*;

import wow.shooter.managers.*;

public class Client extends Thread {
    private String serverName;
    private int port;
    Socket client;
    GameManager manager;
    //gameManager

    public Client(String s,int p, GameManager m){
        serverName = s;
        port = p;
        manager = m;
        // Nawiazanie polaczenia
        connect();
        // Watek odbierajacy dane od serwera i zapisujacy je w recv
        Thread t = new ClientRecv();

        t.start();
    }
    // laczenie z serwerem
    public void connect(){
        try
        {
            System.out.println("Connecting to " + serverName +
                    " on port " + port);
            client = new Socket(serverName, port);
            System.out.println("Just connected to "
                    + client.getRemoteSocketAddress());
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String s){
        try {
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF(s);
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public class ClientRecv extends Thread{
        public void run(){
            String r;
            while(true) {
                try {
                    DataInputStream in = new DataInputStream(client.getInputStream());
                    r = in.readUTF();
                    System.out.println(r);
                    manager.handleData(r.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}
