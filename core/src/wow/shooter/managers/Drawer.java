package wow.shooter.managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import wow.shooter.entities.Box;
import wow.shooter.entities.Bullet;
import wow.shooter.entities.Enemy;

/**
 * Created by kopec on 2016-05-17.
 */
public class Drawer {
    public SpriteBatch batch;
    public BitmapFont font;
    public TextureManager textures;

    public Texture currentTexture;

    public DataStore data;

    public int namePosition = 30;
    public Drawer(DataStore d){
        data = d;
    }
    public void create(){
        batch = new SpriteBatch();;
        font = new BitmapFont();
        textures = new TextureManager();
        font.setColor(Color.RED);
    }
    public void drawBullets(){
        for(Bullet b: data.bullets){
            batch.draw(textures.getTexture("bullet"),
                    data.centerx + b.position.x - data.player.position.x,
                    data.centery + b.position.y - data.player.position.y);
        }
    }

    public void drawMenuBox(){
        batch.draw(textures.getTexture("menuBox"),data.centerx - 200, data.centery - 200);
        font.draw(batch, "SIEMA",data.centerx-100,data.centery+100);
    }

    public void drawPlayers(){
        if(!data.player.dead) {
            currentTexture = textures.getTexture("player");
            batch.draw(currentTexture, data.centerx - currentTexture.getWidth() / 2, data.centery - currentTexture.getHeight() / 2);
            if (data.player.name != null) {
                font.draw(batch, data.player.name, data.centerx - currentTexture.getWidth() / 2,
                        data.centery + currentTexture.getHeight() / 2 + namePosition);
                font.draw(batch, Integer.toString(data.player.getHealth()),data.centerx - currentTexture.getWidth() / 2,
                        data.centery + currentTexture.getHeight() / 2 + namePosition - 15);
            }
        }
        // rysowanie reszty
        for(Enemy e: data.enemies){
            currentTexture = textures.getTexture("player2");
            if(e.name != null) {
                font.draw(batch, e.name, data.centerx + e.position.x - data.player.position.x - currentTexture.getWidth()/2,
                        data.centery + e.position.y - data.player.position.y + currentTexture.getHeight()/2 + namePosition);
                font.draw(batch,Integer.toString(e.getHealth()),data.centerx + e.position.x - data.player.position.x - currentTexture.getWidth()/2,
                        data.centery + e.position.y - data.player.position.y + currentTexture.getHeight()/2 + namePosition-15);
            }
            batch.draw(currentTexture,
                    data.centerx + e.position.x - data.player.position.x - currentTexture.getWidth()/2,
                    data.centery + e.position.y - data.player.position.y - currentTexture.getHeight()/2);
        }
    }
    public void drawBoxes(){
            for (Box b : data.boxes) {
                batch.draw(textures.getTexture("box"),
                        data.centerx + b.position.x - data.player.position.x - currentTexture.getWidth() / 2,
                        data.centery + b.position.y - data.player.position.y - currentTexture.getHeight() / 2);
            }
    }


    public void dispose(){
        batch.dispose();
        font.dispose();
        textures.dipose();
    }

}
