package com.example.towerdefence;

import android.util.Log;

public class SoundRepository {
	public Sound battle, resistance, epic, imp, hound, fireTower, explosion,
			zealot, frostTower, normalTower;
	
	public SoundRepository(Game game) {
		battle = game.loadSound("music/callToBattle.mp3");
		resistance = game.loadSound("music/resistance.mp3");
		epic = game.loadSound("music/epicBattleMusic.mp3");
		imp = game.loadSound("sounds/imp.wav");
		hound = game.loadSound("sounds/hound.wav");
		fireTower = game.loadSound("sounds/fireTower.wav");
		explosion = game.loadSound("sounds/explosion.wav");
		zealot = game.loadSound("sounds/zealot.wav");
		frostTower = game.loadSound("sounds/frostTower.wav");
		normalTower = game.loadSound("sounds/normalTower.wav");
	}
}
