package server;

/**
 * Created by kopec on 2016-03-22.
 */

import server.logic.Client;
import server.logic.Room;

import java.net.*;
import java.io.*;
import java.util.*;


public class WowServer extends Thread{
    private ServerSocket serverSocket;
    private Vector<Client> clients = new Vector<Client>();

    public WowServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(1000000);
    }
    public void run(){
        prepareRoom(2);
    }
    public void prepareRoom(int numberOfPlayers){
        int num = 0;
        Client [] clientsInRoom = new Client[numberOfPlayers];

        System.out.println("Waiting for clients on port " +
                serverSocket.getLocalPort() + " ...");

        // oczekiwanie na grczy
        while (true) {
            try {
                clientsInRoom[num] = waitForNewConnection(clients.size());
                num += 1;

                if (num == numberOfPlayers) {
                    System.out.println("All players connected");
                    break;
                }
            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        // rozpoczecie gry
        Thread room = new Room(this, clientsInRoom, numberOfPlayers);
        room.start();
    }
    public Client waitForNewConnection(int num) throws SocketTimeoutException,IOException{
        Socket clientSocket = serverSocket.accept();
        Client client = new Client(clientSocket, num);

        clients.addElement(client);
        System.out.println("Just connected to " +
                clientSocket.getRemoteSocketAddress());
        Thread t = new ClientHandler(client);
        t.start();

        return client;
    }
    public void waitForBrokenConnection(int num) throws SocketTimeoutException,IOException{
        Socket clientSocket = serverSocket.accept();

        clients.get(num).socket = clientSocket;
        System.out.println("Just connected to " +
                clientSocket.getRemoteSocketAddress());

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
                        in.readFully(message, 0, message.length); // read the message


                        // obsluge koljeki trzeba ogarnac
                        client.offer(message);
                    }

                }catch(IOException e){
                    try {
                        waitForBrokenConnection(client.getNumber());
                    }
                    catch (SocketTimeoutException s) {
                        System.out.println("Socket timed out!");
                        break;
                    } catch (IOException s) {
                        s.printStackTrace();
                        break;
                    }
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