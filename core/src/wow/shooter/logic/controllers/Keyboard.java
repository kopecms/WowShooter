package wow.shooter.logic.controllers;

import com.badlogic.gdx.math.Vector2;
import components.data.GameData;
import components.entities.Bullet;

import static components.data.functions.DataSetters.setBulletData;

/**
 * Created by kopec on 2016-03-23.
 */
public class Keyboard {
    private GameData g = GameData.getInstance();

    public void keyDown(int keyCode){
        Vector2 velocity = new Vector2(mouse.x-centerx,mouse.y-centery).setLength(Bullet.speed);
        client.send(setBulletData(velocity));
        data.bullets.addElement(player.shoot(velocity));
    }
}
