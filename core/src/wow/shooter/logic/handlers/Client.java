package wow.shooter.logic.handlers;

/**
 * Created by kopec on 2016-03-22.
 */
import java.net.*;
import java.io.*;

import components.data.Massage;
import wow.shooter.managers.*;

public class Client extends Thread {
    private String serverName;
    private int port;
    public Socket client;
    GameManager manager;

    public void create(String s,int p, GameManager m){
        serverName = s;
        port = p;
        manager = m;

        connect();

        // Watek odbierajacy dane od serwera i zapisujacy je w recv
        Thread t = new ClientRecv();

        t.start();
    }

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

    public void send(byte [] s){
        try {
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeInt(s.length);
            out.write(s);
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public class ClientRecv extends Thread{
        public void run(){
            byte[] bytes;
            while(true) {
                try {
                    DataInputStream in = new DataInputStream(client.getInputStream());
                    int length = in.readInt();
                    if(length>0) {
                        bytes = new byte[length];
                        in.readFully(bytes, 0, bytes.length); // read the message
                        manager.handleData(new Massage(bytes));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}
