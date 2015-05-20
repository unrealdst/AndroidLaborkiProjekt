package Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Fort extends Sprite{
	
	public int maxHp;
	public int hp;
	public int def;
	
	public Fort(Sprite sprite,int maxHp){
		super(sprite);
		this.maxHp = maxHp;
		this.hp = maxHp;
	}
}