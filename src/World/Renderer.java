package world;

import android.graphics.Bitmap;

import com.example.towerdefence.Game;

public class Renderer {
	private World world;
	private Game game;
	private Bitmap startArea, exitArea;

	public Renderer(Game game, World world) {
		this.world = world;
		this.game = game;
		startArea = game.loadBitmap("maps/startArea.png");
		exitArea = game.loadBitmap("maps/exitArea.png");
	}

	public void render(float deltaTime) {
		game.drawBitmap(startArea, world.map.enterNode.x * 72,
				world.map.enterNode.y * 72 + 108);
		game.drawBitmap(exitArea, world.map.exitNode.x * 72,
				world.map.exitNode.y * 72 + 108);
		for (int i = world.towers.size() - 1; i >= 0; i--)
			world.towers.get(i).render(deltaTime);
		for (int i = world.creeps.size() - 1; i >= 0; i--)
			world.creeps.get(i).render(deltaTime);
	}
}
