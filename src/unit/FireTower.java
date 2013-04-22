package unit;

import world.World;

import com.example.towerdefence.Game;
import com.example.towerdefence.Sound;
import com.example.towerdefence.SoundRepository;

public class FireTower extends Tower {
	
	public FireTower(Game game, World controller, int x, int y) {
		super(game, controller, x, y);
		attackspeed = 2f;
		imageNames = new String[3];
		imageNames[0] = "towers/fire/fireTower.png";
		imageNames[1] = "towers/fire/fireTower2.png";
		imageNames[2] = "towers/fire/fireTower3.png";
		currentImage = game.imageRepository.getTowerImage(imageNames[0]);
		projectileImage = game.imageRepository
				.getTowerImage("towers/fire/fireTowerProjectile.png");
		damage = 5;
		price = 50;
		range = 2;
		maxLevel = 3;
		projectileSpeed = 5;
	}

	@Override
	public void doTowerSpecificChanges() {
		damage += 5;
	}

	@Override
	public void doTowerEffect(Projectile p) {
		addEffect(new FireEffect(game, world, this, (int) p.x
				- p.image.getWidth() / 2, (int) p.y - p.image.getHeight() / 2));
		SoundRepository.explosion.play(game.soundVolume);
		
	}

	@Override
	public void attack(Creep target) {
		projectiles.add(new FireProjectile(game, target, projectileImage, this,
				x, y, projectileSpeed, damage));
		SoundRepository.fireTower.play(game.soundVolume);
	}
}
