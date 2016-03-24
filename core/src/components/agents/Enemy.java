package components.agents;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by kopec on 2016-03-22.
 */
public class Enemy {
    String name;
    int id;
    private float speed = 200;
    public Vector2 position = new Vector2();
    public Vector2 destination = new Vector2();
    public Vector2 velocity = new Vector2();

    public void move(float dt){
        velocity.set(new Vector2(destination.x-position.x,destination.y-position.y).limit(speed));
        position.x += velocity.x * dt;
        position.y += velocity.y * dt;
    }

    public void setId(int id) { this.id = id; }
    public int getId(){
        return id;
    }

}
