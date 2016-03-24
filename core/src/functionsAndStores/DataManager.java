package functionsAndStores;

import components.agents.Bullet;
import components.agents.Enemy;
import components.agents.Player;
import enums.DataType;
import enums.ObjectType;
import components.*;
import java.nio.ByteBuffer;
import java.util.Vector;

/**
 * Created by kopec on 2016-03-22.
 */
public class DataManager {
    private Map map = new Map();
    public Vector<Player> players = new Vector<Player>();
    public Vector<Enemy> enemies = new Vector<Enemy>();
    public Vector<Box> boxes = new Vector<Box>();
    public Vector<Bullet> bullets = new Vector<Bullet>();

    public void setBoxes(){
        boxes = new Vector<Box>(map.getBoxes());
    }

}
