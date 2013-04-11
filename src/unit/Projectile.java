package unit;

import java.util.Vector;

import com.example.towerdefence.Game;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

public class Projectile implements Unit {
	protected Creep target;
	protected float x, y, theta;
	protected int speed, damage;
	protected Bitmap image;
	protected Game game;
	private boolean hasHitTarget;
	protected Tower tower;
	private Rect tRect, pRect;

	public Projectile(Game game, Creep target, Bitmap image, Tower tower,
			int x, int y, int speed, int damage) {
		this.game = game;
		this.target = target;
		this.image = image;
		this.tower = tower;
		this.x = x * 72 + 36 - image.getWidth() / 2;
		this.y = y * 72 + 108 + 36;
		this.speed = speed;
		this.damage = damage;
		hasHitTarget = false;
		theta = 0;
	}

	public int imageYPos() {
		return (int) y;
	}

	public int imageXPos() {
		return (int) x;
	}

	public float targetImageY() {
		return target.imageYPos + 36;
	}

	public float targetImageX() {
		return target.imageXPos + 36;
	}

	public void update(float deltaTime) {

		theta = (float) Math.atan2(
				(targetImageY() - y - image.getHeight() / 2), (targetImageX()
						- x - image.getWidth() / 2));
		// if ((targetImageY() - y) <= 0 && (targetImageX() - x) <= 0) {
		// theta += Math.PI;
		// theta *= -1;
		// } else if ((targetImageX() - x) < 0) {
		// theta -= Math.PI;
		// theta *= -1;
		// }

		x += (int) (deltaTime * speed * Math.cos(theta) * 72);
		y += (int) (deltaTime * speed * Math.sin(theta) * 72);
	}

	public boolean hitTarget() {
		tRect = new Rect((int) target.imageXPos, (int) target.imageYPos,
				(int) target.imageXPos + 72, (int) target.imageYPos + 72);
		pRect = new Rect((int) x, (int) y, (int) x + image.getWidth(), (int) y
				+ image.getHeight());
		if (tRect.contains(pRect)) {
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
