package ui;

import android.graphics.Bitmap;

import com.example.towerdefence.Game;

public class LevelSelectScreen extends Screen {
	private Button level1, level2, level3, survival, back;
	private Bitmap background;
	private int levelNum;

	public LevelSelectScreen(Game game) {
		super(game);
		level1 = new Button(game, "ui/level1.png", 120, 256 - 170);
		level2 = new Button(game, "ui/level2.png", 120, 512 - 170);
		level3 = new Button(game, "ui/level3.png", 120, 768 - 170);
		survival = new Button(game, "ui/survival.png", 120, 1024 - 170);
		back = new Button(game, "ui/backButton.png", 120, 1280 - 170);
		background = game.loadBitmap("menu/background.png");
		levelNum = 0;
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
			levelNum = 1;
		} else if (level2.touched()) {
			levelNum = 2;
		} else if (level3.touched()) {
			levelNum = 3;
		} else if (survival.touched()) {
			levelNum = -1;
		} else if (back.touched()) {
			dispose();
			game.setScreen(new MainMenuScreen(game));
		}
		if (levelNum != 0)
			game.setScreen(new GameScreen(game, levelNum));
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
