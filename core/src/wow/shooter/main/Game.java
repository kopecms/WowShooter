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
import components.entities.Box;
import components.entities.Bullet;
import components.entities.Enemy;
import components.entities.Player;
import components.funstore.DataStore;
import wow.shooter.controllers.Mouse;
import wow.shooter.logic.Client;
import wow.shooter.managers.*;
import java.util.TimerTask;
import java.util.Timer;
import static components.funstore.DataSetter.*;

public class Game implements ApplicationListener , InputProcessor {

	private int screenWidth;
	private int screenHeight;
	float centerx;
	float centery;

	private SpriteBatch batch;
	private BitmapFont font;
	private Texture currentTexture;

	private Client client;
	private GameManager manager;
	private DataStore data;
	private TextureManager textures ;

	Mouse mouse = new Mouse();

	private Player player;

	private int namePosition = 30;
	@Override
	public void create() {
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		centerx = screenWidth / 2;
		centery = screenHeight / 2;

		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.RED);
		data = new DataStore();
		data.centerx = centerx;
		data.centery = centery;
		textures = new TextureManager();

		manager = new GameManager(data);

		player = manager.getPlayer();

		client = new Client("localhost", 5055, manager);
		client.send(new byte[]{13});
		client.send(setNameData("Kopciu"));
		player.name = "Kopciu";
		manager.setClient(client);

		manager.start();
		Gdx.input.setInputProcessor(this);

	}
	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		textures.dipose();

		manager.close();
	}

	@Override
	public void render() {
		manager.updateGame(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		// rysowanie gracza

		currentTexture = textures.getTexture("player");
		batch.draw(currentTexture,centerx - currentTexture.getWidth()/2,centery- currentTexture.getHeight()/2);
		if(player.name != null) {
			font.draw(batch, player.name, centerx - currentTexture.getWidth() / 2,
					centery + currentTexture.getHeight() / 2 + namePosition);
			font.draw(batch,Integer.toString(player.getHealth()),centerx - currentTexture.getWidth() / 2,
					centery + currentTexture.getHeight() / 2 + namePosition-15);
		}
		// rysowanie reszty
		for(Enemy e: data.enemies){
			currentTexture = textures.getTexture("player2");
			if(e.name != null) {
				font.draw(batch, e.name, centerx + e.position.x - player.position.x - currentTexture.getWidth()/2,
						centery + e.position.y - player.position.y + currentTexture.getHeight()/2 + namePosition);
				font.draw(batch,Integer.toString(e.getHealth()), centerx + e.position.x - player.position.x - currentTexture.getWidth()/2,
						centery + e.position.y - player.position.y + currentTexture.getHeight()/2 + namePosition-15);
			}
			batch.draw(currentTexture,
					centerx + e.position.x - player.position.x - currentTexture.getWidth()/2,
					centery + e.position.y - player.position.y - currentTexture.getHeight()/2);
		}
		try {
			for (Box b : data.boxes) {
				batch.draw(textures.getTexture("box"),
						centerx + b.position.x - player.position.x - currentTexture.getWidth() / 2,
						centery + b.position.y - player.position.y - currentTexture.getHeight() / 2);
			}
		} catch (java.util.ConcurrentModificationException e)
		{} // do ogarniecia

		for(Bullet b: data.bullets){
			batch.draw(textures.getTexture("bullet"),
					centerx + b.position.x - player.position.x,
					centery + b.position.y - player.position.y);
		}
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
		Vector2 velocity = new Vector2(mouse.x-centerx,mouse.y-centery).setLength(Bullet.speed);
		client.send(setBulletData(velocity));
		data.bullets.addElement(player.shoot(velocity));
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
		mouse.x = screenX;
		mouse.y = Gdx.graphics.getHeight() - screenY;

		// jesli akurat to nie przycisk
		if(true) {
			player.destination.set(player.position.x + mouse.x - centerx,
					player.position.y + mouse.y - centery);
			client.send(setDestinationData(player.position.x + mouse.x - centerx,
					player.position.y + mouse.y - centery));
		}
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
		mouse.x = screenX;
		mouse.y = Gdx.graphics.getHeight() - screenY;
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}