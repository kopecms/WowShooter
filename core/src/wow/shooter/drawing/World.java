package wow.shooter.drawing;

import components.entities.Box;
import components.data.GameData;

/**
 * Created by kopec on 2016-04-14.
 */
public class World {
    private GameData g = GameData.getInstance();

    public void render(){
        for (Box b : g.boxes) {
            g.batch.draw(g.textures.getTexture("box"),
                    g.centerx + b.position.x - g.player.position.x - g.currentTexture.getWidth() / 2,
                    g.centery + b.position.y - g.player.position.y - g.currentTexture.getHeight() / 2);
        }

    }
}
