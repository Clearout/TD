package unit;

import world.World;

import com.example.towerdefence.Game;

public class FireEffect extends AreaOfEffect {
	private Tower t;
	
	public FireEffect(Game game, World world, Tower t, int x, int y) {
		super(game, world, x, y);
		this.t = t;
		imageNames = new String[2];
		imageNames[0] = "towers/fire/fireTowerProjectile.png";
		imageNames[1] = "towers/fire/fireTowerProjectile2.png";
		activeImage = game.imageRepository.getTowerImage(imageNames[0]);
		range = (game.imageRepository.getTowerImage(imageNames[1])
				.getWidth() / 2) / 72;
		speed = 0.4f;
	}
	
	@Override
	public void doEffect(Creep c) {
		c.takeDamage(t.damage);
	}

}
