package wow.shooter.logic;

import com.badlogic.gdx.math.Vector2;
import wow.shooter.entities.Box;
import wow.shooter.entities.Bullet;
import wow.shooter.entities.Enemy;
import wow.shooter.entities.Player;

import java.util.ConcurrentModificationException;
import java.util.Vector;

/**
 * Created by kopec on 2016-05-17.
 */
public class Update {
    private static int objectsSize = 100;

    public static boolean updatePlayer(float dt, Player player){
        if(!player.dead)
            player.move(dt);

        if(player.getHealth() == 0 && !player.dead) {
            player.dead = true;
            return true;
        }
        return false;
    }

    public static void updateEnemies(float dt, Vector<Enemy> enemies) throws ConcurrentModificationException{
        for(Enemy enemy: enemies){
            if(!enemy.dead)
                enemy.move(dt);
            if(enemy.getHealth() == 0 && !enemy.dead)
                enemy.dead = true;
        }
    }
    public static boolean updateBullets(float dt, Player player,Vector<Enemy> enemies,Vector<Box> boxes, Vector<Bullet> bullets) throws ConcurrentModificationException{
        for(Bullet bullet: bullets) {
            bullet.move(dt);

            if(notNeeded(bullet,player,enemies,boxes)){
                bullets.remove(bullet);
                break;
            }
            if(gotHit(bullet,player)){
                bullets.remove(bullet);
                player.setHealth(player.getHealth()-10);
                return true;
            }
        }
        return false;
    }
    public static boolean collisions(Player player, Vector<Box> boxes, Vector<Enemy> enemies) throws ConcurrentModificationException{
            for (Box box : boxes) {
                if (player.position.x + objectsSize > box.position.x &&
                        player.position.x < box.position.x + objectsSize &&
                        player.position.y < box.position.y + objectsSize &&
                        player.position.y + objectsSize > box.position.y) {
                    Vector2 v = new Vector2(player.position.x + player.velocity.y, player.position.y - player.velocity.x);
                    player.destination.set(v);
                    return true;
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
        return false;
    }
    private static boolean notNeeded(Bullet bullet, Player player, Vector<Enemy> enemies, Vector<Box> boxes)
    {
        if(bullet.dist(player.position)>700)
            return true;

        for (Enemy enemy : enemies)
            if(bullet.dist(enemy.position)<50 && bullet.my){
                return true;
            }

        for(Box box: boxes)
            if(bullet.dist(box.position)<50)
                return true;

        return false;
    }
    private static boolean gotHit(Bullet bullet, Player player){
        if(bullet.dist(player.position)<50 && !bullet.my)
            return true;
        return false;
    }
}
