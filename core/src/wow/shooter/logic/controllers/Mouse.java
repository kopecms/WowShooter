package wow.shooter.logic.controllers;

import com.badlogic.gdx.Gdx;
import components.data.GameData;
public class Mouse {
    private GameData g = GameData.getInstance();

    public int x;
    public int y;


    public void getTouch(int screenX, int screenY){
        x = screenX;
        y = Gdx.graphics.getHeight() - screenY;

        // jesli akurat to nie przycisk
        if(true) {
            g.player.destination.set(g.player.position.x + x - g.centerx,
                    g.player.position.y + y - g.centery);
            client.send(setDestinationData(player.position.x + mouse.x - centerx,
                    player.position.y + mouse.y - centery));
        }
    }

    public void getMove(int screenX, int screenY){
        x = screenX;
        y = Gdx.graphics.getHeight() - screenY;
    }

}