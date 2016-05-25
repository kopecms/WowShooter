package wow.shooter.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by kopec on 2016-03-24.
 */

public class Box {
    public int id = 0;
    public Vector2 position;
    public Box(float x,float y){
        this.position = new Vector2(x,y);
    }
}
