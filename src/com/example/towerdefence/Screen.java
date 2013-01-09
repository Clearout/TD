package com.example.towerdefence;

public abstract class Screen {
	protected final Game game;
	
	public Screen(Game game) {
		this.game = game;
	}
	
	public abstract void update (float deltatime);
	public abstract void pause();
	public abstract void resume();
	public abstract void dispose();
}
