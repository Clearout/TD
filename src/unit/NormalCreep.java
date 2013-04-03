package unit;

import world.Map;
import world.World;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.towerdefence.Game;

public class NormalCreep extends Creep {
	public NormalCreep(Game game, World world, Map map,	int life, int goldReward, float movespeed) {
		super(game, world, map, life, goldReward, movespeed);
		try {
			images = new Bitmap[1];
			activeImage = game.loadBitmap("creeps/normalCreep.png");
			images[0] = activeImage;
		} catch (NullPointerException e) {
			Log.e("Nullpointer", "Ruh roh");
		}
	}
}
