package components.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by kopec on 2016-03-22.
 */
public class Enemy extends Actor {
    public Vector2 afterCollision = new Vector2();
    public Enemy(){
        setHealth(100);
    }
    public void collisionHandler(){
            destination.set(afterCollision);
    }

}
