package server.main;

/**
 * Created by kopec on 2016-03-22.
 */

import server.logic.Client;
import server.logic.Game;

import java.net.*;
import java.io.*;
import java.util.*;


public class WowServer{
    private ServerSocket serverSocket;
    Game game;
    public WowServer(int port, int n) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(1000000);
    }

    public void startGame(){
        System.out.println("Waiting for clients on port " +
                serverSocket.getLocalPort() + " ...");

        game = new Game();
        game.start();
        int num = 0;

        while (true) {
            try {
                game.addClient(waitForNewConnection(num++));
            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public Client waitForNewConnection(int num) throws SocketTimeoutException,IOException{

        Socket clientSocket = serverSocket.accept();
        Client client = new Client(clientSocket, num);
        Thread t = new ClientHandler(client);
        t.start();

        System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());
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
                    client.close();
                    game.removeClient(client);
                }
            }
        }
    }


    public static void main(String [] args){
        int port = 5055;
        int numberOfPlayers = 2;
        try{
            WowServer t = new WowServer(port, numberOfPlayers);
            t.startGame();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}