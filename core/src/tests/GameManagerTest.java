package tests;

import com.badlogic.gdx.math.Vector2;
import enums.DataType;
import org.junit.Test;
import wow.shooter.entities.Enemy;
import wow.shooter.managers.DataStore;
import wow.shooter.managers.GameManager;

import static org.junit.Assert.*;

/**
 * Created by kopec on 2016-05-25.
 */
public class GameManagerTest {
    DataStore data = new DataStore();
    GameManager manager = new GameManager(data);

    @Test
    public void addBulletAfterShootEvent() throws Exception {
        manager.shootEvent();
        assertEquals(data.bullets.size(), 1);
    }

    @Test
    public void setDestinationAfterTouchDownEvent() throws Exception {
        manager.touchDownEvent(10,10);
        assertTrue(manager.player.destination.equals(new Vector2(10,10)));
    }

    @Test
    public void setMouseAfterMouseEvent() throws Exception {
        manager.mouseEvent(100, 100);
        assertTrue(manager.mouse.getMouse().equals(new Vector2(100 - data.centerx, 100 - data.centery)));
    }

    @Test
    public void keyboardInputShoot() throws Exception {
        assertEquals(manager.handleKeyboardInput(62), 62);
        assertTrue(data.bullets.size() == 1);
    }
    @Test
    public void keyboardInputEsc() throws Exception{
        data.enemies.addElement(new Enemy());
        manager.connected = true;
        manager.handleKeyboardInput(131);
        assertEquals(data.enemies.size(), 0);
    }
    @Test
    public void handleDataType() throws Exception{
        assertTrue(manager.handleData(new byte[] {(byte)DataType.COLLISION.getId()}) == DataType.COLLISION);
    }

}