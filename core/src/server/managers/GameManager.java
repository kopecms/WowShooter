package server.managers;

/**
 * Created by kopec on 2016-03-22.
 */

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.stream.Stream;

import enums.DataType;
import functions.fun;
import server.Client;

public class GameManager {
    private Client [] clients;
    public GameManager(Client [] clients){
        this.clients = clients;
    }


    public void handleGame(){

    }

    public void handleData() {
        for(Client client: clients){
            if(client.peek()){
                byte [] recv = client.poll();
                DataType data = DataType.fromInt((int)recv[0]);
                System.out.println(data);
                if( data == data.NAME){

                }
                else if( data == data.MOVE){

                }
                else if( data == data.SHOOT){

                }
                else if (data == data.MSG){

                }




            }
        }
    }
}
