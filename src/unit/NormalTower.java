package unit;

import world.World;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.towerdefence.Game;
import com.example.towerdefence.SoundRepository;

public class NormalTower extends Tower {

	public NormalTower(Game game, World controller, int x, int y) {
		super(game, controller, x, y);
		attackspeed = 0.5f;
		imageNames = new String[3];
		imageNames[0] = "towers/normal/normalTower.png";
		imageNames[1] = "towers/normal/normalTowerLevel2.png";
		imageNames[2] = "towers/normal/normalTowerLevel3.png";
		currentImage = game.imageRepository.getTowerImage(imageNames[0]);
		projectileImage = game.imageRepository
				.getTowerImage("towers/normal/normalTowerProjectile.png");
		damage = 1;
		price = 5;
		range = 2;
		maxLevel = 3;
		projectileSpeed = 8;
	}
	
	@Override
	protected void doTowerSpecificChanges() {
		attackspeed -= 0.1f;
		damage *= 2;
	}
	
	@Override
	public void attack(Creep target) {
		projectiles.add(new NormalTowerProjectile(game, target, this, x, y));
		SoundRepository.normalTower.play(game.soundVolume);
	}
}
