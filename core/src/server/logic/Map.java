package server.logic;

import wow.shooter.entities.Box;

import java.util.Vector;

/**
 * Created by kopec on 2016-03-22.
 */
public class Map {
    private Vector<Box> boxes = new Vector<Box>();
    private int mapSize = 7;
    public Map(){
        for(int i=-mapSize;i<=mapSize;i++){
            boxes.addElement(new Box(i*100,100*mapSize));
            boxes.addElement(new Box(i*100,-100*mapSize));
            boxes.addElement(new Box(-100*mapSize,i*100));
            boxes.addElement(new Box(100*mapSize,i*100));
        }
        boxes.addElement(new Box(300,600));
        boxes.addElement(new Box(100,100));
        boxes.addElement(new Box(600,200));
        boxes.addElement(new Box(-200,-300));
    }

    public Vector<Box> getBoxes(){
        return boxes;
    }
}
