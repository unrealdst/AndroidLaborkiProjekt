package Screens;

import java.util.ArrayList;

import Objects.Bullet;
import Objects.Enemy;
import Objects.Fort;
import Objects.Player;
import Objects.Position;
import Objects.Weapon;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.BestGameEvaa;

public class PlayScreen extends PlayScreenFields implements Screen, InputProcessor, ApplicationListener {

	public PlayScreen(BestGameEvaa game) {
		this.game = game;

		batch = new SpriteBatch();
		background = new Texture("background.png");

		bullets = new ArrayList<Bullet>();

		createAnimations();

		createSprites();

		initLayout(game);
	}

	@Override
	public void render(float delta) {
		Gdx.input.setInputProcessor(Gdx.input.getInputProcessor());
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		drawBatch();

		batch.end();

		Gdx.input.setInputProcessor(stage);

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();

		drawWeapon();

		drawBulletsInCartidges();

		handleInput();
		updateAtackMode();
		moveEnemys(delta);
		enemysAttack();
		moveBullets(delta);
	}

	private void initLayout(BestGameEvaa game) {
		player = new Player();

		initEnemys();

		initWeapon();

		stage = new Stage();
		createMenuButton(game);
	}

	private void createSprites() {
		createHpBarSprites();

		createEndGameSprites();

		fort = new Fort(200);

		bullet = new Sprite(new Texture("cartridge.png"));
	}

	private void createAnimations() {
		createWalkAnimation();

		createReloadAnimation();
	}

