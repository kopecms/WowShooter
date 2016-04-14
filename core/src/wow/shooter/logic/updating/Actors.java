package wow.shooter.logic.updating;

import com.badlogic.gdx.math.Vector2;
import components.data.GameData;
import components.entities.Box;
import components.entities.Enemy;
import components.entities.Player;
import wow.shooter.logic.handlers.HandleOutput;
import wow.shooter.main.Game;

import static components.data.functions.DataSetters.setCollisionData;

/**
 * Created by kopec on 2016-04-14.
 */
public class Actors {
    GameData g = GameData.getInstance();
    private HandleOutput o = HandleOutput.getInstance();
    Player player = g.player;

    int objectsSize = g.objectsSize;
    public void update(float dt){
        player.move(dt);

        for(Enemy enemy: g.enemies){
            enemy.move(dt);
        }

        for(Box box: g.boxes) {
            if (player.position.x + objectsSize > box.position.x &&
                   player.position.x < box.position.x + g.objectsSize &&
                    player.position.y < box.position.y + objectsSize &&
                    player.position.y + objectsSize > box.position.y) {
                Vector2 v = new Vector2(player.position.x + player.velocity.y,
                        player.position.y - player.velocity.x);
                player.destination.set(v);
                o.sendCollision(v);
            }
            for (Enemy enemy : g.enemies) {
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
