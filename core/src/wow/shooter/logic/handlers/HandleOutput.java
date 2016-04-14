package wow.shooter.logic.handlers;

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

    public void sendPositionCorrection() {
        client.send(setErrorCorrection(player.id, player.position, player.destination));
    }
}
