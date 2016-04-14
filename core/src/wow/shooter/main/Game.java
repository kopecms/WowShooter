package wow.shooter.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import components.entities.Bullet;
import components.entities.Player;
import components.data.GameData;
import wow.shooter.logic.controllers.Keyboard;
import wow.shooter.logic.controllers.Mouse;
import wow.shooter.drawing.Actors;
import wow.shooter.drawing.Bullets;
import wow.shooter.drawing.World;
import wow.shooter.logic.handlers.Client;
import wow.shooter.managers.*;

import static components.data.functions.DataSetters.*;

public class Game implements ApplicationListener , InputProcessor {

	public GameData g = new GameData();
	public GameManager manager =  new GameManager();

	private Actors actors = new Actors();
	private World world = new World();
	private Bullets bullets = new Bullets();

	private Mouse mouse = new Mouse();
	private Keyboard keyboard = new Keyboard();

	@Override
	public void create() {
		g.screenWidth = Gdx.graphics.getWidth();
		g.screenHeight = Gdx.graphics.getHeight();
		g.centerx = g.screenWidth / 2;
		g.centery = g.screenHeight / 2;

		manager.start();
		Gdx.input.setInputProcessor(this);
	}
	@Override
	public void dispose() {
		g.batch.dispose();
		g.font.dispose();
		g.textures.dipose();
		manager.close();
	}

	@Override
	public void render() {
		manager.updateGame(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		g.batch.begin();

		actors.render();
		world.render();
		bullets.render();

		g.batch.end();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public boolean keyDown(int keycode) {
		keyboard.keyDown(keycode);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		mouse.getTouch(screenX,screenY);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mouse.getMove(screenX, screenY);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}