package world;

import java.util.ArrayList;

import unit.Creep;
import unit.NormalCreep;

import android.graphics.Bitmap;

import com.example.towerdefence.Game;

public class Level {
	private ArrayList<Wave> waves;
	private Game game;
	private Map map;
	private World world;
	private boolean infiniteLevel;
	private float lastTime;
	private Bitmap background;
	public int waveNumber;

	public Level(Game game, World world, int level) {
		this.game = game;
		this.world = world;
		waves = new ArrayList<Wave>();
		infiniteLevel = false;
		lastTime = 0;
		waveNumber = 1;
		generateLevel(level);
	}

	public Wave getWave() {
		if (waves.size() > 0)
			return waves.get(0);
		return null;
	}

	public boolean levelComplete() {
		if (getWave() == null)
			return true;
		else
			return false;
	}

	public void update(float deltaTime) {
		lastTime += deltaTime;

		if (lastTime > getWave().time) {
			lastTime = 0;
			if (getWave().creeps.size() == 0)
				newWave();
			else
				world.addCreep(getWave().getCreep());
		}
	}

	private void generateLevel(int level) {
		switch (level) {
		case 0:
			infiniteLevel = true;
			break;
		case 1:
			map = new Map(4, 0, 4, 13);
			background = game.loadBitmap("maps/mud.png");

			for (int i = 0; i < 20; i++) {
				ArrayList<Creep> creeps = new ArrayList<Creep>();

				for (int j = 0; j < 2 * (i + 1); j++) {
					creeps.add(new NormalCreep(game, world, map, 5 + i * 2, 2
							+ (int) 0.2 * i, (float) 72 + 7 * i));
				}
				waves.add(new Wave(creeps, 1 + (int) 0.5 * i));
			}
			break;
		case 2:
			map = new Map(0, 0, 9, 13);
			background = game.loadBitmap("maps/marble.png");

			for (int i = 0; i < 5; i++) {
				ArrayList<Creep> c = new ArrayList<Creep>();

				for (int j = 0; j < 5; j++) {
					c.add(new NormalCreep(game, world, map, 5 + i * 2, 2
							+ (int) 0.2 * i, (float) 72 + 20 * i));
				}
				waves.add(new Wave(c, 1 + (int) 0.5 * i));
			}
		}
	}

	private void newWave() {
		if (waves.size() > 0) {
			waves.remove(0);
			waveNumber++;
		}
	}

	public Bitmap getBackground() {
		return background;
	}

	public Map getMap() {
		return map;
	}

}
