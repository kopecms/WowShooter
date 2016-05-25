package tests;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;
import wow.shooter.entities.Enemy;

import static org.junit.Assert.*;

/**
 * Created by kopec on 2016-05-25.
 */
public class EnemyTest {
    Enemy enemy = new Enemy();

    @Test
    public void setHealth() throws Exception {
        enemy.setHealth(50);
        assertEquals(enemy.getHealth(),50);
    }

    @Test
    public void setTooMuchHealth() throws Exception {
        enemy.setHealth(150);
        assertEquals(enemy.getHealth(),100);
    }

    public void setLessThanZeroHealth() throws Exception {
        enemy.setHealth(-100);
        assertEquals(enemy.getHealth(),100);
    }

}