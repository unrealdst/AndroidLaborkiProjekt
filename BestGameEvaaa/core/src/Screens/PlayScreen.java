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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.BestGameEvaa;

public class PlayScreen extends PlayScreenFields implements Screen, InputProcessor, ApplicationListener {
	public PlayScreen(BestGameEvaa game) {
		this.game = game;
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		background = new Texture("background.png");

		hpBar = new Sprite(new Texture("hpBar.png"), 0, 0, 398, 18);
		hpBackground = new Sprite(new Texture("hpBackground.png"), 0, 0, 402, 20);
			
		fort = new Fort(new Sprite(new Texture("fort.png"), 0, 0, 262, 327),200);
		fort.hp = 200;
		
		player = new Player(new Sprite(new Texture("player.png")));
		enemys = new ArrayList<Enemy>();
		enemys.add(new Enemy(new Sprite(new Texture("enemy.png")), "Name", 100,
				10, 10, 2000, 150, 1000));
		enemys.add(new Enemy(new Sprite(new Texture("enemy.png")), "Name", 100,
				10, 10, 2000, 60, 1000));
		enemys.add(new Enemy(new Sprite(new Texture("enemy.png")), "Name", 100,
				10, 10, 2000, 90, 1000));
		for (int i = 0; i < enemys.size(); i++) {
			enemys.get(i).setxPosition(100 * (i + 1));
		}
		weapon = new Weapon(new Sprite(new Texture("weapon.png")));
		weapon.setOrigin(5, weapon.getHeight()/2);
		weapon.fireRate = 1000;
		
		skin = new Skin(Gdx.files.internal("style/uiskin.json"));
		stage = new Stage();
		menuButton =  new Button(skin);
		menuButton.setBounds(67*1280/Gdx.graphics.getWidth(), 70*720/Gdx.graphics.getHeight(), 100, 100);
		stage.addActor(menuButton);
		
		menuButton.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			 {
			 	Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
			 	System.out.println("aaaaaaaaaaaaaaaaaaaaaaa");
			 	return true;
			 }
		});
	
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

		
		batch.begin();
		
		batch.draw(background, 0, 0);
		batch.draw(hpBackground, 25, 25);
		hpBar.setPosition(27, 26);
		hpBar.setOrigin(0,0);
		hpBar.setScale((float)((float)fort.hp/(float)fort.maxHp), 1);
		
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
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
		stage.draw();
		
		drawWeapon();
		handleInput();
		updateAtackMode();
		moveEnemys(delta);
		EnemysAttack();
	}


	private void drawWeapon() {
		batch.begin();
		weapon.draw(batch);
				weapon.setX(fort.getX() + fort.getWidth() - (weapon.getWidth() - 10));
		weapon.setY(fort.getOriginY() + fort.getHeight() + 30);
		batch.end();
	}

	private void handleInput() {
		float presentTangens = weapon.getRotation();
		if(Gdx.input.isTouched() && TimeUtils.millis() > clickDelay + weapon.fireRate){
			clickDelay = TimeUtils.millis();
			float pointerX = Gdx.input.getX();
			float pointerY = Gdx.input.getY();
			float cathetusA = pointerX - (fort.getX() + fort.getWidth() - 
				(weapon.getWidth() - 10));
			float temp = Gdx.graphics.getHeight() - (fort.getOriginY() + fort.getHeight() + 45);
			float cathetusB = pointerY - temp;
			tangens = (float) Math.toDegrees(Math.atan2(cathetusB, cathetusA));
			
			weapon.setRotation(-tangens);
			System.out.println("x: " + pointerX + ", y: " + pointerY + ", rot: " + presentTangens + ", tan: " + tangens + 
					", l: " + cathetusB + ", d: " + cathetusA);
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

	private void EnemysAttack() {
		for (int i = 0; i < enemys.size(); i++) {
			Enemy enemy = enemys.get(i);
			if (enemy.isAttack) {
				if(TimeUtils.millis() > enemy.lastAtc + enemy.getAtcSpeed()){
					enemy.lastAtc = TimeUtils.millis();
					fort.hp -= enemy.getAtc();
					endGame();
				}
			}
		}	
	}
	
	private void endGame() {
		if(fort.hp < 0){
			fort.hp = 0;
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
