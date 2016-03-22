package wow.shooter.managers;

import components.Player;
import enums.DataType;
/**
 * Created by kopec on 2016-03-22.
 */
public class GameManager {
    private Player player;

    private DataType data;
    public GameManager(){

    }
    public void handleData(byte [] recv){
        data.fromInt(recv[0]);
        if(data == data.NUMBER){

        }
        else if(data == data.NONE){

        }
    }

}

