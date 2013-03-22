package World;

import java.util.ArrayList;

import android.graphics.Bitmap;

import com.example.towerdefence.Game;

import unit.Creep;
import unit.NormalCreep;

public class Level {
	private ArrayList<Wave> waves;
	private int waveCounter;
	private Game game;
	private World world;
	private Map map;
	private Bitmap background;
	
	public Level(Game game, World world, int level) {
		this.game = game;
		this.world = world;
		waveCounter = 0;
		waves = new ArrayList<Wave>();
		generateLevel(level);
	}
	public Wave getWave() {
		return waves.get(waveCounter);
	}
	public boolean update() {
		if (waves.get(waveCounter).creeps.size() == 0) {
			if (!levelCompleted()) {
				startNextWave();
				return false;
			}
			return true;
		}
		return false;
	}
	private boolean levelCompleted() {
		if (waves.size() > waveCounter + 1) 
			return false;
		return  true;
	}
	private void startNextWave() {
		waveCounter++;
	}
	public ArrayList<Wave> generateLevel(int level) {
		if (level == 1) {
			map = new Map(3, 0, 9, 13);
			ArrayList<Creep> c = new ArrayList<Creep>();
			c.add(new NormalCreep(game, world, map, map.enterNode.x, map.enterNode.y, 10, 10, 50));
			waves.add(new Wave(c, 1));
			background = game.loadBitmap("maps/cracks.png");
		}
		if (level == 10) {
			map = new Map(8, 0, 4, 13);
			for (int i=0; i<10; i++) {
				ArrayList<Creep> creeps = new ArrayList<Creep>();
				for (int j=0; j<20 + i*2; j++) {
					creeps.add(new NormalCreep(game, world, map, map.enterNode.x, map.enterNode.y,
							10 + i*2, 2 + (int)(i*0.2), 72 + (int)(72*0.2*i)));
				}
				waves.add(new Wave(creeps, 20 - (int)(0.5*i)));
			}
			background = game.loadBitmap("maps/mud.png");
		}
		return null;
	}
	public Map getMap() { return map; }
	public Bitmap getBackground() {
		return background;
	}
}