	private void createMenuButton(BestGameEvaa game) {
		menuButton = new Button(game.skin);
		menuButton.setBounds(1195 * 1280 / Gdx.graphics.getWidth(), 625 * 720 / Gdx.graphics.getHeight(),
				75 * 1280 / Gdx.graphics.getWidth(), 75 * 720 / Gdx.graphics.getHeight());

		menuButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.exit();
				return true;
			}
		});
		stage.addActor(menuButton);
	}

	private void initWeapon() {
		weapon = new Weapon(new Sprite(new Texture("weapon.png")), 50, 500, 2000, 10, 2);
		weapon.setOrigin(5, weapon.getHeight() / 2);
		reload = false;
	}

	private void initEnemys() {
		enemys = new ArrayList<Enemy>();
		enemys.add(new Enemy(walkAnimation, "Szybki", 100, 10, 10, 2000, 150, 1000));
		enemys.add(new Enemy(walkAnimation, "Sredni", 100, 10, 10, 2000, 70, 1000));
		enemys.add(new Enemy(walkAnimation, "Wolny", 100, 10, 10, 2000, 90, 1000));

		for (int i = 0; i < enemys.size(); i++) {
			enemys.get(i).setxPosition(100 * (i + 1));
		}
	}

	private void createEndGameSprites() {
		loser = new Sprite(new Texture("loser.png"), 0, 0, 528, 186);
		winner = new Sprite(new Texture("winner.png"), 0, 0, 633, 119);
	}

	private void createHpBarSprites() {
		hpBar = new Sprite(new Texture("hpBar.png"), 0, 0, 398, 18);
		hpBackground = new Sprite(new Texture("hpBackground.png"), 0, 0, 402, 20);
	}

	private void createReloadAnimation() {
		reloadingSheet = new Texture(Gdx.files.internal("loading.png"));
		TextureRegion[][] tmpReloading = TextureRegion.split(reloadingSheet, reloadingSheet.getWidth() / FrameCols,
				reloadingSheet.getHeight() / FrameRows);
		reloadingFrames = new TextureRegion[FrameCols * FrameRows];

		int indexreoading = 0;
		for (int i = 0; i < FrameRows; i++) {
			for (int j = 0; j < FrameCols; j++) {
				reloadingFrames[indexreoading++] = tmpReloading[i][j];
			}
		}
		reloadingAnimation = new Animation(0.025f, reloadingFrames);
		stateTime = 0f;
	}

	private void createWalkAnimation() {
		walkSheet = new Texture(Gdx.files.internal("enemyAnimationSprite.png"));
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FrameCols, walkSheet.getHeight()
				/ FrameRows);
		walkFrames = new TextureRegion[FrameCols * FrameRows];
		int index = 0;
		for (int i = 0; i < FrameRows; i++) {
			for (int j = 0; j < FrameCols; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}

		walkAnimation = new Animation(0.025f, walkFrames);
		stateTime = 0f;
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
		stage.dispose();
		batch.dispose();
	}

	private void drawBatch() {
		drawBackground();
		drawHpBar();

		drawPlayer();
		drawFort();

		drawBullets();

		chackEndGame();

		// Animated move
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = walkAnimation.getKeyFrame(stateTime, true);

		drawEnemy();
		drawReload();
	}

	private void chackEndGame() {
		checkWinGame();

		checkLooseGame();
	}

	private void checkLooseGame() {
		if (fort.hp <= 0) {
			endGameAsLooser();
		}
	}

	private void checkWinGame() {
		if (enemys.size() <= 0 && weapon.power != 0) {
			endGameAsWinner();
		}
	}

	private void endGameAsLooser() {
		batch.draw(loser, (Gdx.graphics.getWidth() / 2) - (winner.getWidth() / 2), (Gdx.graphics.getHeight() / 2)
				- (winner.getHeight() / 2));
		weapon.power = 0;
		weapon.ammo = 0;
		weapon.magazines = 0;
		weapon.isAmmo = false;
		enemys.removeAll(enemys);

	}

	private void endGameAsWinner() {
		batch.draw(winner, (Gdx.graphics.getWidth() / 2) - (winner.getWidth() / 2), (Gdx.graphics.getHeight() / 2)
				- (winner.getHeight() / 2));
		weapon.isAmmo = false;
		weapon.ammo = 0;
		weapon.magazines = 0;
	}

	private void drawBullets() {
		for (int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			batch.draw(bullet, bullet.current.x, bullet.current.y);
		}
	}

	private void drawEnemy() {
		for (int i = 0; i < enemys.size(); i++) {
			batch.draw(currentFrame, Gdx.graphics.getWidth() - enemys.get(i).getxPosition(),
					(Gdx.graphics.getHeight() / 100) * 26 - 4);
		}
	}

	private void drawReload() {
		if (reload) {
			currentReloadingFrame = reloadingAnimation.getKeyFrame(stateTime, true);
			batch.draw(currentReloadingFrame, (1280 / Gdx.graphics.getWidth()) * 35,
					(720 / Gdx.graphics.getHeight()) * 660);
		}
	}

	private void drawFort() {
		batch.draw(fort, 0, GROUND_LEVEL);
	}

	private void drawPlayer() {
		batch.draw(player, fort.getX() + fort.getWidth() - (player.getWidth() + 10),
				(fort.getOriginY() + fort.getHeight()) - 10);
	}

	private void drawHpBar() {
		batch.draw(hpBackground, 25, 25);
		hpBar.setPosition(27, 26);
		hpBar.setOrigin(0, 0);
		hpBar.setScale((float) ((float) fort.hp / (float) fort.maxHp), 1);

		hpBar.draw(batch);
	}

	private void drawBackground() {
		batch.draw(background, 0, 0);
	}

	private void enemyKill() {
		for (int i = 0; i < enemys.size(); i++) {
			if (checkHit(i)) {
				enemys.get(i).setHp(enemys.get(i).getHp() - weapon.power);
				if (enemys.get(i).getHp() <= 0) {
					enemys.remove(i);
				}
			}
		}
	}

	private boolean checkHit(int i) {
		return (1280 - Gdx.input.getX()) < enemys.get(i).getxPosition()
				&& (enemys.get(i).getxPosition() - enemyX) < (1280 - Gdx.input.getX())
				&& (720 - Gdx.input.getY()) > GROUND_LEVEL && (GROUND_LEVEL + enemyY) > (720 - Gdx.input.getY());
	}

	private void moveBullets(float delta) {
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).move(delta);
		}

		diposeUnseeingBullet();
	}

	private void diposeUnseeingBullet() {
		ArrayList<Bullet> toRemove = new ArrayList<Bullet>();
		for (Bullet bullet : bullets) {
			if (Math.abs(bullet.current.x) > Gdx.graphics.getWidth()
					|| Math.abs(bullet.current.y) > Gdx.graphics.getHeight()) {
				toRemove.add(bullet);
			}
		}

		bullets.removeAll(toRemove);
	}

	private void echo(String s) {
		System.out.println(s);
	}

	private void drawWeapon() {
		batch.begin();
		weapon.draw(batch);
		weapon.setX(fort.getX() + fort.getWidth() - (weapon.getWidth() - 10));
		weapon.setY(fort.getOriginY() + fort.getHeight() + 30);
		batch.end();
	}

	private void drawBulletsInCartidges() {
		if (weapon.isAmmo) {
			batch.begin();
			for (int i = 0; i < weapon.ammo; i++) {
				bullet.draw(batch);
				bullet.setX(((1280 / Gdx.graphics.getWidth()) * 40) + (i * 10));
				bullet.setY(((720 / Gdx.graphics.getHeight()) * 680));
			}
			batch.end();
		}
	}

	private void handleInput() {
		if (weapon.isAmmo) {
			if (Gdx.input.isTouched() && TimeUtils.millis() > clickDelay + weapon.fireRate) {
				Position touch = new Position(Gdx.input.getX(), Gdx.input.getY());
				clickDelay = TimeUtils.millis();
				moveWeapon(touch);
				ammoDecrease();
				enemyKill();
				shootFire(touch);
			}
		}
		if (!weapon.isAmmo) {
			if (Gdx.input.isTouched() && TimeUtils.millis() > clickDelay + weapon.reload) {
				clickDelay = TimeUtils.millis();
				reload();
			}
		}
	}

	private void moveWeapon(Position touch) {
		float cathetusA = touch.x - (fort.getX() + fort.getWidth() - (weapon.getWidth() - 10));
		float temp = Gdx.graphics.getHeight() - (fort.getOriginY() + fort.getHeight() + 45);
		float cathetusB = touch.y - temp;
		float tangens = (float) Math.toDegrees(Math.atan2(cathetusB, cathetusA));

		weapon.setRotation(-tangens);
	}

	private void ammoDecrease() {
		weapon.ammo--;
		if (weapon.ammo == 0) {
			weapon.isAmmo = false;
			weapon.magazines -= 1;
			if (weapon.magazines > 0) {
				weapon.ammo = weapon.maxAmmo;
				reload = true;
			}
		}
	}

	private void reload() {
		if (weapon.magazines > 0) {
			echo("reload");
			reload = false;
			weapon.isAmmo = true;
		}
	}

	private void shootFire(Position target) {
		Position from = new Position(weapon.getX(), weapon.getY());
		Vector2 velocity = new Vector2(Math.abs(target.x - from.x), (-(target.y - from.y) - fort.getHeight()));
		Bullet newBullet = new Bullet(from, velocity);
		bullets.add(newBullet);
	}

	private void updateAtackMode() {
		for (int i = 0; i < enemys.size(); i++) {
			if (!enemys.get(i).isAttack) {
				float position = enemys.get(i).getxPosition();
				if (position > Gdx.graphics.getWidth() - fort.getWidth() + 6) {
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
				enemys.get(i).setxPosition((int) ((delta * speed) + oldPosition));
			}
		}
	}

	private void enemysAttack() {
		for (int i = 0; i < enemys.size(); i++) {
			Enemy enemy = enemys.get(i);
			if (enemy.isAttack) {
				if (TimeUtils.millis() > enemy.lastAtc + enemy.getAtcSpeed()) {
					enemy.lastAtc = TimeUtils.millis();
					fort.hp -= enemy.getAtc();
					endGame();
				}
			}
		}
	}

	private void endGame() {
		if (fort.hp < 0) {
			fort.hp = 0;
		}

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {

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
