package ui;

import unit.Creep;
import unit.NormalTower;
import unit.Tower;
import world.Renderer;
import world.World;

import com.example.towerdefence.Game;
import com.example.towerdefence.Screen;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

public class GameScreen extends Screen {
	enum State {
		Paused, Running, GameOver
	}

	private Bitmap background, bottomBar, topBar, man, heart, coin, buildBG;
	private Bitmap[] ffButtons;
	private Button playPause, fastForward, menu;
	private Icon backIcon, upgradeIcon, sellIcon;
	State state = State.Running;
	private int coinArea, heartArea, manArea, bottomBarSeparator;
	private World world;
	private Renderer renderer;
	private boolean buildMenuUp, inIngameMenu, touchDelay;
	private int xToUse, yToUse;
	private Icon[] towers;
	private float lastTouch, timeSpeed;

	public GameScreen(Game game) {
		super(game);
		timeSpeed = 1;
		// initialize bitmaps
		world = new World(game);
		renderer = new Renderer(world);
		background = world.getBackground();
		
		bottomBar = game.loadBitmap("ui/bottomBar.png");
		topBar = game.loadBitmap("ui/topBar.png");
		coin = game.loadBitmap("ui/coin.png");
		heart = game.loadBitmap("ui/heart.png");
		man = game.loadBitmap("ui/man.png");
		buildBG = game.loadBitmap("ui/transparentBuildMenuBackground.png");
		
		backIcon = new Icon(game, "ui/backIcon.png", 0, 0);
		upgradeIcon = new Icon(game, "ui/upgradeIcon.png", 0, 0);
		sellIcon = new Icon(game, "ui/sellIcon.png", 0, 0);

		coinArea = 320;
		heartArea = manArea = 200;
		bottomBarSeparator = 50;

		buildMenuUp = false;
		inIngameMenu = false;

		towers = new Icon[1];
		towers[0] = new Icon(game, "ui/normalTowerIcon.png", 0, 0);
		towers[0].setTower(new NormalTower(game, world, 100, 100));

		playPause = new Button(game, "ui/pauseButton.png",
				240 - 144 - bottomBarSeparator, topBar.getHeight()
						+ background.getHeight() + 12);
		menu = new Button(game, "ui/homeButton.png",
				720 - 144 - bottomBarSeparator, topBar.getHeight()
						+ background.getHeight() + 12);
		fastForward = new Button(game, "ui/fastForwardButton.png",
				480 - 144 - bottomBarSeparator, topBar.getHeight()
						+ background.getHeight() + 12);

		ffButtons = new Bitmap[3];
		ffButtons[0] = game.loadBitmap("ui/fastForwardButton.png");
		ffButtons[1] = game.loadBitmap("ui/fastForwardButton2.png");
		ffButtons[2] = game.loadBitmap("ui/fastForwardButton3.png");

		lastTouch = 0;
		touchDelay = false;
	}

	public void update(float deltaTime) {
		lastTouch += deltaTime;
		if (lastTouch > 0.2) {
			touchDelay = false;
			lastTouch = 0;
		}
		// Drawing background and bars
		game.drawBitmap(background, 0, topBar.getHeight() + 1);
		game.drawBitmap(topBar, 0, 0);
		game.drawBitmap(bottomBar, 0,
				background.getHeight() + topBar.getHeight() + 1);

		// Drawing icons on the topBar
		game.drawBitmap(coin, coinArea - coin.getWidth() - 22,
				72 - coin.getHeight() / 2);
		game.drawText(Typeface.DEFAULT, "" + world.getGold(), 50,
				72 - coin.getHeight() / 2 + 10, Color.BLACK, 60);

		game.drawBitmap(heart, coinArea + heartArea - heart.getWidth() - 2,
				72 - heart.getHeight() / 2);
		game.drawText(Typeface.DEFAULT, "" + world.getLife(), coinArea,
				72 - coin.getHeight() / 2 + 10, Color.BLACK, 60);

		game.drawBitmap(man, coinArea + heartArea + manArea - man.getWidth()
				- 2, 72 - man.getHeight() / 2);
		game.drawText(Typeface.DEFAULT, "" + world.getLevel().waveNumber,
				coinArea + heartArea, 72 - man.getHeight() / 2 + 10,
				Color.BLACK, 60);

		// Drawing buttons on the bottomBar
		playPause.draw();
		menu.draw();
		fastForward.draw();

		if (inIngameMenu) {
			game.drawBitmap(buildBG, 110, 400);
			backIcon.sx(buildBG.getWidth() - backIcon.w() + 110 - 50);
			backIcon.sy(buildBG.getHeight() - backIcon.h() + 400 - 50);
			backIcon.draw();
			backPressed();
			if (buildMenuUp) {
				drawTowerIcons();
				buildTowerPressed();
			} else {
				drawUpgradeAndSellIcons();
				upgradeOrSellPressed();
			}
		} else {
			world.update(deltaTime * timeSpeed);
			renderer.render(deltaTime * timeSpeed);
			gridPressed();
		}
		if (touchDelay == false) {

			if (state == State.Paused) {
				if (playPause.touched()) {
					touchDelay = true;
					resume();
				}
			} else if (state == State.Running) {
				if (playPause.touched()) {
					touchDelay = true;
					pause();
				}
			}
			if (menu.touched()) {
				touchDelay = true;
				game.setScreen(new MainMenuScreen(game));
				this.dispose();
			}
			if (fastForward.touched()) {
				touchDelay = true;
				if (timeSpeed != 0) {
					timeSpeed++;
					if (timeSpeed > 3)
						timeSpeed = 1;

					fastForward.setImg(ffButtons[(int) (timeSpeed - 1)]);
				}
			}
		}
	}

