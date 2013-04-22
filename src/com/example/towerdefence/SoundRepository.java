package com.example.towerdefence;

import android.util.Log;

public class SoundRepository {
	public static Sound imp, hound, fireTower, explosion,
			zealot, frostTower, normalTower;
	
	public SoundRepository(Game game) {
		imp = game.loadSound("sounds/imp.wav");
		hound = game.loadSound("sounds/hound.wav");
		fireTower = game.loadSound("sounds/fireTower.wav");
		explosion = game.loadSound("sounds/explosion.wav");
		zealot = game.loadSound("sounds/zealot.wav");
		frostTower = game.loadSound("sounds/frostTower.wav");
		normalTower = game.loadSound("sounds/normalTower.wav");
	}
}
