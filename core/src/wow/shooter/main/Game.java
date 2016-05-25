package wow.shooter.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import wow.shooter.logic.SimpleMenuHandler;
import wow.shooter.managers.DataStore;
import wow.shooter.managers.Drawer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import java.util.ConcurrentModificationException;
import wow.shooter.logic.SimpleMenuHandler;
import wow.shooter.managers.DataStore;
import wow.shooter.managers.Drawer;
import wow.shooter.managers.GameManager;

public class Game implements ApplicationListener, InputProcessor {
    private DataStore data = new DataStore();
    private GameManager manager;
    private Drawer drawer;
    private SimpleMenuHandler menu;

    public Game() {
        this.manager = new GameManager(data);
        this.drawer = new Drawer(data);
        this.menu = new SimpleMenuHandler(manager);
    }

    public void create() {
        this.data.setSreenSize((float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        this.drawer.create();
        Gdx.input.setInputProcessor(this);
    }

    public void dispose() {
        this.drawer.dispose();
        this.manager.close();
    }

    public void render() {


        try {
            this.manager.updateGame(Gdx.graphics.getDeltaTime());
            Gdx.gl.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
            Gdx.gl.glClear(16384);
            this.drawer.batch.begin();

            this.drawer.drawPlayers();
            this.drawer.drawBoxes();
            this.drawer.drawBullets();

            if (!this.menu.connected) {
                // this.menu.drawTextDataDialog();
                //this.drawer.drawMenuBox();
            }
            this.drawer.batch.end();
        } catch (ConcurrentModificationException var2) {
            ;
        }

    }

    public void resize(int width, int height) {
    }

    public void pause() {
    }

    public void resume() {
    }

    public boolean keyDown(int keycode) {
        System.out.println(keycode);
        this.manager.shootEvent();
        return true;
    }

    public boolean keyUp(int keycode) {
        return false;
    }

    public boolean keyTyped(char character) {
        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        this.manager.touchDownEvent(screenX, Gdx.graphics.getHeight() - screenY);
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        this.manager.moveEvent(screenX, Gdx.graphics.getHeight() - screenY);
        return false;
    }

    public boolean scrolled(int amount) {
        return false;
    }

}