package server.managers;

import server.logic.Client;
import server.logic.Map;
import wow.shooter.entities.Box;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;
import components.DataSetter;
import enums.DataType;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;
import server.logic.Client;
import server.logic.Map;
import wow.shooter.entities.Box;

public class GameManager {
    private Vector<Client> clients;
    Map map = new Map();

    public GameManager(Vector<Client> c) {
        this.clients = c;
    }

    public void handleData() {
        Iterator var1 = this.clients.iterator();

        while(true) {
            while(true) {
                Client client;
                do {
                    if(!var1.hasNext()) {
                        return;
                    }

                    client = (Client)var1.next();
                } while(!client.peek());

                byte[] recv = client.poll();
                DataType data = DataType.fromInt(recv[0]);
                switch(data) {
                    case NAME:
                        this.sendFurther(client, DataSetter.setNameData(client.number, Arrays.copyOfRange(recv, 1, recv.length)));
                        break;
                    case SHOOT:
                        this.sendFurther(client, DataSetter.setBulletData(client.number, Arrays.copyOfRange(recv, 1, recv.length)));
                        break;
                    case MOVE:
                        this.sendFurther(client, DataSetter.setDestinationData(client.number, Arrays.copyOfRange(recv, 1, recv.length)));
                        break;
                    case LAGERRORCORRECTION:
                    case HIT:
                    case COLLISION:
                        this.sendFurther(client, recv);
                        break;
                    case GETSTATE:
                        client.send(DataSetter.setIdData(client.number));
                        client.send(DataSetter.positionData((int)client.position.x, (int)client.position.y));
                        Iterator var5 = this.clients.iterator();

                        while(var5.hasNext()) {
                            Client box = (Client)var5.next();
                            if(box.number != client.number) {
                                box.send(DataSetter.setHitData(client.number, client.health));
                                box.send(DataSetter.setEnemyData(client.number, (int)client.position.x, (int)client.position.y));
                                client.send(DataSetter.setHitData(box.number, box.health));
                                client.send(DataSetter.setEnemyData(box.number, (int)box.position.x, (int)box.position.y));
                            }
                        }

                        var5 = this.map.getBoxes().iterator();

                        while(var5.hasNext()) {
                            Box box1 = (Box)var5.next();
                            client.send(DataSetter.setBoxData(box1));
                        }
                }
            }
        }
    }

    public void sendFurther(Client client, byte[] recv) {
        Iterator var3 = this.clients.iterator();

        while(var3.hasNext()) {
            Client c = (Client)var3.next();
            if(c != client) {
                c.send(recv);
            }
        }

    }
}
