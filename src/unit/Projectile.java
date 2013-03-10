package unit;

import java.util.Vector;

import com.example.towerdefence.Game;


import android.graphics.Bitmap;

public class Projectile implements Unit {
	private Creep target;
	private int x, y, speed, damage;
	private Bitmap image;
	private Game game;
	private boolean hasHitTarget;
	
	public Projectile(Game game, Creep target, Bitmap image, int x, int y, int speed, int damage) {
		this.game = game;
		this.target = target;
		this.image = image;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.damage = damage;
		hasHitTarget = false;
	}
	
	public void update(float deltaTime) {
		double theta = Math.atan((target.yPos - y) / (target.xPos - x));
		if ((target.yPos - y) <= 0 && (target.xPos - x) <= 0)
			theta -= Math.PI;
		else if ((target.xPos - x) <= 0)
			theta += Math.PI;
		x = (int)(deltaTime * speed * Math.cos(theta));
		y = (int)(deltaTime * speed * Math.sin(theta));
		if (hitTarget())
			target.takeDamage(damage);
	}
	
	public boolean hitTarget() {
		if (x == target.xPos && y == target.yPos) {
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
