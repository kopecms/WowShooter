package tests;

import org.junit.Test;
import wow.shooter.entities.Player;

import static org.junit.Assert.*;

/**
 * Created by kopec on 2016-05-25.
 */
public class PlayerTest {
    Player player = new Player(0,0,0);
    public PlayerTest(){}
    @Test
    public void setHealth() throws Exception {
        player.setHealth(50);
        assertEquals(player.getHealth(),50);
    }

    @Test
    public void setTooMuchHealth() throws Exception {
        player.setHealth(150);
        assertEquals(player.getHealth(),100);
    }

    @Test
    public void setLessThanZeroHealth() throws Exception {
        player.setHealth(-100);
        assertEquals(player.getHealth(),100);
    }

}