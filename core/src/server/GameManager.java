package server;

/**
 * Created by kopec on 2016-03-22.
 */

import java.nio.ByteBuffer;

public class GameManager {

    public byte [] startPosition(){
        int posx = 100;
        return ByteBuffer.allocate(4).putInt(posx).array();
    }
}
