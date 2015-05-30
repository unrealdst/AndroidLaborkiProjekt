package Screens;

import java.util.ArrayList;

import Objects.Bullet;
import Objects.Enemy;
import Objects.Fort;
import Objects.Player;
import Objects.Weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.BestGameEvaa;

public abstract class PlayScreenFields {
	protected SpriteBatch batch;
	protected Texture img, background;
	protected BestGameEvaa game;
	protected Sprite hpBar, hpBackground, loser, winner;
	protected Sprite bullet;
	
	protected Player player;
	protected Fort fort;
	protected Weapon weapon;

	protected ArrayList<Enemy> enemys;
	protected ArrayList<Bullet> bullets;
	
	protected long clickDelay = TimeUtils.millis(); 
	
	protected final int GROUND_LEVEL = (Gdx.graphics.getHeight() / 100) * 26;
	
	protected int FrameCols = 8;
	protected int FrameRows = 1;
	
	protected final int enemyX = 28;
	protected final int enemyY = 51;
	
	Animation walkAnimation;          
    Texture walkSheet;              
    TextureRegion[] walkFrames;                     
    TextureRegion currentFrame;           
    float stateTime;                 
    
    Animation reloadingAnimation;
    Texture reloadingSheet;
    TextureRegion[] reloadingFrames;
    TextureRegion currentReloadingFrame;
    
	protected Button menuButton;  
	protected Stage stage;
	protected Skin skin;
	
	boolean reload;
}
