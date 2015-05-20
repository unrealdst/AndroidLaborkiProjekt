package Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.TimeUtils;

public class Enemy extends Sprite {

	private int maxHp;
	private int hp;
	private int def;
	private int atc;
	private int atcSpeed;
	private int walkSpeed;
	private int rewerd;
	
	public boolean isAttack;
	

	private String name;

	private float xPosition;
	public long lastAtc;

	public Enemy(Sprite sprite, String name, int maxHp, int def, int atc,int atcSpeed, int walkSpeed, int rewerd) {
		super(sprite);

		this.maxHp = maxHp;
		this.def = def;
		this.atc = atc;
		this.atcSpeed = atcSpeed;
		this.walkSpeed = walkSpeed;
		this.rewerd = rewerd;
		this.isAttack = false;

	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public float getxPosition() {
		return xPosition;
	}

	public void setxPosition(float xPosition) {
		this.xPosition = xPosition;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getDef() {
		return def;
	}

	public int getAtc() {
		return atc;
	}

	public int getAtcSpeed() {
		return atcSpeed;
	}

	public int getWalkSpeed() {
		return walkSpeed;
	}

	public int getRewerd() {
		return rewerd;
	}

	public String getName() {
		return name;
	}
	
	public void setAttack(boolean isAttack) {
		this.isAttack = isAttack;
	}
}