package wow.shooter.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import components.agents.Enemy;
import components.agents.Player;
import enums.State;
import wow.shooter.controllers.Mouse;
import wow.shooter.managers.*;


public class Game implements ApplicationListener , InputProcessor {
	private SpriteBatch batch;
	private BitmapFont font;

	private Client client;
	private GameManager manager = new GameManager();;
	private TextureManager textures ;

	Mouse mouse = new Mouse();

	private Player player;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.RED);

		textures = new TextureManager();

		manager.start();

		player = manager.getPlayer();

		client = new Client("localhost", 5055, manager);
		manager.setClient(client);

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(textures.getTexture("player"),player.getx(),player.gety());
		for(Enemy e: manager.objects.enemies){
			batch.draw(textures.getTexture("player2"),e.getx(),e.gety());
		}
		font.draw(batch, "Hello World", 200, 200);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public boolean keyDown(int keycode) {
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		System.out.println("siemqqqa"); return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

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
		System.out.println(screenX); return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}