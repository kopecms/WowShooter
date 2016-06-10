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
        manager = new GameManager(data);
        drawer = new Drawer(data);
        menu = new SimpleMenuHandler(manager);
        data.menu = menu;
    }

    public void create() {
        data.setScreenSize((float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        drawer.create();
        Gdx.input.setInputProcessor(this);
    }

    public void dispose() {
        drawer.dispose();
        manager.close();
    }

    public void render() {
        try {
            manager.updateGame(Gdx.graphics.getDeltaTime());
            Gdx.gl.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
            Gdx.gl.glClear(16384);
            drawer.batch.begin();

            drawer.drawGame();

            drawer.drawConnectionState(manager.connected);
            if (!manager.connected) {
                menu.drawTextDataDialogs();
            }
            drawer.batch.end();
        } catch (ConcurrentModificationException var2) {
            drawer.batch.end();
        }

    }

    public void resize(int width, int height) {
    }

    public void pause() {
    }

    public void resume() {
    }

    public boolean keyDown(int keycode) {
        manager.handleKeyboardInput(keycode);
        return true;
    }

    public boolean keyUp(int keycode) {
        return false;
    }

    public boolean keyTyped(char character) {
        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        manager.touchDownEvent(screenX, Gdx.graphics.getHeight() - screenY);
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        manager.mouseEvent(screenX, Gdx.graphics.getHeight() - screenY);
        return false;
    }

    public boolean scrolled(int amount) {
        return false;
    }

}