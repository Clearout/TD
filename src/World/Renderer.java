package world;

import com.example.towerdefence.Game;

public class Renderer {
	private World world;

	public Renderer(World world) {
		this.world = world;
	}

	public void render(float deltaTime) {
		for (int i = 0; i < world.towers.size(); i++)
			world.towers.get(i).render(deltaTime);
		for (int i = 0; i < world.creeps.size(); i++)
			world.creeps.get(i).render(deltaTime);
	}
}
