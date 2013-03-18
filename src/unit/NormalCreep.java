package unit;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.towerdefence.Game;

import World.Map;
import World.World;

public class NormalCreep extends Creep {
	public NormalCreep(Game game, World world, Map map, int xPos, int yPos, int life, int goldReward, float movespeed) {
		super(game, world, map, xPos, yPos, life, goldReward, movespeed);
		try {
			images = new Bitmap[1];
			activeImage = game.loadBitmap("creeps/normalCreep.png");
			images[0] = activeImage;
		} catch (NullPointerException e) {
			Log.e("Nullpointer", "Ruh roh");
		}
	}
}
