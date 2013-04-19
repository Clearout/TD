package com.example.towerdefence;

import ui.MainMenuScreen;
import ui.Screen;
import android.util.Log;

public class TowerDefence extends Game {
	public Screen createStartScreen() {
		return new MainMenuScreen(this);
	}
	public void onPause() {
		super.onPause();
	}
	public void onResume() {
		super.onResume();
	}
}
