package server.managers;

import functions.fun;
import server.logic.Client;
import server.logic.Map;
import wow.shooter.entities.Box;

import java.util.Arrays;
import java.util.Vector;
import functions.DataSetter;
import enums.DataType;

public class GameManager {
    private Vector<Client> clients;
    Map map = new Map();

    public GameManager(Vector<Client> c) {
        clients = c;
    }

    public void handleData() {
        for(Client client: clients){
            if(client.peek()){

                byte[] recv = client.poll();
                DataType data = DataType.fromInt(recv[0]);
                switch(data) {
                    case LAGERRORCORRECTION:
                    case HIT:
                    case COLLISION:
                        sendFurther(client, recv);
                        break;
                    case SHOOT:
                        sendFurther(client, DataSetter.setBulletData(client.number, Arrays.copyOfRange(recv, 1, recv.length)));
                        break;
                    case MOVE:
                        sendFurther(client, DataSetter.setDestinationData(client.number, Arrays.copyOfRange(recv, 1, recv.length)));
                        break;
                    case GETSTATE:
                        sendGameState(client);
                        break;
                    case NAME:
                        client.name = fun.stringFromBytes(recv,1,recv.length-1);
                        sendFurther(client, DataSetter.setNameData(client.number, Arrays.copyOfRange(recv, 1, recv.length)));
                        break;
                }
            }
        }
    }

    public void sendFurther(Client client, byte[] recv) {
        for(Client c: clients)
            if(c != client)
                c.send(recv);
    }

    public void sendGameState(Client client){
        client.send(DataSetter.setIdData(client.number));
        client.send(DataSetter.positionData((int)client.position.x, (int)client.position.y));
        for(Client clientOnServer: clients){
            if(clientOnServer.number != client.number) {
                client.send(DataSetter.setEnemyData(clientOnServer.number, (int)clientOnServer.position.x, (int)clientOnServer.position.y));
                client.send(DataSetter.setNameData(clientOnServer.number, clientOnServer.name.getBytes()));
                clientOnServer.send(DataSetter.setEnemyData(client.number, (int)client.position.x, (int)client.position.y));
            }
        }
        for(Box box: map.getBoxes()){
            client.send(DataSetter.setBoxData(box));
        }
    }
}
