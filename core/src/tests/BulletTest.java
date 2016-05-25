package tests;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;
import wow.shooter.entities.Bullet;

import static org.junit.Assert.*;

/**
 * Created by kopec on 2016-05-25.
 */
public class BulletTest {
    Bullet bullet = new Bullet(new Vector2(0,0), new Vector2(1,1));
    @Test
    public void move() throws Exception {
        bullet.move(1);
        assertTrue(bullet.position.equals(new Vector2(1,1)));
    }
}