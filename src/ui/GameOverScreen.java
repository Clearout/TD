package ui;

import android.graphics.Bitmap;

import com.example.towerdefence.Game;
import com.example.towerdefence.Screen;

public class GameOverScreen extends Screen {
	private Bitmap background, retry, menu;
	private int lastLevel;

	public GameOverScreen(Game game, int lastLevel) {
		super(game);
		this.lastLevel = lastLevel;
		background = game.loadBitmap("menu/gameOverBackground.png");
		retry = game.loadBitmap("menu/retryButton.png");
		menu = game.loadBitmap("menu/menuButton.png");
	}

	public void update(float deltatime) {
		game.drawBitmap(background, 0, 0);
		game.drawBitmap(retry, 120, 1280 / 2);
		game.drawBitmap(menu, 120, 1280 / 2 + retry.getHeight() + 100);
		retry();
		menu();
	}

	private void menu() {
		if (game.getTouchX(0) > 120
				&& game.getTouchX(0) < 120 + menu.getWidth()
				&& game.getTouchY(0) > 1280 / 2 + 100 + retry.getHeight()
				&& game.getTouchY(0) < 1280 / 2 + 100 + retry.getHeight()
						+ menu.getHeight()) {
			game.setScreen(new MainMenuScreen(game));
		}
	}

	private void retry() {
		if (game.getTouchX(0) > 120
				&& game.getTouchX(0) < 120 + retry.getWidth()
				&& game.getTouchY(0) > 1280 / 2
				&& game.getTouchY(0) < 1280 / 2 + retry.getHeight()) {
			game.setScreen(new GameScreen(game, lastLevel));
		}
	}

	public void pause() {
	}

	public void resume() {
	}

	public void dispose() {
	}

}
