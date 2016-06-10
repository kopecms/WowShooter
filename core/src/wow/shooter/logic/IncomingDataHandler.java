
package wow.shooter.logic;
import com.badlogic.gdx.math.Vector2;
import functions.StringAndBytes;
import wow.shooter.entities.Box;
import wow.shooter.entities.Bullet;
import wow.shooter.entities.Enemy;
import wow.shooter.entities.Player;

import enums.ObjectType;
import java.util.Vector;

public class IncomingDataHandler {
    public IncomingDataHandler() {
    }

    public static void handleName(byte[] recv, Vector<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (enemy.id == recv[1]) {
                enemy.name = StringAndBytes.stringFromBytes(recv, 2, recv.length - 2);
            }
        }
    }

    public static void handleLag(byte[] recv, Vector<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (enemy.id == recv[1]) {
                Vector2 p = new Vector2((float) StringAndBytes.bytesToInt(recv, 2, 4), (float) StringAndBytes.bytesToInt(recv, 6, 4));
                new Vector2((float) StringAndBytes.bytesToInt(recv, 10, 4), (float) StringAndBytes.bytesToInt(recv, 14, 4));
                if (p.sub(enemy.position).len() > 200) {
                    enemy.position.set((float) StringAndBytes.bytesToInt(recv, 2, 4), (float) StringAndBytes.bytesToInt(recv, 6, 4));
                    enemy.destination.set((float) StringAndBytes.bytesToInt(recv, 10, 4), (float) StringAndBytes.bytesToInt(recv, 14, 4));
                }
            }
        }

    }

    public static void handleCollison(byte[] recv, Vector<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (enemy.id == recv[1]) {
                enemy.afterCollision.set((float) StringAndBytes.bytesToInt(recv, 2, 4), (float) StringAndBytes.bytesToInt(recv, 6, 4));
            }
        }

    }

    public static void handleHit(byte[] recv, Vector<Enemy> enemies, Player player) {
        for (Enemy enemy : enemies) {
            if (player.id == recv[1]) {
                player.setHealth(StringAndBytes.bytesToInt(recv, 2, 4));
            } else if (enemy.id == recv[1]) {
                enemy.setHealth(StringAndBytes.bytesToInt(recv, 2, 4));
            }
        }

    }

    public static void handleShoot(byte[] recv, Vector<Enemy> enemies, Vector<Bullet> bullets) {
        for (Enemy enemy : enemies) {
            if (enemy.id == recv[1]) {
                bullets.addElement(new Bullet(enemy.position, (new Vector2((float) StringAndBytes.bytesToInt(recv, 2, 4), (float) StringAndBytes.bytesToInt(recv, 6, 4))).setLength(Bullet.speed)));
            }
        }
    }

    public static void handlePosition(byte[] recv, Player player) {
        player.position.set((float) StringAndBytes.bytesToInt(recv, 1, 4), (float) StringAndBytes.bytesToInt(recv, 5, 4));
        player.destination.set(player.position);
    }

    public static void handleMove(byte[] recv, Vector<Enemy> enemies) {
        for (Enemy e : enemies) {
            if (e.id == recv[1]) {
                e.destination.set((float) StringAndBytes.bytesToInt(recv, 2, 4), (float) StringAndBytes.bytesToInt(recv, 6, 4));
            }
        }

    }

    public static void handleObject(byte[] recv, Vector<Enemy> enemies, Vector<Box> boxes) {
        ObjectType obj = ObjectType.fromInt(recv[1]);
        if (obj == ObjectType.ENEMY) {
            Enemy enemy = new Enemy();
            enemy.id = recv[2];
            enemy.position.set((float) StringAndBytes.bytesToInt(recv, 3, 4), (float) StringAndBytes.bytesToInt(recv, 7, 4));
            enemies.addElement(enemy);
        } else if (obj == ObjectType.BOX) {
            boxes.addElement(new Box((float) StringAndBytes.bytesToInt(recv, 3, 4), (float) StringAndBytes.bytesToInt(recv, 7, 4)));
        }

    }

    public static void disconnection(byte[] recv, Vector<Enemy> enemies) {
        for (Enemy enemy : enemies)
            if (enemy.id == recv[1]) {
                enemies.remove(enemy);
                return;
            }
    }

    public static void addScore(byte[] recv, Player player, Vector<Enemy> enemies) {
        player.score += 100;
        for (Enemy enemy : enemies)
            if (enemy.id != recv[1])
                enemy.score += 100;
    }

    public static void reset(byte[] recv, Player player, Vector<Enemy> enemies) {
        if (recv[1] == player.id) {
            player.position.set((float) StringAndBytes.bytesToInt(recv, 2, 4), (float) StringAndBytes.bytesToInt(recv, 6, 4));
            player.destination.set((float) StringAndBytes.bytesToInt(recv, 2, 4), (float) StringAndBytes.bytesToInt(recv, 6, 4));
            player.setHealth(100);
            player.score = 0;
            player.dead = false;
        } else {
            for (Enemy e : enemies)
                if (recv[1] == e.id) {
                    e.position.set((float) StringAndBytes.bytesToInt(recv, 2, 4), (float) StringAndBytes.bytesToInt(recv, 6, 4));
                    e.destination.set((float) StringAndBytes.bytesToInt(recv, 2, 4), (float) StringAndBytes.bytesToInt(recv, 6, 4));
                    e.setHealth(100);
                    e.score = 0;
                    e.dead = false;
                }
        }
    }

}
