package Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Weapon extends Sprite{
	public String name;
	public int power;
	public int fireRate;
	public int ammo;
	public boolean isAmmo;
	public int maxAmmo;
	public int magazines;
	public int recoil;
	public int reload;
	
	public Weapon(Sprite sprite,int power){
		super(sprite);	
		this.power = power;
	}
}