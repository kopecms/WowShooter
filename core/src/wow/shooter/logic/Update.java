package wow.shooter.logic;

import com.badlogic.gdx.math.Vector2;
import wow.shooter.entities.Box;
import wow.shooter.entities.Bullet;
import wow.shooter.entities.Enemy;
import wow.shooter.entities.Player;

import java.util.ConcurrentModificationException;
import java.util.Vector;

import static components.DataSetter.setCollisionData;
import static components.DataSetter.setHitData;

/**
 * Created by kopec on 2016-05-17.
 */
public class Update {
    public static void updatePlayer(float dt, Player player){
        player.move(dt);

        if(player.getHealth() == 0)
            player.dead = true;
    }

    public static void updateEnemies(float dt, Vector<Enemy> enemies) throws ConcurrentModificationException{
        for(Enemy enemy: enemies){
            enemy.move(dt);

            if(enemy.getHealth() == 0)
                enemies.remove(enemy);
        }
    }
    public static void updateBullets(float dt, Client client, Vector<Bullet> bullets, Player player, BulletLogic bulletLogic) throws ConcurrentModificationException{
        for(Bullet bullet: bullets) {
            bullet.move(dt);

            if(bulletLogic.notNeeded(bullet)){
                break;
            }
            if(bulletLogic.gotHit(bullet)){
                player.setHealth(player.getHealth()-10);
                client.send(setHitData(player.id,player.getHealth()));
                break;
            }
        }
    }
    public static void collisions(Player player,Client client, int objectsSize, Vector<Box> boxes, Vector<Enemy> enemies) throws ConcurrentModificationException{
            for (Box box : boxes) {
                if (player.position.x + objectsSize > box.position.x &&
                        player.position.x < box.position.x + objectsSize &&
                        player.position.y < box.position.y + objectsSize &&
                        player.position.y + objectsSize > box.position.y) {
                    Vector2 v = new Vector2(player.position.x + player.velocity.y,
                            player.position.y - player.velocity.x);
                    player.destination.set(v);
                    client.send(setCollisionData(player.id, v));
                }
                for (Enemy enemy : enemies) {
                    if (enemy.position.x + objectsSize > box.position.x &&
                            enemy.position.x < box.position.x + objectsSize &&
                            enemy.position.y < box.position.y + objectsSize &&
                            enemy.position.y + objectsSize > box.position.y) {
                        enemy.collisionHandler();
                    }

            }
            }
    }
}
