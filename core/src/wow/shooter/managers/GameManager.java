package wow.shooter.managers;
import com.badlogic.gdx.math.Vector2;
import wow.shooter.entities.Bullet;
import wow.shooter.entities.Player;
import wow.shooter.logic.Client;
import wow.shooter.logic.UpdateGame;


import functions.DataSetter;
import enums.DataType;

import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;

import wow.shooter.logic.IncomingDataHandler;

import static functions.DataSetter.setCollisionData;
import static functions.DataSetter.setHitData;

public class GameManager extends Thread {
    public Player player = new Player(0, 0, 0);
    public Client client;
    private DataStore data;
    public Mouse mouse = new Mouse();

    public String playerName = "";
    public String serverAddr = "localhost";
    public String port = "5055";

    public boolean connected = false;
    Timer t = new Timer();

    public GameManager(DataStore d) {
        data = d;
        data.player = player;
        client = new Client();
        client.send(DataSetter.setNameData("No name"));
        player.name = "No name";

        startErrorCorrector();
    }

    public boolean createConnection() {
        try {
            client.connect(serverAddr, Integer.parseInt(port), this);
            client.send(new byte[]{(byte) DataType.GETSTATE.getId()});
            client.send(DataSetter.setNameData(playerName));
            player.name = playerName;
            connected = true;
        } catch (NumberFormatException e){
            return false;
        }
        return true;

    }
    public void disconnect(){
        connected = false;
        client.disconnect();
    }

    public void shootEvent() {
        data.bullets.addElement(player.shoot(mouse.getMouse().setLength(Bullet.speed)));
        client.send(DataSetter.setBulletData(mouse.getMouse().setLength(Bullet.speed)));
    }

    public void touchDownEvent(int x, int y) {
        player.destination.set(player.position.x + (float)x - data.centerx, player.position.y + (float)y - data.centery);
        client.send(DataSetter.setDestinationData(player.position.x + (float)x - data.centerx, player.position.y + (float)y - data.centery));
    }

    public void mouseEvent(int x, int y) {
        mouse.setMouse(x, y);
    }

    public void updateGame(float dt) {
        try {
            if(UpdateGame.updatePlayer(dt, player, data.enemies))
                client.send(new byte[]{(byte) DataType.KILL.getId(), (byte) player.id});

            UpdateGame.updateEnemies(dt, data.enemies);

            if(UpdateGame.collisions(player, data.boxes, data.enemies))
                client.send(setCollisionData(player.id, new Vector2(player.position.x + player.velocity.y,player.position.y - player.velocity.x)));

            if(UpdateGame.updateBullets(dt,player, data.enemies, data.boxes, data.bullets))
                client.send(setHitData(player.id,player.getHealth()));
        } catch (ConcurrentModificationException var3) {
            ;
        }

    }

    public DataType handleData(byte[] recv) {
        DataType dataType = DataType.fromInt(recv[0]);
        switch(dataType) {
            case NAME:
                IncomingDataHandler.handleName(recv, data.enemies); break;
            case LAGERRORCORRECTION:
                IncomingDataHandler.handleLag(recv, data.enemies); break;
            case COLLISION:
                IncomingDataHandler.handleCollison(recv, data.enemies); break;
            case HIT:
                IncomingDataHandler.handleHit(recv, data.enemies, player); break;
            case SHOOT:
                IncomingDataHandler.handleShoot(recv, data.enemies, data.bullets); break;
            case POSITION:
                IncomingDataHandler.handlePosition(recv, player); break;
            case MOVE:
                IncomingDataHandler.handleMove(recv, data.enemies); break;
            case OBJECT:
                IncomingDataHandler.handleObject(recv, data.enemies, data.boxes); break;
            case ID:
                player.id = recv[1]; break;
            case DISCONNECTED:
                IncomingDataHandler.disconnection(recv, data.enemies); break;
            case KILL:
                IncomingDataHandler.addScore(recv, player, data.enemies); break;
            case RESET:
                IncomingDataHandler.reset(recv, player, data.enemies); break;
            case NONE:
                break;
        }
        return dataType;
    }

    public int handleKeyboardInput(int keycode){
        switch(keycode) {
            case 131:
                handleConnection(); break;
            case 62:
                shootEvent(); break;
            case 66:
                if(player.dead)
                    client.send(new byte[]{(byte) DataType.RESET.getId(), (byte)player.id}); break;
        }
        return keycode;
    }

    public void handleConnection(){
        if(data.menu != null)
            data.menu.reset();
        if (connected) {
            disconnect();
            data.clear();
        }
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
        }, 0, 1000);
    }

    public class Mouse {
        int x; int y;
        public void setMouse(int a, int b) { x = a;y = b; }
        public Vector2 getMouse() {
            return new Vector2((float)x - data.centerx, (float)y - data.centery);
        }
    }
}
