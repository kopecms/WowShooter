package wow.shooter.logic.controllers;

import com.badlogic.gdx.math.Vector2;
import components.data.GameData;
import components.entities.Bullet;
import wow.shooter.logic.handlers.HandleOutput;

import static components.data.functions.DataSetters.setBulletData;

/**
 * Created by kopec on 2016-03-23.
 */
public class Keyboard {
    private GameData g = GameData.getInstance();
    private HandleOutput o = HandleOutput.getInstance();

    public void keyDown(int keyCode){
        Vector2 velocity = new Vector2(g.mouse.x-g.centerx,g.mouse.y-g.centery).setLength(Bullet.speed);
        o.sendBulletData(velocity);
        g.bullets.addElement(g.player.shoot(velocity));
    }
}
