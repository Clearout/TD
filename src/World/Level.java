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
	private boolean levelComplete;
	
	public Level(Game game, World world, int level) {
		generateLevel(level);
		this.game = game;
		this.world = world;
		map = world.map;
		waveCounter = 0;
		levelComplete = false;
	}
	public Wave getWave() {
		if (waves.get(waveCounter).creeps.size() == 0) {
			waveCounter++;
			if (waveCounter == waves.size())
				levelComplete = true;
		}
		return waves.get(waveCounter);
	}
	public ArrayList<Wave> generateLevel(int level) {
		if (level == 1) {
			for (int i=0; i<10; i++) {
				ArrayList<Creep> creeps = new ArrayList<Creep>();
				for (int j=0; j<20 + i*2; j++) {
					creeps.add(new NormalCreep(game, world, map, map.enter.x, map.enter.y,
							10 + i*2, 2 + (int)(i*0.2), 72 + (int)(72*0.2*i)));
				}
				waves.add(new Wave(creeps, 20 - (int)(0.5*i)));
			}
			background = game.loadBitmap("maps/mud.png");
		}
		return null;
	}
	public boolean isCompleted() {
		return levelComplete;
	}
	public Bitmap getBackground() {
		return background;
	}
}
