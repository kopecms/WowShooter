package wow.shooter.drawing;

import components.entities.Actor;
import components.entities.Enemy;
import components.data.GameData;

/**
 * Created by kopec on 2016-04-14.
 */
public class Actors {
    private GameData g = GameData.getInstance();;

    int namePosition = 30;

    public void render(){
        drawPlayer();
        drawEnemy();
    }

    public void drawPlayer(){
        g.currentTexture = g.textures.getTexture("player");
        g.batch.draw(g.currentTexture,g.centerx - g.currentTexture.getWidth()/2,g.centery- g.currentTexture.getHeight()/2);
        drawTextData(g.player);
    }
    public void drawEnemy(){
        for(Enemy e: g.enemies){
            g.currentTexture = g.textures.getTexture("player2");
            g.batch.draw(g.currentTexture,
                    g.centerx + e.position.x - g.player.position.x - g.currentTexture.getWidth()/2,
                    g.centery + e.position.y - g.player.position.y - g.currentTexture.getHeight()/2);
            drawTextData(e);
        }
    }

    public void drawTextData(Actor actor){
        if(actor.name != null) {
            g.font.draw(g.batch, actor.name, g.centerx - g.currentTexture.getWidth() / 2,
                    g.centery + g.currentTexture.getHeight() / 2 + namePosition);
            g.font.draw(g.batch,Integer.toString(actor.getHealth()),g.centerx - g.currentTexture.getWidth() / 2,
                    g.centery + g.currentTexture.getHeight() / 2 + namePosition-15);
        }
    }
}
