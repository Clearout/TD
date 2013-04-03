package unit;

import java.util.Vector;

import com.example.towerdefence.Game;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

public class Projectile implements Unit {
	private Creep target;
	protected float x, y;
	protected int speed, damage;
	protected Bitmap image;
	private Game game;
	private boolean hasHitTarget;
	protected Tower tower;
	private Rect tRect, pRect;

	public Projectile(Game game, Creep target, Bitmap image, Tower tower,
			int x, int y, int speed, int damage) {
		this.game = game;
		this.target = target;
		this.image = image;
		this.tower = tower;
		this.x = x * 72;
		this.y = y * 72 + 108;
		this.speed = speed;
		this.damage = damage;
		hasHitTarget = false;
	}

	public int imageYPos() {
		return (int) y;
	}

	public int imageXPos() {
		return (int) x;
	}

	public void update(float deltaTime) {

		float theta = (float) Math.atan((target.imageYPos + 36 - y)
				/ (target.imageXPos + 36 - x));
		if ((target.imageYPos + 36 - y) <= 0
				&& (target.imageXPos + 36 - x) <= 0)
			theta -= Math.PI;
		else if ((target.imageXPos + 36 - x) <= 0)
			theta += Math.PI;
		x += (int) (deltaTime * speed * Math.cos(theta) * 72);
		y += (int) (deltaTime * speed * Math.sin(theta) * 72);
	}

	public boolean hitTarget() {
		tRect = new Rect((int) target.imageXPos, (int) target.imageYPos,
				(int) target.imageXPos + 72, (int) target.imageYPos + 72);
		pRect = new Rect((int) x, (int) y, (int) x + image.getWidth(), (int) y
				+ image.getHeight());
		if (pRect.intersect(tRect)) {
			hasHitTarget = true;
			target.takeDamage(damage);
			return true;
		}
		return false;
	}

	public boolean hasHitTarget() {
		return hasHitTarget;
	}

	@Override
	public void render(float deltaTime) {
		game.drawBitmap(image, imageXPos(), imageYPos());
	}
}
