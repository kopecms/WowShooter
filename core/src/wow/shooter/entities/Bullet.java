package wow.shooter.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by kopec on 2016-03-24.
 */
public class Bullet {
    public static float speed = 400;
    public Vector2 position = new Vector2();
    public Vector2 velocity = new Vector2();
    public boolean my = false;

    public Bullet(Vector2 position, Vector2 velocity){
        this.position = new Vector2(position);
        this.velocity = velocity;
    }
    public void move(float dt){
        position.x += velocity.x * dt;
        position.y += velocity.y * dt;
    }
    public float dist(Vector2 playerPosition){
        Vector2 v = new Vector2(position.x-playerPosition.x,position.y-playerPosition.y);
        return v.len();
    }
}
