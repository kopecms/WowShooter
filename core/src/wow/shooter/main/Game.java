package wow.shooter.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import components.Enemy;
import components.Player;
import wow.shooter.managers.*;
public class Game implements ApplicationListener {
	private SpriteBatch batch;
	private BitmapFont font;

	private Client client;
	private GameManager manager;
	private TextureManager textures;

	private Player player;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.RED);

		manager = new GameManager();
		player = manager.getPlayer();
		textures = new TextureManager();

		client = new Client("localhost", 5055, manager);
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
			batch.draw(textures.getTexture("player"),e.getx(),e.gety());
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
}