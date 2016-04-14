package server.main;

/**
 * Created by kopec on 2016-03-22.
 */

import server.logic.Client;
import server.logic.Room;
import server.managers.RoomManager;

import java.net.*;
import java.io.*;
import java.util.*;


public class WowServer extends Thread{
    private ServerSocket serverSocket;
    private Vector<Client> clients = new Vector<Client>();
    private RoomManager roomManager;
    public WowServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(1000000);

        roomManager = new RoomManager(clients);
        roomManager.start();
    }
    public void run(){
        System.out.println("WowShooter server started");
        while(true){
            try {
                Client client = waitForNewConnection();
                clients.addElement(client);
            } catch(SocketTimeoutException e){

            } catch(IOException e){

            }
        }
    }

    public Client waitForNewConnection() throws SocketTimeoutException,IOException{
        Socket clientSocket = serverSocket.accept();
        Client client = new Client(clientSocket);

        clients.addElement(client);
        System.out.println("Just connected to " +
                clientSocket.getRemoteSocketAddress());
        Thread t = new ClientHandler(client);
        t.start();

        return client;
    }

    public class ClientHandler extends Thread{
        private Client client;
        public ClientHandler(Client client){
            this.client = client;
        }
        public void run(){
            byte [] message;
            while(true){
                try{
                    DataInputStream in = new DataInputStream(client.socket.getInputStream());
                    int length = in.readInt();
                    if(length>0) {
                        message = new byte[length];
                        in.readFully(message, 0, message.length);

                        client.offer(message);
                    }

                }catch(IOException e){

                        e.printStackTrace();
                        break;
                    }
                }
            }

    }


    public static void main(String [] args){
        int port = 5055;
        try{
            Thread t = new WowServer(port);
            t.start();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}