package com.example.towerdefence;

import android.graphics.Bitmap;
import android.util.Log;

public class MainMenuScreen extends Screen {
	Bitmap background, play, exit, mute;
	public MainMenuScreen(Game game) {
		super(game);
		background = game.loadBitmap("menu/menuBackground.png");
		play = game.loadBitmap("menu/play.png");
		exit = game.loadBitmap("menu/exit.png");
		mute = game.loadBitmap("menu/mute.png");
	}

	public void update(float deltatime) {
		game.drawBitmap(background, 0, 0);
		game.drawBitmap(play, 150, 900, 0, 0, 200, 100);
		game.drawBitmap(exit, 400, 975, 0, 0, 200, 100);
		game.drawBitmap(mute, 250, 1100, 0, 0, 150, 150);
		play();
		exit();
		mute();
		
	}
	private void exit() {
		if (game.getTouchX(0) > 400 && game.getTouchX(0) < 600 &&
				game.getTouchY(0) > 975 && game.getTouchY(0) < 1075) {
			System.exit(0);
		}
	}

	private void mute() {
		
	}

	private void play() {
		if (game.getTouchX(0) > 150 && game.getTouchX(0) < 350 &&
				game.getTouchY(0) > 900 && game.getTouchY(0) < 1000) {
			game.setScreen(new GameScreen(game));
		}	
	}
	
	public void pause() {}

	public void resume() {}

	public void dispose() {}

}