	private void drawTowerIcons() {
		int j = 1;
		for (int i = 0; i < towers.length; i++) {
			towers[i].sx(110 + (i + 1) * 50 + i * towers[i].w());
			towers[i].sy(400 + 50 * j + (j - 1) * towers[i].h());
			if ((i + 1) % 3 == 0)
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
		if (backIcon.touched() && touchDelay == false) {
			buildMenuUp = false;
			inIngameMenu = false;
			touchDelay = true;
		}
	}

	private void buildTowerPressed() {
		for (int i = 0; i < towers.length; i++) {
			if (towers[i].touched() && touchDelay == false) {
				Tower t = null;
				// add different towers here
				if (towers[i].tower() instanceof NormalTower)
					t = new NormalTower(game, world, xToUse, yToUse);
				if (t != null) {
					// if (t.price <= world.gold) {
					world.addTower(t);
					world.map.setTaken(xToUse, yToUse);
					// world.gold -= t.price;
					// }
				}
				buildMenuUp = false;
				inIngameMenu = false;
				touchDelay = true;
				xToUse = -1;
				yToUse = -1;
			}
		}
	}

	private void upgradeOrSellPressed() {
		if (touchDelay == false) {
			if (upgradeIcon.touched()) {
				touchDelay = true;
				world.findTower(xToUse, yToUse).upgrade();
				inIngameMenu = false;
			} else if (sellIcon.touched()) {
				touchDelay = true;
				world.findTower(xToUse, yToUse).sell();
				inIngameMenu = false;
			}
		}
	}

	private void gridPressed() {
		// Traverse the grid
		if (touchDelay == false) {

			for (int i = 0; i < world.map.X_LENGTH; i++) {
				for (int j = 0; j < world.map.Y_LENGTH; j++) {

					// If touch inside one of the 72x72 pixel squares
					if (game.getTouchX(0) >= i * 72
							&& game.getTouchY(0) >= j * 72 + 108
							&& game.getTouchX(0) < (i + 1) * 72
							&& game.getTouchY(0) < (j + 1) * 72 + 108
							&& game.isTouchDown(0)) {

						// If it's not the enter area nor the exit
						touchDelay = true;
						if (world.map.isEnterOrExit(i, j) == false) {
							xToUse = i;
							yToUse = j;

							// If it is not already taken
							if (world.map.blocked(new Creep(), i, j) == false) {
								world.map.setTaken(i, j);

								if (world.map.fromEnterToExit() != null) {
									world.map.setFree(i, j);
									inIngameMenu = true;
									buildMenuUp = true;
								} else {
									world.map.setFree(i, j);
								}

							} else {
								inIngameMenu = true;
								buildMenuUp = false;
								// If tile is already occupied by a tower
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void pause() {
		state = State.Paused;
		playPause.setImg(game.loadBitmap("ui/playButton.png"));
		timeSpeed = 0;
	}

	@Override
	public void resume() {
		state = State.Running;
		playPause.setImg(game.loadBitmap("ui/pauseButton.png"));
		timeSpeed = 1;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
}
