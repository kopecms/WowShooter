package wow.shooter.managers;
import com.badlogic.gdx.math.Vector2;
import wow.shooter.entities.Bullet;
import wow.shooter.entities.Player;
import wow.shooter.logic.Client;
import wow.shooter.logic.Update;


import functions.DataSetter;
import enums.DataType;
import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;

import wow.shooter.logic.DataHandler;

import static functions.DataSetter.setCollisionData;
import static functions.DataSetter.setHitData;

public class GameManager extends Thread {
    private Player player = new Player(0, 0, 0);
    private Client client;
    private DataStore data;
    private Mouse mouse = new Mouse();

    public String playerName = "";
    public String serverAddr = "localhost";
    public String port = "5055";

    Timer t = new Timer();

    public GameManager(DataStore d) {

        data = d;
        client = new Client("localhost", 5055, this);
        client.send(new byte[]{(byte)DataType.GETSTATE.getId()});
        client.send(DataSetter.setNameData("Kopciu"));
        player.name = "Kopciu";

        start();
        startErrorCorrector();
    }

    public boolean createConnection() {
        client = new Client(serverAddr, Integer.parseInt(port), this);
        client.send(new byte[]{(byte)DataType.GETSTATE.getId()});
        client.send(DataSetter.setNameData(playerName));
        player.name = playerName;

        if(client.connected)
            return true;
        return false;
    }

    public void shootEvent() {
        data.bullets.addElement(player.shoot(mouse.getMouse().setLength(Bullet.speed)));
        client.send(DataSetter.setBulletData(mouse.getMouse().setLength(Bullet.speed)));
    }

    public void touchDownEvent(int x, int y) {
        player.destination.set(player.position.x + (float)x - data.centerx, player.position.y + (float)y - data.centery);
        client.send(DataSetter.setDestinationData(player.position.x + (float)x - data.centerx, player.position.y + (float)y - data.centery));
    }

    public void moveEvent(int x, int y) {
        mouse.setMouse(x, y);
    }

    public void updateGame(float dt) {
        try {
            Update.updatePlayer(dt, player);
            Update.updateEnemies(dt, data.enemies);
            if(Update.collisions(player, data.boxes, data.enemies))
                client.send(setCollisionData(player.id, new Vector2(player.position.x + player.velocity.y,
                        player.position.y - player.velocity.x)));
            if(Update.updateBullets(dt,player, data.enemies, data.boxes, data.bullets))
                client.send(setHitData(player.id,player.getHealth()));
        } catch (ConcurrentModificationException var3) {
            ;
        }

    }

    public DataType handleData(byte[] recv) {
        DataType dataType = DataType.fromInt(recv[0]);
        switch(dataType) {
            case NAME:
                DataHandler.handleName(recv, data.enemies);
                break;
            case LAGERRORCORRECTION:
                DataHandler.handleLag(recv, data.enemies);
                break;
            case COLLISION:
                DataHandler.handleCollison(recv, data.enemies);
                break;
            case HIT:
                DataHandler.handleHit(recv, data.enemies, player);
                break;
            case SHOOT:
                DataHandler.handleShoot(recv, data.enemies, data.bullets);
                break;
            case POSITION:
                DataHandler.handlePosition(recv, player);
                break;
            case MOVE:
                DataHandler.handleMove(recv, data.enemies);
                break;
            case OBJECT:
                DataHandler.handleObject(recv, data.enemies, data.boxes);
                break;
            case ID:
                player.id = recv[1];
                break;
            case DISCONNECTED:
                DataHandler.disconnection(recv, data.enemies);
            case NONE:
        }
        return dataType;
    }


    public void close() {
        t.cancel();
    }

    public void startErrorCorrector() {
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(client != null) {
                    client.send(DataSetter.setErrorCorrection(player.id, player.position, player.destination));
                }

            }
        }, 0L, 1000L);
    }

    private class Mouse {
        int x;
        int y;

        private Mouse() {
        }

        public void setMouse(int a, int b) {
            x = a;
            y = b;
        }

        public Vector2 getMouse() {
            return new Vector2((float)x - data.centerx, (float)y - data.centery);
        }
    }
}
