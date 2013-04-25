package ui;

import com.example.towerdefence.Game;

import android.graphics.Bitmap;

public class MainMenuScreen extends Screen {
	Button play, exit, highScores;
	Bitmap background;

	public MainMenuScreen(Game game) {
		super(game);
		background = game.loadBitmap("menu/menuBackground2.png");
		play = new Button(game, "menu/playButton2.png", 120, 450);
		exit = new Button(game, "menu/exitButton2.png", 120,
				450 + 200 + 2 * play.h());
		highScores = new Button(game, "menu/highScoresButton.png", 120,
				450 + play.h() + 100);
	}

	@Override
	public void update(float deltatime) {
		game.drawBitmap(background, 0, 0);
		play.draw();
		exit.draw();
		highScores.draw();
		play();
		exit();
		highScores();
		mute();
	}
	private void highScores() {
		if (highScores.touched()) {
			dispose();
			game.setScreen(new ScoreSelectScreen(game));
		}
	}

	private void exit() {
		if (exit.touched()) {
			dispose();
			game.finish();
		}

	}

	private void mute() {

	}

	private void play() {
		if (play.touched()) {
			game.setScreen(new LevelSelectScreen(game));
			dispose();
		}
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
