package components.agents;

import com.badlogic.gdx.math.Vector2;


/**
 * Created by kopec on 2016-03-22.
 */

public class Player {
    private String name;
    private int id;
    private float speed = 200;

    public Vector2 position = new Vector2();
    public Vector2 destination = new Vector2();
    public Vector2 velocity = new Vector2();

    public Player(int id, float x, float y){
        this.id = id;
        position = new Vector2(x,y);
    }

    public void move(float dt){
        velocity.set(new Vector2(destination.x-position.x,destination.y-position.y).limit(speed));
        position.x += velocity.x * dt;
        position.y += velocity.y * dt;
    }

    public Bullet shoot(Vector2 direction){
        Bullet bullet = new Bullet(position,direction);
        return bullet;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
}
