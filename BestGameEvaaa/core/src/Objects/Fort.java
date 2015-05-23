package Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Fort extends Sprite{
	
	public int maxHp;
	public int hp;
	public int def;
	
	public Fort(int maxHp){
		super(new Sprite(new Texture("fort.png"), 0, 0, 262, 327));
		this.maxHp = maxHp;
		this.hp = maxHp;
	}
}