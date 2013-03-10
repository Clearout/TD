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
	private long lastTime;

	public World(Game game) {
		this.game = game;
		levelCounter = 1;
		level = new Level(game, this, levelCounter);
		currentBackground = level.getBackground();
	}

	public void update(float deltaTime) {
		long startTime = System.nanoTime();
		lastTime += deltaTime;
		if (lastTime > (level.getWave().time / level.getWave().size) * game.oneSecond) {
			creeps.add(level.getWave().getCreep());
			lastTime = System.nanoTime() - startTime;
		}

		if (level.isCompleted()) {
			if (creeps.size() == 0) {
				nextLevel();
			}
		}

		for (int i=0; i<creeps.size(); i++) {
			if (creeps.get(i).isDead()) 
				creeps.remove(i);
			creeps.get(i).update(deltaTime);
		}
		for (int i=0; i<towers.size(); i++) {
			if (towers.get(i).isSold())
				towers.remove(i);
			towers.get(i).update(deltaTime);
		}
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
	}

	public Bitmap getBackground() {
		return currentBackground;
	}
}
