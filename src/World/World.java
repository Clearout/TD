package World;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.towerdefence.Game;

import unit.Creep;
import unit.Tower;

public class World {
	public ArrayList<Creep> creeps;
	public ArrayList<Tower> towers;
	public Map map;
	public int wave, life, untilNextWave, gold, levelCounter;
	private Level level;
	private Game game;
	private Bitmap currentBackground;
	private float lastTime;

	public World(Game game) {
		this.game = game;
		levelCounter = 1;
		level = new Level(game, this, levelCounter);
		currentBackground = level.getBackground();
		map = level.getMap();
		creeps = new ArrayList<Creep>();
		towers = new ArrayList<Tower>();
		lastTime = 0;
	}

	public void update(float deltaTime) {
		lastTime += deltaTime;
		
		if (level.update() == false) {
			if (lastTime > (level.getWave().time / level.getWave().size)) {
				Log.e("" + lastTime + " > " + level.getWave().time / level.getWave().size, "");
				creeps.add(level.getWave().getCreep());
				lastTime = 0;
			}
		} else {
			if (creeps.size() == 0)
				nextLevel();
		}

		for (int i=0; i<creeps.size(); i++) {
			creeps.get(i).update(deltaTime);
			if (creeps.get(i).isDead()) {
				getGold(creeps.get(i).goldReward);
				creeps.remove(i);
			}
		}
		for (int i=0; i<towers.size(); i++) {
			towers.get(i).update(deltaTime);
			if (towers.get(i).isSold())
				towers.remove(i);
		}
	}
	public Tower findTower(int x, int y) {
		for (int i=0; i<towers.size(); i++)
			if (towers.get(i).x == x && towers.get(i).y == y)
				return towers.get(i);
		return null;
	}

	public void getGold(int gold) {
		gold += gold;
	}

	private void nextLevel() {
		game.drawBitmap(game.loadBitmap("ui/nextWave.png"), 0, 108);

		try {
			game.wait((long)(game.oneSecond));
		} catch (InterruptedException e) {
			Log.e("Interrupted at nextLevel()", "Waiting failed at World.nextLevel()");
		}

		levelCounter++;
		level = new Level(game, this, levelCounter);
		currentBackground = level.getBackground();
		towers.clear();
		creeps.clear();
		life = 20;
		gold = 0;
	}
	public void addTower(Tower tower) {
		towers.add(tower);
	}

	public Bitmap getBackground() {
		return currentBackground;
	}
}
