package com.example.towerdefence;

import android.graphics.Bitmap;

public class GameScreen extends Screen {
	enum State { Paused, Running, GameOver }
	Bitmap background, resume, gameOver;
	State state = State.Running;
	
	public GameScreen(Game game) {
		super(game);
		// initialize bitmaps
	}
	
	public void update(float deltaTime) {
		// Endre til play knapp
		if (state == State.Paused && game.getTouchEvents().size() > 0) 
			state = State.Running;
//		if (state == State.GameOver && game.getTouchEvents().size() > 0){
//			game.setScreen(new MainMenuScreen(game));
//			return;
//		}
//		if (state == State.Running && game.getTouchY(0) < pauseY && game.getTouchX(0) < pause X) {
//			state = State.Paused;
//			return;
//		}
		game.drawBitmap(background,  0, 0);
		if (state == State.Paused) 
			game.drawBitmap(resume, 0, 0);
		if (state == State.GameOver)
			game.drawBitmap(gameOver, 0 ,0);
	}

	@Override
	public void pause() {
		if (state == State.Running) state = State.Paused;
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
