package wow.shooter.logic.controllers;

import com.badlogic.gdx.Gdx;
import components.data.GameData;
import wow.shooter.logic.handlers.HandleOutput;

public class Mouse {
    private GameData g = GameData.getInstance();
    private HandleOutput o = HandleOutput.getInstance();
    public int x;
    public int y;


    public void getTouch(int screenX, int screenY){
        x = screenX;
        y = Gdx.graphics.getHeight() - screenY;

        if(true){
            g.player.destination.set(g.player.position.x + x - g.centerx, g.player.position.y + y - g.centery);
            o.sendDestination(g.player.position.x + g.mouse.x - g.centerx,
                    g.player.position.y + g.mouse.y - g.centery);
        }

    }

    public void getMove(int screenX, int screenY){
        x = screenX;
        y = Gdx.graphics.getHeight() - screenY;
    }

}