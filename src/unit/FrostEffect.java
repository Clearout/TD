package unit;

import world.World;

import com.example.towerdefence.Game;

public class FrostEffect extends AreaOfEffect {
	private float slow, duration;
	public FrostEffect(Game game, World world, int x, int y, float slow, float duration) {
		super(game, world, x, y);
		imageNames = new String[5];
		imageNames[0] = "towers/frost/frostTowerProjectile.png";
		imageNames[1] = "towers/frost/frostTowerProjectile2.png";
		imageNames[2] = "towers/frost/frostTowerProjectile3.png";
		imageNames[3] = "towers/frost/frostTowerProjectile4.png";
		imageNames[4] = "towers/frost/frostTowerProjectile5.png";
		activeImage = game.imageRepository.getTowerImage(imageNames[0]);
		range = (int)((game.imageRepository.getTowerImage(imageNames[4]).getWidth()/2)/72);
		speed = 0.6f;
		this.slow = slow;
		this.duration = duration;
	}

	@Override
	protected void doEffect(Creep c) {
		c.setEffect(slow, duration);
	}
}
