package unit;

import java.util.ArrayList;

import world.World;

import com.example.towerdefence.Game;
import com.example.towerdefence.SoundRepository;

public class FrostTower extends Tower {
	private float slow, duration;

	public FrostTower(Game game, World controller, int x, int y) {
		super(game, controller, x, y);
		attackspeed = 1f;
		imageNames = new String[3];
		imageNames[0] = "towers/frost/frostTower.png";
		imageNames[1] = "towers/frost/frostTower2.png";
		imageNames[2] = "towers/frost/frostTower3.png";
		currentImage = game.imageRepository.getTowerImage(imageNames[0]);
		damage = 1;
		price = 10;
		range = 1;
		maxLevel = 3;
		slow = 0.6f;
		duration = 0.4f;
	}

	@Override
	protected void doTowerSpecificChanges() {
		slow -= 0.1;
		duration += 0.1;
	}

	@Override
	public void attack(Creep target) {
		addEffect(new FrostEffect(game, world, x * 72, y * 72 + 108,
				slow, duration));
		SoundRepository.frostTower.play(game.soundVolume);
	}
}
