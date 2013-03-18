package ui;

import com.example.towerdefence.Game;
import com.example.towerdefence.Screen;

import android.graphics.Bitmap;
import android.util.Log;

public class MainMenuScreen extends Screen {
	Bitmap background, play, exit;
	public MainMenuScreen(Game game) {
		super(game);
		background = game.loadBitmap("menu/menuBackground2.png");
		play = game.loadBitmap("menu/playButton2.png");
		exit = game.loadBitmap("menu/exitButton2.png");
	}

	public void update(float deltatime) {
		game.drawBitmap(background, 0, 0);
		game.drawBitmap(play, 120, 1280/2);
		game.drawBitmap(exit, 120, 1280/2 + play.getHeight() + 100);
		play();
		exit();
		mute();
		
	}
	private void exit() {
		if (game.getTouchX(0) > 120 && game.getTouchX(0) < 120 + exit.getWidth() &&
				game.getTouchY(0) > 1280/2 + 100 + play.getHeight() && 
				game.getTouchY(0) < 1280/2 + 100 + play.getHeight() + exit.getHeight()) {
			System.exit(0);
		}
	}

	private void mute() {
		
	}

	private void play() {
		if (game.getTouchX(0) > 120 && game.getTouchX(0) < 120 + play.getWidth() &&
				game.getTouchY(0) > 1280/2 && game.getTouchY(0) < 1280/2 + play.getHeight()) {
			game.setScreen(new GameScreen(game));
		}	
	}
	
	public void pause() {}

	public void resume() {}

	public void dispose() {}

}
