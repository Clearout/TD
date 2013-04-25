package com.example.towerdefence;

import ui.MainMenuScreen;
import ui.Screen;

public class TowerDefence extends Game {
	@Override
	public Screen createStartScreen() {
		return new MainMenuScreen(this);
	}
	@Override
	public void onPause() {
		super.onPause();
	}
	@Override
	public void onResume() {
		super.onResume();
	}
}
