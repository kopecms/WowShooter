package wow.shooter.managers;
import com.badlogic.gdx.math.Vector2;
import wow.shooter.entities.Bullet;
import wow.shooter.entities.Player;
import wow.shooter.logic.BulletLogic;
import wow.shooter.logic.Client;
import wow.shooter.logic.Update;
import wow.shooter.managers.DataStore;
import java.util.*;


import com.badlogic.gdx.math.Vector2;
import components.DataSetter;
import enums.DataType;
import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;
import wow.shooter.entities.Bullet;
import wow.shooter.entities.Player;
import wow.shooter.logic.BulletLogic;
import wow.shooter.logic.Client;
import wow.shooter.logic.DataHandler;
import wow.shooter.logic.Update;
import wow.shooter.managers.DataStore;

public class GameManager extends Thread {
    private Player player = new Player(0, 0.0F, 0.0F);
    private Client client;
    private DataStore data;
    private BulletLogic bulletLogic;
    private GameManager.Mouse mouse = new GameManager.Mouse();
    public String playerName;
    public String serverAddr;
    public String port;
    Timer t = new Timer();

    public GameManager(DataStore data) {

        this.data = data;
        this.bulletLogic = new BulletLogic(data, this.player);
        this.player = this.getPlayer();
        data.player = this.getPlayer();
        this.client = new Client("localhost", 5055, this);
        this.client.send(new byte[]{(byte)13});
        this.client.send(DataSetter.setNameData("Kopciu"));
        this.player.name = "Kopciu";
        this.setClient(this.client);
        this.start();
        this.startErrorCorrector();
    }

    public boolean createConnection() {
        return false;
    }

    public void shootEvent() {
        this.data.bullets.addElement(this.player.shoot(this.mouse.getMouse().setLength(Bullet.speed)));
        this.client.send(DataSetter.setBulletData(this.mouse.getMouse().setLength(Bullet.speed)));
    }

    public void touchDownEvent(int x, int y) {
        this.player.destination.set(this.player.position.x + (float)x - this.data.centerx, this.player.position.y + (float)y - this.data.centery);
        this.client.send(DataSetter.setDestinationData(this.player.position.x + (float)x - this.data.centerx, this.player.position.y + (float)y - this.data.centery));
    }

    public void moveEvent(int x, int y) {
        this.mouse.setMouse(x, y);
    }

    public void updateGame(float dt) {
        try {
            if(!this.player.dead) {
                Update.updatePlayer(dt, this.player);
            }

            Update.updateEnemies(dt, this.data.enemies);
            Update.updateBullets(dt, this.client, this.data.bullets, this.player, this.bulletLogic);
            Update.collisions(this.player, this.client, 100, this.data.boxes, this.data.enemies);
        } catch (ConcurrentModificationException var3) {
            ;
        }

    }

    public void handleData(byte[] recv) {
        DataType dataType = DataType.fromInt(recv[0]);
        switch(dataType) {
            case NAME:
                DataHandler.handleName(recv, this.data.enemies);
                break;
            case LAGERRORCORRECTION:
                DataHandler.handleLag(recv, this.data.enemies);
                break;
            case COLLISION:
                DataHandler.handleCollison(recv, this.data.enemies);
                break;
            case HIT:
                DataHandler.handleHit(recv, this.data.enemies, this.player);
                break;
            case SHOOT:
                DataHandler.handleShoot(recv, this.data.enemies, this.data.bullets);
                break;
            case POSITION:
                DataHandler.handlePosition(recv, this.player);
                break;
            case MOVE:
                DataHandler.handleMove(recv, this.data.enemies);
                break;
            case OBJECT:
                DataHandler.handleObject(recv, this.data.enemies, this.data.boxes);
                break;
            case ID:
                this.player.id = recv[1];
            case NONE:
        }

    }


    public Player getPlayer() {
        return this.player;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public void close() {
        this.t.cancel();
    }

    public void startErrorCorrector() {
        this.t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(GameManager.this.client != null) {
                    GameManager.this.client.send(DataSetter.setErrorCorrection(GameManager.this.player.id, GameManager.this.player.position, GameManager.this.player.destination));
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
            this.x = a;
            this.y = b;
        }

        public Vector2 getMouse() {
            return new Vector2((float)this.x - GameManager.this.data.centerx, (float)this.y - GameManager.this.data.centery);
        }
    }
}
