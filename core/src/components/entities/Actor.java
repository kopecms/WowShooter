package components.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by kopec on 2016-04-14.
 */
public class Actor {
    public String name;
    public int id;
    private int health;
    final private float speed = 300;
    public Vector2 position = new Vector2();
    public Vector2 destination = new Vector2();
    public Vector2 velocity = new Vector2();

    public void move(float dt){
        velocity.set(new Vector2(destination.x-position.x,destination.y-position.y).limit(speed));
        position.x += velocity.x * dt;
        position.y += velocity.y * dt;
    }

    public void setHealth(int health){
        if(health <= 100 && health >=0)
            this.health = health;
    }
    public int getHealth(){
        return health;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}
