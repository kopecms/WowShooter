package wow.shooter.logic;

import components.entities.Box;
import components.entities.Bullet;
import components.entities.Enemy;
import components.entities.Player;
import components.funstore.DataStore;

/**
 * Created by kopec on 2016-03-25.
 */
public class BulletLogic  {
    DataStore data;
    Player player;
    boolean remove = true;
    boolean hit = false;

    public BulletLogic(DataStore data, Player player)
    {
        this.data = data;
        this.player = player;
    }
    public boolean notNeeded(Bullet bullet)
    {
            remove = true;
            hit = false;

            if(bullet.dist(player.position)<500){
                remove = false;
            }
            for (Enemy enemy : data.enemies) {
                // ktos trafiony
                if(bullet.dist(enemy.position)<50 && bullet.my){
                    hit = true;
                    break;
                }
                if(bullet.dist(enemy.position)<500){
                    remove = false;
                }
            }
            for(Box box: data.boxes){
                if(bullet.dist(box.position)<50){
                    remove = true;
                    break;
                }
            }
            if(remove || hit){
                data.bullets.remove(bullet);
                return true;
            }
        return false;
    }
    public boolean gotHit(Bullet bullet){
        if(bullet.dist(player.position)<50 && !bullet.my) {
            data.bullets.remove(bullet);
            return true;
        }
        return false;
    }

}
