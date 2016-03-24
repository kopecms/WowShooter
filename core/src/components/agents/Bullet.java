package components.agents;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by kopec on 2016-03-24.
 */
public class Bullet {
    public static float speed = 400;
    public Vector2 position = new Vector2();
    public Vector2 velocity = new Vector2();

    public Bullet(Vector2 position, Vector2 velocity){
        this.position = new Vector2(position);
        this.velocity = velocity;
    }
    public void move(float dt){
        position.x += velocity.x * dt;
        position.y += velocity.y * dt;
    }
}
