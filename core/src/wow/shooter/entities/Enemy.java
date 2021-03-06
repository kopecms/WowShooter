package wow.shooter.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by kopec on 2016-03-22.
 */
public class Enemy {
    public String name = "";
    public int id;
    public boolean dead = false;

    public int score = 0;

    private int health = 100;

    private float speed = 300;

    public Vector2 position = new Vector2();
    public Vector2 destination = new Vector2();
    public Vector2 velocity = new Vector2();

    public Vector2 afterCollision = new Vector2();

    public Enemy(){
        setHealth(100);
    }

    public void collisionHandler(){
            destination.set(afterCollision);
    }

    public void setHealth(int health){
        if(health <= 100 && health >=0)
            this.health = health;
    }
    public int getHealth(){
        return health;
    }

    public void move(float dt){
        velocity.set(new Vector2(destination.x-position.x,destination.y-position.y).limit(speed));
        position.x += velocity.x * dt;
        position.y += velocity.y * dt;
    }
}
