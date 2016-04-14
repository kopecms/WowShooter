package wow.shooter.logic.handlers;

import com.badlogic.gdx.math.Vector2;
import components.data.GameData;
import components.entities.Player;

import static components.data.functions.DataSetters.*;

/**
 * Created by kopec on 2016-04-14.
 */
public class HandleOutput {
    GameData g = GameData.getInstance();
    Client client = g.client;
    Player player = g.player;
    private static HandleOutput holder;
    public HandleOutput(){
        holder = this;
    }

    public void sendPositionCorrection() {
        client.send(setErrorCorrection(player.id, player.position, player.destination));
    }
    public void sendBulletData(Vector2 velocity) {
        client.send(setBulletData(velocity));
    }
    public void sendDestination(float x,float y){
        client.send(setDestinationData(x,y));
    }
    public void sendCollision(Vector2 v){
        client.send(setCollisionData(player.id,v));
    }
    public void sendHit(){
        client.send(setHitData(player.id,player.getHealth()));
    }

    public static HandleOutput getInstance() { return holder;}
}
