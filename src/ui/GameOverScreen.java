package ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.example.towerdefence.Game;

public class GameOverScreen extends Screen {
	private Button retry, menu;
	private Bitmap background;
	private int lastLevel, score, textWidth;
	private Paint p;

	public GameOverScreen(Game game, int lastLevel, int score, boolean gameWon) {
		super(game);
		this.lastLevel = lastLevel;
		this.score = score;
		game.scores.addScore(score, lastLevel);
		if (gameWon)
			background = game.loadBitmap("menu/gameFinishedBackground.png");
		else
			background = game.loadBitmap("menu/gameOverBackground.png");
		retry = new Button(game, "menu/retryButton.png", 120, 1280 / 2);
		menu = new Button(game, "menu/menuButton.png", 120,
				1280 / 2 + retry.h() + 100);
		p = new Paint();
		p.setTypeface(Typeface.DEFAULT_BOLD);
		p.setTextSize(80);
		textWidth = (int) p.measureText("Score: " + score);
	}

	@Override
	public void update(float deltatime) {
		game.drawBitmap(background, 0, 0);
		retry.draw();
		menu.draw();
		game.drawText(p.getTypeface(), "Score: " + score,
				720 / 2 - textWidth / 2, 500, Color.BLACK,
				(int) p.getTextSize());
		retry();
		menu();
	}

	private void menu() {
		if (menu.touched()) {
			dispose();
			game.setScreen(new MainMenuScreen(game));
		}
	}

	private void retry() {
		if (retry.touched()) {
			dispose();
			game.setScreen(new GameScreen(game, lastLevel));
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
