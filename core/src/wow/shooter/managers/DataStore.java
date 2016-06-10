package wow.shooter.managers;


import wow.shooter.entities.Box;
import wow.shooter.entities.Bullet;
import wow.shooter.entities.Enemy;
import wow.shooter.entities.Player;
import wow.shooter.logic.SimpleMenuHandler;

import java.util.Vector;

/**
 * Created by kopec on 2016-03-22.
 */

public class DataStore {
    public Vector<Enemy> enemies = new Vector<Enemy>();
    public Vector<Box> boxes = new Vector<Box>();
    public Vector<Bullet> bullets = new Vector<Bullet>();

    public int screenH;
    public int screenW;
    public float centerx;
    public float centery;

    public Player player;
    public SimpleMenuHandler menu;
    public void clear(){
        enemies = new Vector<Enemy>();
        boxes = new Vector<Box>();
        bullets = new Vector<Bullet>();
    }


    public void setScreenSize(float x, float y){
        screenH = (int)y;
        screenW = (int)x;
        centerx = x / 2;
        centery = y / 2;
    }

}
