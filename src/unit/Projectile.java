package unit;

import java.util.Vector;

import com.example.towerdefence.Game;


import android.graphics.Bitmap;

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
	
	public void update(float deltaTime) {
		double theta = Math.atan((target.imageYPos - y) / (target.imageXPos - x));
		if ((target.imageYPos - y) <= 0 && (target.imageXPos - x) <= 0)
			theta -= Math.PI;
		else if ((target.imageXPos - x) <= 0)
			theta += Math.PI;
		x = x + (int)(deltaTime * speed * Math.cos(theta));
		y = x + (int)(deltaTime * speed * Math.sin(theta));
		if (hitTarget())
			target.takeDamage(damage);
	}
	
	public boolean hitTarget() {
		if (x == target.imageXPos && y == target.imageYPos) {
			hasHitTarget = true;
			return true;
		}
		return false;
	}
	public boolean hasHitTarget() {
		return hasHitTarget;
	}
	@Override
	public void render(float deltaTime) {
		game.drawBitmap(image, x, y);
	}
}
