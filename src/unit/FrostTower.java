package unit;

import java.util.ArrayList;

import world.World;

import com.example.towerdefence.Game;

public class FrostTower extends Tower {
	private ArrayList<FrostEffect> attacks;
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
		attacks = new ArrayList<FrostEffect>();
	}

	@Override
	public void update(float deltaTime) {
		lastAttackTime += deltaTime;

		if (lastAttackTime > attackspeed) {
			findTarget();
			lastAttackTime = 0;
		}

		for (int i = 0; i < attacks.size(); i++) {
			if (attacks.get(i).isDone()) {
				attacks.remove(i);
			} else {
				attacks.get(i).update(deltaTime);
			}
		}
	}
	@Override
	protected void doTowerSpecificChanges() {
		slow -= 0.1;
		duration += 0.1;
	}

	@Override
	public void render(float deltaTime) {
		game.drawBitmap(currentImage, x * 72, y * 72 + 108);
		for (int i = 0; i < attacks.size(); i++) {
			attacks.get(i).render(deltaTime);
		}
	}

	@Override
	public void findTarget() {
		ArrayList<Creep> creeps = world.creeps;
		for (int i = 0; i < creeps.size(); i++) {
			if (creeps.get(i).xPos <= x + range
					&& creeps.get(i).xPos >= x - range
					&& creeps.get(i).yPos <= y + range
					&& creeps.get(i).yPos >= y - range) {
				attacks.add(new FrostEffect(game, world, x, y, slow, duration));
				return;
			}
		}
	}
}
