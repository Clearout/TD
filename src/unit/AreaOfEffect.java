package unit;

import android.graphics.Bitmap;

import com.example.towerdefence.Game;

import world.World;

public class AreaOfEffect implements Unit {
	protected String[] imageNames;
	protected Bitmap activeImage;
	protected int x, y, range, imageNum;
	protected float speed, lastTime;
	protected Game game;
	protected World world;
	protected boolean done;

	public AreaOfEffect(Game game, World world, int x, int y) {
		this.game = game;
		this.world = world;
		this.x = x;
		this.y = y;
		lastTime = 0;
		imageNum = 0;
		done = false;
	}

	@Override
	public void render(float deltaTime) {
		game.drawBitmap(activeImage, x * 72 + 36 - activeImage.getWidth() / 2,
				y * 72 + 36 - activeImage.getHeight() / 2 + 108);
	}

	@Override
	public void update(float deltaTime) {
		lastTime += deltaTime;
		if (lastTime > speed / imageNames.length) {
			lastTime = 0;
			expand();
		}
		for (int i = 0; i < world.creeps.size(); i++) {
			Creep c = world.creeps.get(i);
			if (c.imageXPos > x * 72 - activeImage.getWidth() / 2
					&& c.imageYPos > y * 72 - activeImage.getHeight() / 2 + 108
					&& c.imageXPos < x * 72 + activeImage.getWidth() / 2
					&& c.imageYPos < y * 72 + activeImage.getHeight() / 2 + 108) {
				doEffect(c);
			}
		}
	}

	protected void doEffect(Creep c) {

	}

	private void effectDone() {
		done = true;
	}

	public boolean isDone() {
		return done;
	}

	protected void expand() {
		imageNum++;
		if (imageNum >= imageNames.length) {
			effectDone();
		} else {
			activeImage = game.imageRepository
					.getTowerImage(imageNames[imageNum]);
		}
	}

}
