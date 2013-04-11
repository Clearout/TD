package unit;

import java.util.ArrayList;

import world.World;

import com.example.towerdefence.Game;

import android.graphics.Bitmap;
import android.util.Log;

public class Tower implements Unit {
	public int x, y, damage, price, level, maxLevel, projectileSpeed, range;
	protected String[] imageNames;
	protected Bitmap currentImage, projectileImage;
	protected Game game;
	protected World world;
	private ArrayList<AreaOfEffect> effects;
	protected ArrayList<Projectile> projectiles;
	protected float lastAttackTime;
	public float attackspeed;
	private boolean sold;

	public Tower(Game game, World controller, int x, int y) {
		this.game = game;
		this.world = controller;
		this.x = x;
		this.y = y;
		lastAttackTime = 0f;
		level = 1;
		projectiles = new ArrayList<Projectile>();
		effects = new ArrayList<AreaOfEffect>();
		sold = false;
	}

	public int getPrice() {
		return price;
	}

	public int upgradeCost() {
		return (int) (price * 0.5);
	}

	public void sell() {
		world.addGold(sellPrice());
		sold = true;
		world.map.setFree(x, y);
	}

	public void upgrade() {
		if (isUpgradable() == true) {
			if (price <= world.getGold()) {
				world.addGold(-price);
				price += upgradeCost();
				level++;
				currentImage = game.imageRepository
						.getTowerImage(imageNames[level - 1]);
				doTowerSpecificChanges();
			}
		}
	}

	protected void doTowerSpecificChanges() {

	}

	public int sellPrice() {
		return (int) (0.75 * price);
	}

	public boolean isUpgradable() {
		if (level < maxLevel)
			return true;
		return false;
	}

	@Override
	public void render(float deltaTime) {
		game.drawBitmap(currentImage, x * 72, y * 72 + 108);
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(deltaTime);
		}
		for (int i = 0; i< effects.size(); i++) {
			effects.get(i).render(deltaTime);
		}
	}
	protected void addEffect(AreaOfEffect aoe) {
		effects.add(aoe);
	}
	public void attack(Creep target) {
		projectiles.add(new Projectile(game, target, projectileImage, this, x, y, projectileSpeed, damage));
	}

	public void findTarget() {
		ArrayList<Creep> creeps = world.creeps;
		for (int i = 0; i < creeps.size(); i++) {
			if (creeps.get(i).xPos <= x + range
					&& creeps.get(i).xPos >= x - range
					&& creeps.get(i).yPos <= y + range
					&& creeps.get(i).yPos >= y - range) {
				
				attack(creeps.get(i));
				return;
			}
		}
	}

	public boolean isSold() {
		return sold;
	}
	public void doTowerEffect(Projectile p) {
		
	}

	@Override
	public void update(float deltaTime) {
		lastAttackTime += deltaTime;

		if (lastAttackTime > attackspeed) {
			findTarget();
			lastAttackTime = 0;
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).hitTarget();
			if (projectiles.get(i).hasHitTarget()) {
				doTowerEffect(projectiles.get(i));
				projectiles.remove(i);
				continue;
			}
			projectiles.get(i).update(deltaTime);
		}
		for (int i = 0; i < effects.size(); i++) {
			if (effects.get(i).isDone()) {
				effects.remove(i);
			} else {
				effects.get(i).update(deltaTime);
			}
		}
	}

}
