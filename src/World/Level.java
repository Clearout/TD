package world;

import java.util.ArrayList;

import unit.Creep;
import unit.HoundCreep;
import unit.ImpCreep;
import unit.ZealotCreep;

import android.graphics.Bitmap;

import com.example.towerdefence.Game;

public class Level {
	private ArrayList<Wave> waves;
	private Game game;
	private Map map;
	private World world;
	private boolean infiniteLevel;
	private float lastTime, hardCreepRatio, fastCreepRatio, time;
	private Bitmap background;
	public int waveNumber, life, reward, movespeed, creepAmount,
			backgroundCounter;
	private Bitmap[] backgrounds;

	public Level(Game game, World world, int level, boolean infiniteLevel) {
		this.game = game;
		this.world = world;
		this.infiniteLevel = infiniteLevel;
		waves = new ArrayList<Wave>();
		lastTime = 0;
		waveNumber = 1;
		life = 0;
		reward = 0;
		movespeed = 0;
		creepAmount = 0;
		hardCreepRatio = 0;
		fastCreepRatio = 0;
		time = 0;
		backgroundCounter = 0;
		backgrounds = new Bitmap[4];
		backgrounds[0] = game.loadBitmap("maps/mud.png");
		backgrounds[1] = game.loadBitmap("maps/cracks.png");
		backgrounds[2] = game.loadBitmap("maps/rocks.png");
		backgrounds[3] = game.loadBitmap("maps/marble.png");
		background = backgrounds[backgroundCounter];
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
		if (infiniteLevel) {
			map = new Map(4, 0, 4, 13);
			life = 2;
			reward = 2;
			movespeed = 50;
			creepAmount = 10;
			time = 1.6f;
			waves.add(new Wave(new ArrayList<Creep>(), 1));
			waveNumber = 0;
			newWave();
		} else {
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
						creeps.add(new ImpCreep(game, world, map, life + i * 2,
								2 + (int) 0.2 * i, (float) 72 + 7 * i));
						if (j % 3 == 0 || j % 4 == 0)
							creeps.add(new HoundCreep(game, world, map, life
									+ i * 2, reward + (int) 0.2 * i,
									(float) movespeed + 7 * i));
						if (j % 2 == 0)
							creeps.add(new ZealotCreep(game, world, map,
									5 + i * 2, 2 + (int) 0.2 * i, (float) 72
											+ 7 * i));
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
						c.add(new ImpCreep(game, world, map, 5 + i * 2, 2
								+ (int) 0.2 * i, (float) 72 + 20 * i));
					}
					waves.add(new Wave(c, 1 + (int) 0.5 * i));
				}
			}
		}
	}

	private void newWave() {
		if (infiniteLevel) {
			life += 1;

			if (movespeed < 200)
				movespeed += 3;
			creepAmount++;
			if (hardCreepRatio < 0.4)
				hardCreepRatio += 0.5;
			if (fastCreepRatio < 0.6)
				fastCreepRatio += 0.8;
			if (time > 0.4)
				time -= 0.05;
			ArrayList<Creep> creeps = new ArrayList<Creep>();
			for (int i = 0; i < creepAmount; i++) {
				creeps.add(new ImpCreep(game, world, map, life, reward,
						movespeed));
			}
			for (int i = 0; i < (int) (hardCreepRatio * (float)creepAmount); i++) {
				int placement = (int) (Math.random() * (creeps.size() - 1));
				creeps.add(placement, new ZealotCreep(game, world, map, life,
						reward, movespeed));
			}
			for (int i = 0; i < (int) (fastCreepRatio * (float)creepAmount); i++) {
				int placement = (int) (Math.random() * (creeps.size() - 1));
				creeps.add(placement, new HoundCreep(game, world, map, life,
						reward, movespeed));
			}
			waves.add(new Wave(creeps, time));

			if (waveNumber % 10 == 0) {
				backgroundCounter++;
				if (backgroundCounter > 3)
					backgroundCounter = 0;
				background = backgrounds[backgroundCounter];
			}

		}
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
