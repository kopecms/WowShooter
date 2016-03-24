package components;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by kopec on 2016-03-24.
 */

public class Box {
    public int id;
    public Vector2 position;

    public Box(int id, float x,float y){
        this.id = id;
        this.position = new Vector2(x,y);
    }
}
