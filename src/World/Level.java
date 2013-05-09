package world;

import java.util.ArrayList;

import unit.Creep;
import unit.HoundCreep;
import unit.ImpCreep;
import unit.ZealotCreep;

import android.graphics.Bitmap;
import com.example.towerdefence.Game;
import com.example.towerdefence.SoundRepository;

public class Level {
	private ArrayList<Wave> waves;
	private Game game;
	private Map map;
	private World world;
	private boolean infiniteLevel;
	private float lastTime, hardCreepRatio, fastCreepRatio, time;
	private Bitmap background;
	public int waveNumber, life, reward, movespeed, creepAmount, musicCounter;
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
		backgrounds = new Bitmap[4];
		backgrounds[0] = game.loadBitmap("maps/mud.png");
		backgrounds[1] = game.loadBitmap("maps/cracks.png");
		backgrounds[2] = game.loadBitmap("maps/rocks.png");
		backgrounds[3] = game.loadBitmap("maps/marble.png");
		background = backgrounds[0];
		SoundRepository.music.setLooping(true);
		SoundRepository.music.play();
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
			int sx = (int) (Math.random() * 9);
			int sy = (int) (Math.random() * 4);
			int ex = (int) (Math.random() * 9);
			int ey = (int) (Math.random() * 4 + 9);
			map = new Map(sx, sy, ex, ey);
			life = 2;
			reward = 1;
			movespeed = 50;
			creepAmount = 10;
			time = 1.6f;
			waves.add(new Wave(new ArrayList<Creep>(), 1));
			waveNumber = 0;
			background = backgrounds[(int) (Math.random() * 4)];
			newWave();
		} else {
			if (level == 1) {
				map = new Map(4, 0, 4, 13);
				background = backgrounds[0];
				life = 2;
				reward = 1;
				movespeed = 50;
				creepAmount = 5;
				time = 1f;
				for (int i = 0; i < 20; i++) {
					ArrayList<Creep> creeps = new ArrayList<Creep>();

					for (int j = 0; j < creepAmount; j++) {
						creeps.add(new ImpCreep(game, world, map, life, reward,
								movespeed));
						if (j % 6 == 0 || j % 7 == 0)
							creeps.add(new HoundCreep(game, world, map, life,
									reward, movespeed));
						if (j % 9 == 0 || j % 13 == 0)
							creeps.add(new ZealotCreep(game, world, map, life,
									reward, movespeed));
					}
					life += 1;
					movespeed += 2;
					time -= 0.032;
					creepAmount++;
					waves.add(new Wave(creeps, time));
				}
			} else if (level == 2) {
				map = new Map(0, 0, 9, 13);
				background = backgrounds[2];
				life = 2;
				reward = 2;
				movespeed = 50;
				creepAmount = 8;
				time = 1f;
				for (int i = 0; i < 20; i++) {
					ArrayList<Creep> creeps = new ArrayList<Creep>();
					for (int j = 0; j < creepAmount; j++) {
						creeps.add(new ImpCreep(game, world, map, life, reward,
								movespeed));
						if (j % 4 == 0 || j % 5 == 0)
							creeps.add(new HoundCreep(game, world, map, life,
									reward, movespeed));
						if (j % 5 == 0 || j % 7 == 0)
							creeps.add(new ZealotCreep(game, world, map, life,
									reward, movespeed));
					}
					life += 1 + (int) i / 5;
					movespeed += 3;
					time -= 0.032;
					creepAmount++;
					waves.add(new Wave(creeps, time));
				}

			} else {
				map = new Map(9, 0, 9, 13);
				background = backgrounds[1];
				life = 3;
				reward = 3;
				movespeed = 50;
				creepAmount = 10;
				time = 1f;
				for (int i = 0; i < 20; i++) {
					ArrayList<Creep> creeps = new ArrayList<Creep>();

					for (int j = 0; j < creepAmount; j++) {
						creeps.add(new ImpCreep(game, world, map, life, reward,
								movespeed));
						if (j % 6 == 0 || j % 7 == 0)
							creeps.add(new HoundCreep(game, world, map, life,
									reward, movespeed));
						if (j % 9 == 0 || j % 13 == 0)
							creeps.add(new ZealotCreep(game, world, map, life,
									reward, movespeed));
					}
					life += 1 + (int) i / 3;
					movespeed += 4;
					time -= 0.032;
					creepAmount++;
					waves.add(new Wave(creeps, time));
				}
			}
		}
	}

	private void newWave() {
		if (infiniteLevel) {
			life += 1 + (int) (Math.pow(waveNumber, 2.3) / 100);

			if (movespeed < 200)
				movespeed += 3;
			creepAmount++;
			if (hardCreepRatio < 0.4)
				hardCreepRatio += 0.05;
			if (fastCreepRatio < 0.6)
				fastCreepRatio += 0.08;
			if (time > 0.35)
				time -= 0.025;
			ArrayList<Creep> creeps = new ArrayList<Creep>();
			for (int i = 0; i < creepAmount; i++) {
				creeps.add(new ImpCreep(game, world, map, life, reward,
						movespeed));
			}
			for (int i = 0; i < (int) (hardCreepRatio * creepAmount); i++) {
				int placement = (int) (Math.random() * (creeps.size() - 1));
				creeps.add(placement, new ZealotCreep(game, world, map, life,
						reward, movespeed));
			}
			for (int i = 0; i < (int) (fastCreepRatio * creepAmount); i++) {
				int placement = (int) (Math.random() * (creeps.size() - 1));
				creeps.add(placement, new HoundCreep(game, world, map, life,
						reward, movespeed));
			}
			waves.add(new Wave(creeps, time));
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
