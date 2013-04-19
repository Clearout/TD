package ui;

import java.util.ArrayList;

import android.graphics.Bitmap;

import com.example.towerdefence.Game;

public class ScoreSelectScreen extends Screen {
	private Button level1, level2, level3, survival, back;
	private Bitmap background;
	private ArrayList<Integer> scores;

	public ScoreSelectScreen(Game game) {
		super(game);
		level1 = new Button(game, "ui/level1.png", 120, 256 - 170);
		level2 = new Button(game, "ui/level2.png", 120, 512 - 170);
		level3 = new Button(game, "ui/level3.png", 120, 768 - 170);
		survival = new Button(game, "ui/survival.png", 120, 1024 - 170);
		back = new Button(game, "ui/backButton.png", 120, 1280 - 170);
		background = game.loadBitmap("menu/background.png");
	}

	@Override
	public void update(float deltatime) {
		game.drawBitmap(background, 0, 0);
		level1.draw();
		level2.draw();
		level3.draw();
		survival.draw();
		back.draw();
		if (level1.touched()) {
			scores = game.scores.getLevel1Scores();
			scoreView("Level I");
		} else if (level2.touched()) {
			scores = game.scores.getLevel2Scores();
			scoreView("Level II");
		} else if (level3.touched()) {
			scores = game.scores.getLevel3Scores();
			scoreView("Level III");
		} else if (survival.touched()) {
			scores = game.scores.getSurvivalScores();;
			scoreView("Survival");
		} else if (back.touched()) {
			dispose();
			game.setScreen(new MainMenuScreen(game));
		}
	}
	
	public void scoreView(String name) {
		game.setScreen(new ScoreScreen(game, name, scores));
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
}
