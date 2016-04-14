package wow.shooter.logic.updating;

import components.entities.Box;
import components.entities.Bullet;
import components.entities.Enemy;
import components.entities.Player;
import components.data.GameData;
import wow.shooter.logic.handlers.HandleOutput;

import static components.data.functions.DataSetters.setHitData;

/**
 * Created by kopec on 2016-03-25.
 */
public class Bullets {
    private GameData g = GameData.getInstance();
    private HandleOutput o = HandleOutput.getInstance();
    Player player;
    boolean remove = true;
    boolean hit = false;


    public void update(float dt){
        for(Bullet bullet: g.bullets) {
            bullet.move(dt);
            if(notNeeded(bullet)){
                break;
            }
            if(gotHit(bullet)){
                player.setHealth(player.getHealth()-10);
                o.sendHit();
                break;
            }
        }
    }
    public boolean notNeeded(Bullet bullet)
    {
            remove = true;
            hit = false;

            if(bullet.dist(player.position)<500){
                remove = false;
            }
            for (Enemy enemy : g.enemies) {
                // ktos trafiony
                if(bullet.dist(enemy.position)<50 && bullet.my){
                    hit = true;
                    break;
                }
                if(bullet.dist(enemy.position)<500){
                    remove = false;
                }
            }
            for(Box box: g.boxes){
                if(bullet.dist(box.position)<50){
                    remove = true;
                    break;
                }
            }
            if(remove || hit){
                g.bullets.remove(bullet);
                return true;
            }
        return false;
    }
    public boolean gotHit(Bullet bullet){
        if(bullet.dist(player.position)<50 && !bullet.my) {
            g.bullets.remove(bullet);
            return true;
        }
        return false;
    }

}
