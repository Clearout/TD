package unit;

import com.example.towerdefence.Game;

public class NormalTowerProjectile extends Projectile {
	
	public NormalTowerProjectile(Game game, Creep target, NormalTower tower, int x, int y) {
		super(game, target, tower.projectileImage, tower, x, y, tower.projectileSpeed, tower.damage);
//		image = game.imageRepository.getTowerImage("projectiles/normalTowerProjectile.png");
	}
}
