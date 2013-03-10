package unit;

import java.util.ArrayList;

import com.example.towerdefence.Game;

import World.World;
import android.graphics.Bitmap;

public class Tower implements Unit {
	private int x, y, damage, price, level, maxLevel, projectileSpeed, range;
	private Bitmap[] images;
	private Bitmap currentImage, projectileImage;
	private Game game;
	private World world;
	private ArrayList<Projectile> projectiles;
	private long lastAttackTime;
	private double attackspeed;
	private boolean sold;
	
	public Tower(Game game, World controller, Bitmap[] images, int x, int y, int damage, double attackspeed, int range, int price, int maxLevel) {
		this.game = game;
		this.images = images;
		currentImage = images[0];
		this.world = controller;
		this.x = x;
		this.y = y;
		this.damage = damage;
		this.price = price;
		this.maxLevel = maxLevel;
		this.attackspeed = attackspeed;
		this.range = range;
		lastAttackTime = 0;
		level = 0;
		projectiles = new ArrayList<Projectile>();
		sold = false;
	}
	public int getPrice() { 
		return price;
	}
	public int upgradeCost() {
		return (int)(price*0.5);
	}
	public void sell() {
		world.getGold((int)(price*0.5));
		sold = true;
	}
	public void upgrade() {
		if (isUpgradable()) {
			price += upgradeCost();
			level ++;
			currentImage = images[level];
			damage += (int) (0.5* damage);
		}
	}
	public int sellPrice() {
		return (int)(0.25 * price);
	}
	public boolean isUpgradable() {
		return level < maxLevel;
	}
	@Override
	public void render(float deltaTime) {
		game.drawBitmap(currentImage, x, y, 0, 0, currentImage.getWidth(), currentImage.getHeight());
		for (int i=0; i<projectiles.size(); i++) {
			projectiles.get(i).render(deltaTime);
		}
	}
	
	public Projectile attack(Creep target) {
		return new Projectile(game, target, projectileImage, x, y, projectileSpeed, damage);
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
		long startTime = System.nanoTime();
		lastAttackTime += deltaTime;
		if (lastAttackTime > attackspeed * game.oneSecond) {
			findTarget();
			lastAttackTime = System.nanoTime() - startTime;
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
