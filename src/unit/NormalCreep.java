package unit;

import android.graphics.Bitmap;

import com.example.towerdefence.Game;

import World.Map;
import World.World;

public class NormalCreep extends Creep {
	public NormalCreep(Game game, World world, Map map, int xPos, int yPos, int life, int goldReward, float movespeed) {
		super(game, world, map, xPos, yPos, life, goldReward, movespeed);
		images = new Bitmap[1];
		images[0] = game.loadBitmap("creeps/normalCreep.png");
	}
}
