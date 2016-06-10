package tests;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;
import wow.shooter.entities.Box;
import wow.shooter.entities.Enemy;
import wow.shooter.entities.Player;

import java.util.Vector;
import static wow.shooter.logic.IncomingDataHandler.*;
import static functions.DataSetter.*;
import static org.junit.Assert.*;

/**
 * Created by kopec on 2016-05-25.
 */
public class DataHandlerTest {
    Vector<Enemy> enemies = new Vector<>();
    Vector<Box> boxes = new Vector<>();
    Box box = new Box(0, 0);
    Enemy enemy = new Enemy();
    Player player = new Player(1, 0, 0);

    public DataHandlerTest() {
        enemy.id = 0;
        enemy.position = new Vector2(0, 0);
        enemies.addElement(enemy);
    }

    @Test
    public void setName() throws Exception {
        handleName(new byte[]{0, 0, 100, 100, 100}, enemies);
        assertTrue(enemy.name.equals("ddd"));
    }

    @Test
    public void lagOccurred() throws Exception {
        handleLag(setErrorCorrection(0, new Vector2(400, 400), new Vector2(0, 0)), enemies);
        assertTrue(enemy.position.equals(new Vector2(400, 400)));
    }

    @Test
    public void lagDidNotOccurred() throws Exception {
        handleLag(setErrorCorrection(0, new Vector2(100, 100), new Vector2(0, 0)), enemies);
        assertTrue(enemy.position.equals(new Vector2(0, 0)));
    }

    @Test
    public void playerHit() throws Exception {
        handleHit(setHitData(1, 90), enemies, player);
        assertEquals(player.getHealth(), 90);
    }

    @Test
    public void enemyHit() throws Exception {
        handleHit(setHitData(0, 90), enemies, player);
        assertEquals(enemy.getHealth(), 90);
    }

    @Test
    public void boxObject() throws Exception {
        handleObject(setBoxData(box), enemies, boxes);
        assertEquals(boxes.size(), 1);
    }

    @Test
    public void enemyObject() throws Exception {
        handleObject(setEnemyData(2, 0, 0), enemies, boxes);
        assertEquals(enemies.size(), 2);
    }

}