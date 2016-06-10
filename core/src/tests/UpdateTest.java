package tests;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;
import wow.shooter.entities.Box;
import wow.shooter.entities.Bullet;
import wow.shooter.entities.Enemy;
import wow.shooter.entities.Player;

import java.util.Vector;

import static wow.shooter.logic.UpdateGame.*;
import static org.junit.Assert.*;

/**
 * Created by kopec on 2016-05-25.
 */
public class UpdateTest {
    Player player = new Player(0,0,0);
    Vector<Enemy> enemies = new Vector<Enemy>();
    Enemy enemy = new Enemy();
    Vector<Bullet> bullets = new Vector<Bullet>();
    Bullet bullet = new Bullet(new Vector2(0,0),new Vector2(1,1));
    Vector<Box> boxes = new Vector<Box>();
    Box box = new Box(100,100);

    public UpdateTest(){
        enemies.addElement(enemy);
        boxes.addElement(box);
        bullets.addElement(bullet);
    }
    @Test
    public void updatePlayerPositionWhenAlive() throws Exception {
        player.destination = new Vector2(1,1);
        updatePlayer(1,player,enemies);
        assertTrue(player.position.equals(new Vector2(1,1)));
    }

    @Test
    public void doNotUpdatePlayerPositionWhenDead() throws Exception {
        player.dead = true;
        player.destination = new Vector2(1,1);

        updatePlayer(1,player,enemies);
        assertTrue(player.position.equals(new Vector2(0,0)));
    }

    @Test
    public void updateEnemyPositionWhenAlive() throws Exception {
        enemy.destination = new Vector2(1,1);

        updateEnemies(1,enemies);
        assertTrue(enemy.position.equals(new Vector2(1,1)));
    }

    @Test
    public void doNotUpdateEnemyPositionWhenDead() throws Exception {
        enemy.dead = true;
        enemy.destination = new Vector2(1,1);

        updateEnemies(1,enemies);
        assertTrue(enemy.position.equals(new Vector2(0,0)));
    }
    @Test
    public void updateBulletPosition() throws Exception{
        updateBullets(1,player,enemies,boxes,bullets);
        assertTrue(bullet.position.equals(new Vector2(1,1)));
    }
    @Test
    public void  bulletHitPlayer() throws Exception{
        updateBullets(1,player,enemies,boxes,bullets);
        assertTrue(bullets.isEmpty());
    }
    @Test
    public void bulletNotNeeded() throws Exception{
        bullet.position = new Vector2(10000, 10000);
        updateBullets(1,player,enemies,boxes,bullets);
        assertTrue(bullets.isEmpty());
    }
}