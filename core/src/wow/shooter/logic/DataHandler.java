
package wow.shooter.logic;
import com.badlogic.gdx.math.Vector2;
import components.fun;
import wow.shooter.entities.Box;
import wow.shooter.entities.Bullet;
import wow.shooter.entities.Enemy;
import wow.shooter.entities.Player;



import com.badlogic.gdx.math.Vector2;
import components.fun;
import enums.ObjectType;
import java.util.Iterator;
import java.util.Vector;
import wow.shooter.entities.Box;
import wow.shooter.entities.Bullet;
import wow.shooter.entities.Enemy;
import wow.shooter.entities.Player;

public class DataHandler {
    public DataHandler() {
    }

    public static void handleName(byte[] recv, Vector<Enemy> enemies) {
        Iterator var2 = enemies.iterator();

        while(var2.hasNext()) {
            Enemy enemy = (Enemy)var2.next();
            if(enemy.id == recv[1]) {
                enemy.name = fun.stringFromBytes(recv, 2, recv.length - 2);
            }
        }

    }

    public static void handleLag(byte[] recv, Vector<Enemy> enemies) {
        Iterator var2 = enemies.iterator();

        while(var2.hasNext()) {
            Enemy enemy = (Enemy)var2.next();
            if(enemy.id == recv[1]) {
                Vector2 p = new Vector2((float)fun.bytesToInt(recv, 2, 4), (float)fun.bytesToInt(recv, 6, 4));
                new Vector2((float)fun.bytesToInt(recv, 10, 4), (float)fun.bytesToInt(recv, 14, 4));
                if(p.sub(enemy.position).len() > 200.0F) {
                    enemy.position.set((float)fun.bytesToInt(recv, 2, 4), (float)fun.bytesToInt(recv, 6, 4));
                    enemy.destination.set((float)fun.bytesToInt(recv, 10, 4), (float)fun.bytesToInt(recv, 14, 4));
                }
            }
        }

    }

    public static void handleCollison(byte[] recv, Vector<Enemy> enemies) {
        Iterator var2 = enemies.iterator();

        while(var2.hasNext()) {
            Enemy enemy = (Enemy)var2.next();
            if(enemy.id == recv[1]) {
                enemy.afterCollision.set((float)fun.bytesToInt(recv, 2, 4), (float)fun.bytesToInt(recv, 6, 4));
            }
        }

    }

    public static void handleHit(byte[] recv, Vector<Enemy> enemies, Player player) {
        Iterator var3 = enemies.iterator();

        while(var3.hasNext()) {
            Enemy enemy = (Enemy)var3.next();
            if(player.id == recv[1]) {
                player.setHealth(fun.bytesToInt(recv, 2, 4));
            } else if(enemy.id == recv[1]) {
                enemy.setHealth(fun.bytesToInt(recv, 2, 4));
            }
        }

    }

    public static void handleShoot(byte[] recv, Vector<Enemy> enemies, Vector<Bullet> bullets) {
        Iterator var3 = enemies.iterator();

        while(var3.hasNext()) {
            Enemy enemy = (Enemy)var3.next();
            if(enemy.id == recv[1]) {
                bullets.addElement(new Bullet(enemy.position, (new Vector2((float)fun.bytesToInt(recv, 2, 4), (float)fun.bytesToInt(recv, 6, 4))).setLength(Bullet.speed)));
            }
        }

    }

    public static void handlePosition(byte[] recv, Player player) {
        player.position.set((float)fun.bytesToInt(recv, 1, 4), (float)fun.bytesToInt(recv, 5, 4));
        player.destination.set(player.position);
    }

    public static void handleMove(byte[] recv, Vector<Enemy> enemies) {
        Iterator var2 = enemies.iterator();

        while(var2.hasNext()) {
            Enemy e = (Enemy)var2.next();
            if(e.id == recv[1]) {
                e.destination.set((float)fun.bytesToInt(recv, 2, 4), (float)fun.bytesToInt(recv, 6, 4));
            }
        }

    }

    public static void handleObject(byte[] recv, Vector<Enemy> enemies, Vector<Box> boxes) {
        ObjectType obj = ObjectType.fromInt(recv[1]);
        if(obj == ObjectType.ENEMY) {
            Enemy enemy = new Enemy();
            enemy.id = recv[2];
            enemy.position.set((float)fun.bytesToInt(recv, 3, 4), (float)fun.bytesToInt(recv, 7, 4));
            enemies.addElement(enemy);
        } else if(obj == ObjectType.BOX) {
            boxes.addElement(new Box((float)fun.bytesToInt(recv, 3, 4), (float)fun.bytesToInt(recv, 7, 4)));
        }

    }
}
