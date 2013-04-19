package ui;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.example.towerdefence.Game;

public class ScoreScreen extends Screen {
	private Button back;
	private ArrayList<Integer> scores;
	private String title;
	private Paint p;
	private Bitmap background;
	public ScoreScreen(Game game, String title, ArrayList<Integer> scores) {
		super(game);
		this.title = title;
		this.scores = scores;
		back = new Button(game, "ui/backButton.png", 120, 1080);
		background = game.loadBitmap("menu/background.png");
		p = new Paint();
		p.setTypeface(Typeface.DEFAULT_BOLD);
		p.setTextSize(80);
		p.setColor(Color.BLACK);
	}

	@Override
	public void update(float deltatime) {
		game.drawBitmap(background, 0, 0);
		back.draw();
		game.drawText(p.getTypeface(), title, 720/2 - (int)p.measureText(title), 50, p.getColor(), (int)p.getTextSize());
		String score;
		
		for (int i=0; i<10; i++) {
			if (scores.get(i) == -1)
				score = "" + 0;
			else
				score = "" + scores.get(i);
			game.drawText(p.getTypeface(), (i+1) + ". " + score, 100, 180 + i*80, p.getColor(), (int)p.getTextSize());
		}
		if (back.touched()) {
			dispose();
			game.setScreen(new ScoreSelectScreen(game));
		}
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
