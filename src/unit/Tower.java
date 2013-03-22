package unit;

import java.util.ArrayList;

import com.example.towerdefence.Game;

import World.World;
import android.graphics.Bitmap;
import android.util.Log;

public class Tower implements Unit {
	public int x, y, damage, price, level, maxLevel, projectileSpeed, range;
	protected Bitmap[] images;
	protected Bitmap currentImage, projectileImage;
	private Game game;
	private World world;
	private ArrayList<Projectile> projectiles;
	private float lastAttackTime;
	public float attackspeed;
	private boolean sold;
	
	public Tower(Game game, World controller, int x, int y) {//, int damage, double attackspeed, int range, int price, int maxLevel) {
		this.game = game;
		this.world = controller;
		this.x = x;
		this.y = y;
		lastAttackTime = 0f;
		level = 1;
		projectiles = new ArrayList<Projectile>();
		sold = false;
		projectileSpeed = 10;
	}
	public int getPrice() { 
		return price;
	}
	public int upgradeCost() {
		return (int)(price*0.5);
	}
	public void sell() {
		world.addGold((int)(price*0.5));
		sold = true;
	}
	public void upgrade() {
		if (isUpgradable() == true) {
			price += upgradeCost();
			level++;
			currentImage = images[level - 1];
			damage += (int) (0.5* damage);
		}
	}
	public int sellPrice() {
		return (int)(0.25 * price);
	}
	public boolean isUpgradable() {
		if (level <= maxLevel)
			return true;
		return false;
	}
	@Override
	public void render(float deltaTime) {
		game.drawBitmap(currentImage, x*72, y*72 + 108);
		for (int i=0; i<projectiles.size(); i++) {
			projectiles.get(i).render(deltaTime);
		}
	}
	
	public Projectile attack(Creep target) {
		return new Projectile(game, target, projectileImage, this, x, y, projectileSpeed, damage);
	}
	
	public void findTarget() {
		ArrayList<Creep> creeps = world.creeps;
		for (int i=0; i<creeps.size(); i++) {
			if (creeps.get(i).xPos < x + range && creeps.get(i).xPos > x - range &&
					creeps.get(i).yPos < y + range && creeps.get(i).yPos > y - range) {

				projectiles.add(attack(creeps.get(i)));
			}
		}
	}
	public boolean isSold() {
		return sold;
	}
	@Override
	public void update(float deltaTime) {
		lastAttackTime += deltaTime;

		if (lastAttackTime > attackspeed) {
			findTarget();
			lastAttackTime = 0;
		}
		for (int i=0; i<projectiles.size(); i++) {
			if (projectiles.get(i).hasHitTarget()) {
				projectiles.remove(i);
				continue;
			}
			projectiles.get(i).update(deltaTime);
		}
	}
	
}
