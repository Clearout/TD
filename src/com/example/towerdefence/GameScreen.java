package com.example.towerdefence;

import android.graphics.Bitmap;

public class GameScreen extends Screen {
	enum State { Paused, Running, GameOver }
	Bitmap background, playButton, buildButton, menuButton, bottomBar, pauseButton, 
	topBar, man, heart, coin, clock, nextWave;
	State state = State.Running;
	private int coinArea, clockArea, heartArea, manArea, bottomBarSeparator;
	
	public GameScreen(Game game) {
		super(game);
		// initialize bitmaps
		background = game.loadBitmap("maps/mud.png");
		bottomBar = game.loadBitmap("ui/bottomBar.png");
		topBar = game.loadBitmap("ui/topBar.png");
		
		clock = game.loadBitmap("ui/clock.png");
		coin = game.loadBitmap("ui/coin.png");
		heart = game.loadBitmap("ui/heart.png");
		man = game.loadBitmap("ui/man.png");
		
		buildButton = game.loadBitmap("ui/buildButton.png");
		menuButton = game.loadBitmap("ui/menuButton.png");
		pauseButton = game.loadBitmap("ui/pauseButton.png");
		playButton = game.loadBitmap("ui/playButton.png");
		
		nextWave = game.loadBitmap("ui/nextWave.png");
		
		coinArea = 270;
		clockArea = heartArea = manArea = 150;
		bottomBarSeparator = 32;
	}
	
	public void update(float deltaTime) {
		// Drawing background and bars
		game.drawBitmap(background, 0, topBar.getHeight() + 1, 0, 0, background.getWidth(), background.getHeight());
		game.drawBitmap(topBar, 0, 0, 0, 0, topBar.getWidth(), topBar.getHeight());
		game.drawBitmap(bottomBar, 0, background.getHeight() + topBar.getHeight() + 1,
				0, 0, bottomBar.getWidth(), bottomBar.getHeight());
		
		// Drawing icons on the topBar
		game.drawBitmap(coin, coinArea - coin.getWidth() - 22, 72 - coin.getHeight()/2,
				0, 0, coin.getWidth(), coin.getHeight());
		game.drawBitmap(heart, coinArea + heartArea - heart.getWidth() - 2, 72 - heart.getHeight()/2,
				0, 0, heart.getWidth(), heart.getHeight());
		game.drawBitmap(man, coinArea + heartArea + manArea - man.getWidth() - 2, 72 - man.getHeight()/2,
				0, 0, man.getWidth(), man.getHeight());
		game.drawBitmap(clock, coinArea + heartArea + manArea + clockArea - clock.getWidth() - 2, 72 - clock.getHeight()/2,
				0, 0, clock.getWidth(), clock.getHeight());
		
		// Drawing buttons on the bottomBar
		game.drawBitmap(playButton, bottomBarSeparator, topBar.getHeight() + background.getHeight() + 12, 
				0, 0, playButton.getWidth(), playButton.getHeight());
		game.drawBitmap(buildButton, 2*bottomBarSeparator + playButton.getWidth(),
				topBar.getHeight() + background.getHeight() + 12,
				0, 0, buildButton.getWidth(), buildButton.getHeight());
		game.drawBitmap(pauseButton, 3*bottomBarSeparator + playButton.getWidth() + buildButton.getWidth(),
				topBar.getHeight() + background.getHeight() + 12, 0, 0, 
				pauseButton.getWidth(), pauseButton.getHeight());
		game.drawBitmap(menuButton, 4*bottomBarSeparator + playButton.getWidth() + buildButton.getWidth() + pauseButton.getWidth(),
				topBar.getHeight() + background.getHeight() + 12, 0, 0,
				menuButton.getWidth(), menuButton.getHeight());
		
		
		// Endre til play knapp
		if (state == State.Paused && game.getTouchEvents().size() > 0) 
			state = State.Running;
//		if (state == State.Running && game.getTouchY(0) < pauseY && game.getTouchX(0) < pause X) {
//			state = State.Paused;
//			return;
//		}
	}
	private void nextWave() {
		
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
