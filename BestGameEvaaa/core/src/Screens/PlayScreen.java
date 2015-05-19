package Screens;

import java.util.ArrayList;

import Objects.Enemy;
import Objects.Fort;
import Objects.Player;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.BestGameEvaa;

public class PlayScreen implements Screen, InputProcessor, ApplicationListener {

	SpriteBatch batch;
	Texture img, background;
	BestGameEvaa game;

	Player player;
	Fort fort;

	ArrayList<Enemy> enemys;

	public PlayScreen(BestGameEvaa game) {
		this.game = game;
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		background = new Texture("background.png");

		fort = new Fort(new Sprite(new Texture("fort.png"), 0, 0, 262, 327));
		player = new Player(new Sprite(new Texture("player.png")));
		enemys = new ArrayList<Enemy>();
		enemys.add(new Enemy(new Sprite(new Texture("enemy.png")), "Name", 100,
				10, 10, 10, 150, 1000));
		enemys.add(new Enemy(new Sprite(new Texture("enemy.png")), "Name", 100,
				10, 10, 10, 60, 1000));
		enemys.add(new Enemy(new Sprite(new Texture("enemy.png")), "Name", 100,
				10, 10, 10, 90, 1000));
		for (int i = 0; i < enemys.size(); i++) {
			enemys.get(i).setxPosition(100 * (i + 1));
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stu
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(player, fort.getX() + fort.getWidth()
				- (player.getWidth() + 10),
				(fort.getOriginY() + fort.getHeight()) - 10);
		batch.draw(fort, 0, (Gdx.graphics.getHeight() / 100) * 26);

		for (int i = 0; i < enemys.size(); i++) {
			batch.draw(enemys.get(i), Gdx.graphics.getWidth()
					- enemys.get(i).getxPosition(),
					(Gdx.graphics.getHeight() / 100) * 26);
		}

		batch.end();
		updateAtackMode();
		moveEnemys(delta);
	}

	private void updateAtackMode() {
		for (int i = 0; i < enemys.size(); i++) {
			if (!enemys.get(i).isAttack) {
				float position = enemys.get(i).getxPosition();
				if (position > Gdx.graphics.getWidth() - fort.getWidth()) {
					enemys.get(i).isAttack = true;
				}
			}
		}

	}

	private void moveEnemys(float delta) {
		for (int i = 0; i < enemys.size(); i++) {
			if (!enemys.get(i).isAttack) {
				float oldPosition = enemys.get(i).getxPosition();
				int speed = enemys.get(i).getWalkSpeed();
				enemys.get(i).setxPosition(
						(int) ((delta * speed) + oldPosition));
			}
		}

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void create() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
