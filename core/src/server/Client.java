package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by kopec on 2016-03-22.
 */
public class Client {
    public Socket socket;

    private int number;
    private Queue recvFromClient = new LinkedList();

    public Client (Socket socket, int number){
        this.socket = socket;
        this.number = number;
    }

    public void send(String s){
        System.out.println(s);
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void offer(String recv){
        recvFromClient.offer(recv);
    }
}
