package ui;

import pathfinder.Mover;
import unit.Creep;
import unit.NormalTower;
import unit.Tower;

import com.example.towerdefence.Game;
import com.example.towerdefence.Screen;

import World.Renderer;
import World.World;
import android.graphics.Bitmap;
import android.util.Log;

public class GameScreen extends Screen {
	enum State { Paused, Running, GameOver }
	Bitmap background, playButton, buildButton, menuButton, bottomBar, pauseButton, 
	topBar, man, heart, coin, clock, nextWave, buildBG;
	private Icon backIcon, upgradeIcon, sellIcon;
	State state = State.Running;
	private int coinArea, clockArea, heartArea, manArea, bottomBarSeparator;
	private World world;
	private Renderer renderer;
	private boolean buildMenuUp, inIngameMenu, upgradeMenuUp;
	private int xToUse, yToUse;
	private Icon[] towers;

	public GameScreen(Game game) {
		super(game);
		// initialize bitmaps
		world = new World(game);
		renderer = new Renderer(world);
		background = world.getBackground();
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

		buildBG = game.loadBitmap("ui/transparentBuildMenuBackground.png");
		backIcon = new Icon(game, "ui/backIcon.png", 0, 0);
		upgradeIcon = new Icon(game, "ui/upgradeIcon.png", 0, 0);
		sellIcon = new Icon(game, "ui/sellIcon.png", 0, 0);
		nextWave = game.loadBitmap("ui/nextWave.png");

		coinArea = 270;
		clockArea = heartArea = manArea = 150;
		bottomBarSeparator = 32;
		buildMenuUp = false;
		inIngameMenu = false;
		upgradeMenuUp = false;
		towers = new Icon[1];
		towers[0] = new Icon(game, "ui/normalTowerIcon.png", 0, 0);
		towers[0].setTower(new NormalTower(game, world, 100, 100));
		
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

		if (inIngameMenu) {
			game.drawBitmap(buildBG, 110, 400);
			backIcon.sx(buildBG.getWidth() - backIcon.w() + 110 - 50);
			backIcon.sy(buildBG.getHeight() - backIcon.h() + 400 - 50);
			backIcon.draw();
			backPressed();
			if (buildMenuUp && !upgradeMenuUp) {
				drawTowerIcons();
				buildTowerPressed();
			} 
			if (upgradeMenuUp && !buildMenuUp) {
				drawUpgradeAndSellIcons();
				upgradeOrSellPressed();
			}
		} 
		else {		
			world.update(deltaTime);
			renderer.render(deltaTime);
			gridPressed();
		}

		// Endre til play knapp
		if (state == State.Paused && game.getTouchEvents().size() > 0) 
			state = State.Running;
		//		if (state == State.Running && game.getTouchY(0) < pauseY && game.getTouchX(0) < pause X) {
		//			state = State.Paused;
		//			return;
		//		}
	}
	
	private void drawTowerIcons() {
		int j=1;
		for (int i=0; i<towers.length; i++) {
			towers[i].sx(110 + (i+1)*50 + i*towers[i].w());
			towers[i].sy(400 + 50*j + (j-1)*towers[i].h());
			if ((i+1)%3 == 0)
				j++;
			towers[i].draw();
		}
	}
	private void drawUpgradeAndSellIcons() {
		upgradeIcon.sx(110 + 50);
		upgradeIcon.sy(400 + 50);
		sellIcon.sx(110 + buildBG.getWidth() - 50 - sellIcon.w());
		sellIcon.sy(400 + 50);
		upgradeIcon.draw();
		sellIcon.draw();
	}
	private void backPressed() {
		if (backIcon.touched(game.getTouchX(0), game.getTouchY(0)) && game.isTouchDown(0)) {
			buildMenuUp = false;
			inIngameMenu = false;
			upgradeMenuUp = false;
		}
	}
	private void buildTowerPressed() {
		for (int i=0; i<towers.length; i++) {
			if (towers[i].touched(game.getTouchX(0), game.getTouchY(0)) && game.isTouchDown(0)) {
				Tower t = null;
				// add different towers here
				if (towers[i].tower() instanceof NormalTower)
					t = new NormalTower(game, world, xToUse, yToUse);
				if (t != null) {
//					if (t.price <= world.gold) {
						world.addTower(t);
//						world.gold -= t.price;
//					}
				}
				upgradeMenuUp = false;
				buildMenuUp = false;
				inIngameMenu = false;
				xToUse = -1;
				yToUse = -1;
			}
		}
	}
	private void upgradeOrSellPressed() {
		
	}
	private void gridPressed() {
		// Traverse the grid
		for (int i=0; i<world.map.X_LENGTH; i++) {
			for (int j=0; j<world.map.Y_LENGTH; j++) {
				// If touch inside one of the 72x72 pixel squares
				if (game.getTouchX(0) >= i*72 && game.getTouchY(0) >= j*72 + 108 &&
						game.getTouchX(0) < (i+1)*72 && game.getTouchY(0) < (j+1)*72 + 108 &&
						game.isTouchDown(0)) {
					// If it's not the enter area nor the exit
					if (world.map.enterNode.x != i && world.map.enterNode.y != j && 
							world.map.exitNode.x != i && world.map.exitNode.y != j) {
						inIngameMenu = true;
						xToUse = i;
						yToUse = j;
						// If it is not already taken
						if (!world.map.blocked(new Creep(), i,  j)) {
							world.map.setTaken(i, j);
							// Build if there still exists a path from enter to exit
							// so that you cannot wall units in, otherwise set the tile free again
							if (world.map.fromEnterToExit() != null) {
								buildMenuUp = true;
								world.map.setFree(i, j);
							} else {
								world.map.setFree(i, j);
								inIngameMenu = false;
								buildMenuUp = false;
								xToUse = -1;
								yToUse = -1;
							}
							// If tile is already occupied by a tower
						} else {
							upgradeMenuUp = true;
						}
					}
				}
			}
		}
	}
	private void upgrade(int x, int y) {
		Tower tower = world.findTower(x, y);
		tower.upgrade();
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
