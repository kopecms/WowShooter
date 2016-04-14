package components.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import components.entities.*;
import components.entities.World;
import wow.shooter.drawing.*;
import wow.shooter.logic.handlers.Client;
import wow.shooter.logic.updating.Bullets;
import wow.shooter.managers.TextureManager;

import java.util.Timer;
import java.util.Vector;

/**
 * Created by kopec on 2016-03-22.
 */

public class GameData {
    public Client client;

    public SpriteBatch batch;
    public BitmapFont font;
    public Texture currentTexture;
    public TextureManager textures = new TextureManager();

    public Player player = new Player(0,0,0);


    public Bullets bulletLogic;;
    public int mausex;
    public int mausey;

    // flagi bulletow

    public boolean tooFar = true;
    public boolean hit = false;

    boolean getState = false;

    public Thread.State gameState;
    // trzeba to ogarnac ladniej
    public int objectsSize = 100;



    private World map = new World();
    public Vector<Player> players = new Vector<Player>();
    public Vector<Enemy> enemies = new Vector<Enemy>();
    public Vector<Box> boxes = new Vector<Box>();
    public Vector<Bullet> bullets = new Vector<Bullet>();

    public float centerx;
    public float centery;
    public int screenWidth;
    public int screenHeight;

    private static GameData holder;

    public GameData(){
        holder = this;

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
    }

    public void setBoxes(){
        boxes = new Vector<Box>(map.getBoxes());
    }

    public static GameData getInstance() {return holder;}
}
