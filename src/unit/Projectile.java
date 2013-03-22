package unit;

import java.util.Vector;

import com.example.towerdefence.Game;


import android.graphics.Bitmap;
import android.util.Log;

public class Projectile implements Unit {
	private Creep target;
	protected int x, y, speed, damage;
	protected Bitmap image;
	private Game game;
	private boolean hasHitTarget;
	protected Tower tower;
	
	public Projectile(Game game, Creep target, Bitmap image, Tower tower, int x, int y, int speed, int damage) {
		this.game = game;
		this.target = target;
		this.image = image;
		this.tower = tower;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.damage = damage;
		hasHitTarget = false;
	}
	public int imageYPos() {
		return y*72 + 108;
	}
	public int imageXPos() {
		return x*72;
	}
	public void update(float deltaTime) {

		float theta = (float)Math.atan((target.imageYPos - imageYPos()) / (target.imageXPos - imageXPos()));
		if ((target.imageYPos - imageYPos()) <= 0 && (target.imageXPos - imageXPos()) <= 0)
			theta -= Math.PI;
		else if ((target.imageXPos - imageXPos()) <= 0)
			theta += Math.PI;
		x = x + (int)(deltaTime * speed * Math.cos(theta));
		y = y + (int)(deltaTime * speed * Math.sin(theta));
	}
	
	public boolean hitTarget() {
		if (imageXPos() == target.imageXPos && imageYPos() == target.imageYPos) {
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
