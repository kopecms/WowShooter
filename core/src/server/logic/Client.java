package server.logic;

import com.badlogic.gdx.math.Vector2;
import enums.DataType;

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
    public int number;

    private Queue<byte []> recvFromClient = new LinkedList<byte []>();

    public String name = "";
    public Vector2 position = new Vector2(0,0);

    public Client (Socket socket, int number){
        this.socket = socket;
        this.number = number;
    }

    public void close(){
        try {
            socket.close();
        } catch (IOException e){}
    }

    public byte[] poll(){
        return recvFromClient.poll();
    }
    public boolean peek(){
        return recvFromClient.peek() != null;
    }
    public void send(byte [] s){
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeInt(s.length);
            out.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void offer(byte [] recv){
        recvFromClient.offer(recv);
    }


}
