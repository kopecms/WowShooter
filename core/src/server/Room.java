package server;

/**
 * Created by kopec on 2016-03-22.
 */
public class Room extends Thread{
    private WowServer server;
    private Client [] clients;
    private int numberOfPlayers;

    private GameManager manager = new GameManager();
    private DataManager data = new DataManager();
    public Room(WowServer server, Client [] clients, int numberOfPlayers){
        this.server = server;
        this.numberOfPlayers = numberOfPlayers;
        this.clients = clients;
    }
    public void setUp(){
       // for(int i=0; i< numberOfPlayers; i++){
        clients[0].send(data.setIdData(0));
        clients[1].send(data.setIdData(1));
        clients[1].send(data.setPositionData(98,50));
        clients[0].send(data.setPositionData(400,200));
        clients[0].send(data.setEnemyData(0,98,50));

        clients[1].send(data.setEnemyData(1,400,200));
        //}
    }

    public void run() {
        setUp();
    }

}
