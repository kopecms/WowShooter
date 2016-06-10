package wow.shooter.logic;

/**
 * Created by kopec on 2016-03-22.
 */
import java.net.*;
import java.io.*;

import enums.DataType;
import wow.shooter.managers.*;

public class Client extends Thread {
    private String serverName;
    private int port;
    public Socket client;
    GameManager manager;

    public boolean connected = false;

    public boolean connect(String s, int p, GameManager m){
        serverName = s;
        port = p;
        manager = m;
        try {
            client = new Socket(serverName, port);
        }catch(IOException e) {
            return false;
        }
        connected = true;
        Thread t = new ClientRecv();
        t.start();
        return true;
    }
    public void disconnect(){
        send(new byte []{(byte)DataType.DISCONNECTED.getId()});
        connected = false;
        try {
            if(client != null)
                client.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public byte[] send(byte [] s){
        if(connected) {
            try {
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                out.writeInt(s.length);
                out.write(s);
            } catch (IOException e) {
                    manager.handleConnection();
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
                    manager.handleConnection();
                    break;
                }
            }
        }
    }
}
