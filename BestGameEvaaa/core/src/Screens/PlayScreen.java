package Screens;

import java.time.LocalTime;
import java.util.ArrayList;

import Objects.Enemy;
import Objects.Fort;
import Objects.Player;
import Objects.Weapon;

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
	Sprite hpBar, hpBackground;
	
	Player player;
	Fort fort;
	Weapon weapon;

	ArrayList<Enemy> enemys;
	
	float tangens;
	LocalTime clickDelay = LocalTime.now();

	public PlayScreen(BestGameEvaa game) {
		this.game = game;
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		background = new Texture("background.png");

		hpBar = new Sprite(new Texture("hpBar.png"), 0, 0, 398, 18);
		hpBackground = new Sprite(new Texture("hpBackground.png"), 0, 0, 402, 20);
		
		//hpBar.setOrigin(0, 0);
		
		//hpBackground.setOrigin(0, 0);
		
		
		fort = new Fort(new Sprite(new Texture("fort.png"), 0, 0, 262, 327),200);
		fort.hp = 200;
		
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
		weapon = new Weapon(new Sprite(new Texture("weapon.png")));
		weapon.setOrigin(5, weapon.getHeight()/2);
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
		Gdx.input.setInputProcessor(Gdx.input.getInputProcessor());
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		
		fort.hp=fort.hp-1;
		
		
		batch.begin();
		
		batch.draw(background, 0, 0);
		batch.draw(hpBackground, 25, 25);
		hpBar.setPosition(27, 26);
		hpBar.setOrigin(0,0);
		hpBar.setScale((float)((float)fort.hp/(float)fort.maxHp), 1);
		
		//batch.draw(hpBar, 27, 26);
		hpBar.draw(batch);
		
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
		
		drawWeapon();
		handleInput();
		updateAtackMode();
		moveEnemys(delta);
	}

	private void drawWeapon() {
		batch.begin();
		weapon.draw(batch);
		/*batch.draw(weapon, fort.getX() + fort.getWidth() - 
				(weapon.getWidth() - 10), (fort.getOriginY() + fort.getHeight() + 30));*/
		weapon.setX(fort.getX() + fort.getWidth() - (weapon.getWidth() - 10));
		weapon.setY(fort.getOriginY() + fort.getHeight() + 30);
		batch.end();
	}

	private void handleInput() {
		float presentTangens = weapon.getRotation();
		if(Gdx.input.isTouched() && LocalTime.now().isAfter(clickDelay.plusSeconds(1))){
			clickDelay = LocalTime.now();
			float pointerX = Gdx.input.getX();
			float pointerY = Gdx.input.getY();
			float d = pointerX - (fort.getX() + fort.getWidth() - 
				(weapon.getWidth() - 10));
			float temp = Gdx.graphics.getHeight() - (fort.getOriginY() + fort.getHeight() + 45);
			float l = pointerY - temp;
			tangens = (float) Math.toDegrees(Math.atan2(l, d));
			
			weapon.setRotation(-tangens);
			System.out.println("x: " + pointerX + ", y: " + pointerY + ", rot: " + presentTangens + ", tan: " + tangens + 
					", l: " + l + ", d: " + d);
		}
		
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
