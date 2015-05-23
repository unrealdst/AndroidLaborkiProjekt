package Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Sprite{
	public Position from;
	public Position current;
	private Vector2 velocity;
	private final float speed = 5500;

	public Bullet(Position from, Vector2 velocity) {
		super(new Sprite(new Texture("bullet.png")));
		
		this.from = from;
		this.current = this.from;
		this.velocity = velocity;
		velocity.x = velocity.x * 1000;
		velocity.y = velocity.y * 1000;
		velocity.limit(speed);
	}

	public void move(float delta) {
		current.x = current.x + (velocity.x * delta);
		current.y = current.y + (velocity.y * delta);
	}
}

