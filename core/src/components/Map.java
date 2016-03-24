package components;

import components.agents.Enemy;
import components.agents.Player;

import java.util.Vector;

/**
 * Created by kopec on 2016-03-22.
 */
public class Map {
    private Vector<Box> boxes = new Vector<Box>();

    public Map(){
        boxes.addElement(new Box(0,300,700));
        boxes.addElement(new Box(0,100,100));
        boxes.addElement(new Box(0,600,200));
        boxes.addElement(new Box(0,1000,900));
    }

    public Vector<Box> getBoxes(){
        return boxes;
    }
}
