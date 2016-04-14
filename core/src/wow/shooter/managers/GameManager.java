package wow.shooter.managers;

import com.badlogic.gdx.math.Vector2;
import components.data.Massage;
import components.entities.Box;
import components.entities.Bullet;
import components.entities.Player;
import components.data.enums.DataType;
import components.data.enums.ObjectType;
import components.data.GameData;
import components.data.functions.ByteFunctions;
import static components.data.functions.ByteFunctions.stringFromBytes;
import components.entities.Enemy;
import jdk.internal.org.objectweb.asm.Handle;
import wow.shooter.logic.controllers.Keyboard;
import wow.shooter.logic.controllers.Mouse;
import wow.shooter.logic.handlers.HandleMassage;
import wow.shooter.logic.handlers.HandleOutput;
import wow.shooter.logic.updating.Actors;
import wow.shooter.logic.updating.Bullets;
import wow.shooter.logic.handlers.Client;

import java.util.Timer;
import java.util.TimerTask;

import static components.data.functions.DataSetters.*;
/**
 * Created by kopec on 2016-03-22.
 */
public class GameManager extends Thread{
    private Client client;

    private GameData g = GameData.getInstance();
    private HandleMassage m = new HandleMassage();
    private HandleOutput o = new HandleOutput();

    private Timer t = new Timer();

    private Actors actors = new Actors();
    private Bullets bullets = new Bullets();

    public GameManager() {
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if( client!= null)
                    o.sendPositionCorrection();
            }
        }, 0, 1000);
    }

    public void updateGame(float dt){
        actors.update(dt);
        bullets.update(dt);
    }


    public void handleData(Massage massage){
        DataType dataType = massage.getType();
        System.out.println(dataType);

        switch(dataType){
            case NAME: m.setPlayerName(massage);
                break;
            case MSG:
                break;
            case OBJECT:
                break;
            case POSITION:
                break;
            case ID:
                break;
            case OUT:
                break;
            case STATE:
                break;
            case KILL:
                break;
            case HIT:
                break;
            case MOVE:
                break;
            case SHOOT:
                break;
            case GETSTATE:
                break;
            case STOP:
                break;
            case COLLISION:
                break;
            case LAGERRORCORRECTION:
                break;
        }

    }

    public void close(){
        t.cancel();
    }

}

