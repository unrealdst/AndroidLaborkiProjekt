package Screens;

import java.util.ArrayList;

import Objects.Enemy;
import Objects.Fort;
import Objects.Player;
import Objects.Weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.BestGameEvaa;

public abstract class PlayScreenFields {
	protected SpriteBatch batch;
	protected Texture img, background;
	protected BestGameEvaa game;
	protected Sprite hpBar, hpBackground;
	
	protected Player player;
	protected Fort fort;
	protected Weapon weapon;

	protected ArrayList<Enemy> enemys;
	protected float tangens;
	protected long clickDelay = TimeUtils.millis();
	
	protected Button menuButton;  
	protected Stage stage;
	protected Skin skin;
}
