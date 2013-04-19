package world;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.towerdefence.Game;

import ui.GameOverScreen;
import ui.MainMenuScreen;
import unit.Creep;
import unit.Tower;

public class World {
	public ArrayList<Creep> creeps;
	public ArrayList<Tower> towers;
	public Map map;
	private int life, gold, levelCounter, score;
	private Level level;
	private Game game;
	private Bitmap currentBackground;

	public World(Game game, int levelNum) {
		this.game = game;
		levelCounter = levelNum;
		boolean isSurvival = false;
		if (levelNum == -1)
			isSurvival = true;
		level = new Level(game, this, levelCounter, isSurvival);
		currentBackground = level.getBackground();
		map = level.getMap();
		creeps = new ArrayList<Creep>();
		towers = new ArrayList<Tower>();
		life = 20;
		gold = 50;
		score = 0;
	}

	public void update(float deltaTime) {
		if (level.levelComplete() == true) {
			if (creeps.size() == 0)
				game.setScreen(new GameOverScreen(game, levelCounter, score, true));
		} else {
			level.update(deltaTime);
		}

		for (int i = 0; i < towers.size(); i++) {
			towers.get(i).update(deltaTime);
			if (towers.get(i).isSold())
				towers.remove(i);
		}
		for (int i = 0; i < creeps.size(); i++) {
			creeps.get(i).update(deltaTime);
			if (creeps.get(i).isDead()) {
				addGold(creeps.get(i).goldReward);
				creeps.remove(i);
			}
		}
	}

	public Tower findTower(int x, int y) {
		for (int i = 0; i < towers.size(); i++)
			if (towers.get(i).x == x && towers.get(i).y == y)
				return towers.get(i);
		return null;
	}

	public void addGold(int gold) {
		this.gold += gold;
	}

	public void addTower(Tower tower) {
		towers.add(tower);
	}

	public void loseLife() {
		life--;
		if (life == 0)
			gameOver();
	}

	public void gameOver() {
		game.setScreen(new GameOverScreen(game, getLevelNumber(), score, false));
	}

	public void addScore(int score) {
		this.score += score;
	}

	public Level getLevel() {
		return level;
	}

	public int getLife() {
		return life;
	}

	public int getGold() {
		return gold;
	}

	public int getLevelNumber() {
		return levelCounter;
	}

	public Bitmap getBackground() {
		return currentBackground;
	}

	public void addCreep(Creep creep) {
		creeps.add(creep);
	}
}
