package wow.shooter.logic;

/**
 * Created by kopec on 2016-03-22.
 */
import java.net.*;
import java.io.*;

import wow.shooter.managers.*;

public class Client extends Thread {
    private String serverName;
    private int port;
    public Socket client;
    GameManager manager;

    public boolean connected = false;

    public Client(String s, int p, GameManager m){
        serverName = s;
        port = p;
        manager = m;
        connect();

    }

    public void connect(){
        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            client = new Socket(serverName, port);
            System.out.println("Just connected to " + client.getRemoteSocketAddress());

        }catch(IOException e) {
            e.printStackTrace();
        }
        connected = true;
        Thread t = new ClientRecv();
        t.start();
    }

    public byte[] send(byte [] s){
        if(connected) {
            try {
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                out.writeInt(s.length);
                out.write(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s;
        }
        else{
            return s;
        }
    }


    public class ClientRecv extends Thread{
        public void run(){
            byte[] message;
            while(true) {
                try {
                    DataInputStream in = new DataInputStream(client.getInputStream());
                    int length = in.readInt();
                    if(length>0) {
                        message = new byte[length];
                        in.readFully(message, 0, message.length);
                        manager.handleData(message);
                    }

                } catch (IOException e) {
                    connected = false;
                    break;
                }
            }
        }
    }
}
