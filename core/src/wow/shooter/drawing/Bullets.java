package wow.shooter.drawing;

import components.data.GameData;
import components.entities.Bullet;
import wow.shooter.main.Game;

/**
 * Created by kopec on 2016-04-14.
 */
public class Bullets {
    private GameData g = GameData.getInstance();

    public void render(){
        for(Bullet b: g.bullets){
            g.batch.draw(g.textures.getTexture("bullet"),
                    g.centerx + b.position.x - g.player.position.x,
                    g.centery + b.position.y - g.player.position.y);
        }
    }
}
