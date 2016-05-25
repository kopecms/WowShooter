package wow.shooter.managers;


import wow.shooter.entities.Box;
import wow.shooter.entities.Bullet;
import wow.shooter.entities.Enemy;
import wow.shooter.entities.Player;

import java.util.Vector;

/**
 * Created by kopec on 2016-03-22.
 */

public class DataStore {
    public Vector<Enemy> enemies = new Vector<Enemy>();
    public Vector<Box> boxes = new Vector<Box>();
    public Vector<Bullet> bullets = new Vector<Bullet>();

    public int screenH;
    public float centerx;
    public float centery;

    public Player player;

    public void setSreenSize(float x, float y){
        screenH = (int)y;
        centerx = x / 2;
        centery = y / 2;
    }

}
