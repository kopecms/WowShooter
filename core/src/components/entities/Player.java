package components.entities;

import com.badlogic.gdx.math.Vector2;


/**
 * Created by kopec on 2016-03-22.
 */

public class Player extends Actor{
    public Player(int id, float x, float y){
        this.id = id;
        position = new Vector2(x,y);

        setHealth(100);
    }

    public Bullet shoot(Vector2 direction){
        Bullet bullet = new Bullet(position,direction);
        bullet.my = true;
        return bullet;
    }
}
