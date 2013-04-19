package unit;

import com.example.towerdefence.Game;

import pathfinder.Mover;
import pathfinder.Path;
import pathfinder.Path.Step;
import world.Map;
import world.World;
import android.graphics.Bitmap;
import android.util.Log;

public class Creep implements Mover, Unit {
	enum Direction {
		North, East, South, West
	}

	public int xPos, yPos, life, fullLife, imageCounter, stepCounter,
			goldReward, prevX, prevY;
	public float movespeed, imageXPos, imageYPos, lastXOffset, lastYOffset,
			speedEffect;
	protected String[] down, up, right, left;
	protected Bitmap[] activeImages;
	public Bitmap activeImage;
	public Bitmap healthBar;
	public Direction direction;
	private Game game;
	private float lastTime, lastMoveTime, effectTimeLeft;
	private Map map;
	private Path path;
	private boolean dead, firstMove;
	private World world;

	public Creep() {
	}

	public Creep(Game game, World world, Map map, int life, int goldReward,
			float movespeed) {
		up = new String[4];
		down = new String[4];
		left = new String[4];
		right = new String[4];
		this.world = world;
		xPos = map.enterNode.x;
		yPos = map.enterNode.y;
		prevX = xPos;
		prevY = yPos;
		imageXPos = xPos * 72;
		imageYPos = yPos * 72 + 108;
		this.life = life;
		if (life == 0)
			life++;
		this.goldReward = goldReward;
		fullLife = life;
		this.game = game;
		imageCounter = 0;
		lastTime = 0;
		this.movespeed = movespeed;
		speedEffect = 1;
		firstMove = true;
		lastXOffset = 0;
		lastYOffset = 0;
		this.map = map;
		dead = false;
		path = map.findPath(xPos, yPos, map.exitNode.x, map.exitNode.y);
		stepCounter = 0;
		direction = Direction.South;
		effectTimeLeft = 0;
		healthBar = game.imageRepository.healthBar;
	}

	public void animate(float deltaTime) {
		lastTime += deltaTime;
		if (lastTime > 0.25) {
			imageCounter++;
			imageCounter %= activeImages.length;
			activeImage = activeImages[imageCounter];
			lastTime = 0;
		}
	}

	public int calcImgXPos(float deltaTime) {
		if (direction == Direction.East)
			lastXOffset += movespeed * deltaTime;
		if (direction == Direction.West)
			lastXOffset -= movespeed * deltaTime;

		imageXPos = prevX * 72 + lastXOffset;

		return (int) imageXPos;
	}

	public int calcImgYPos(float deltaTime) {
		if (direction == Direction.South)
			lastYOffset += movespeed * deltaTime;
		if (direction == Direction.North)
			lastYOffset -= movespeed * deltaTime;

		imageYPos = prevY * 72 + 108 + lastYOffset;

		return (int) imageYPos;
	}

	public void drawHealthBar() {
		double lifePercent = (double) life / (double) fullLife;
		int width = (int) (lifePercent * healthBar.getWidth());
		game.drawBitmap(healthBar, (int) imageXPos, (int) imageYPos, 0, 0,
				width, healthBar.getHeight());
	}

	public void setEffect(float effect, float duration) {
		speedEffect = effect;
		effectTimeLeft = duration;
	}

	@Override
	public void render(float deltaTime) {
		game.drawBitmap(activeImage, calcImgXPos(deltaTime * speedEffect),
				calcImgYPos(deltaTime * speedEffect));
		animate(deltaTime * speedEffect);

		drawHealthBar();

	}

	public void move(float deltaTime) {
		lastMoveTime += deltaTime;
		if (lastMoveTime > 72 / movespeed) {
			firstMove = false;
			move();
			lastMoveTime = 0;
		}
	}

	public void move() {
		if (xPos == map.exitNode.x && yPos == map.exitNode.y) {
			die();
			world.loseLife();
		} else {
			lastXOffset = 0;
			lastYOffset = 0;
			path = map.findPath(xPos, yPos, map.exitNode.x, map.exitNode.y);
			if (path.getLength() > 1) {
				prevX = xPos;
				prevY = yPos;
				xPos = path.getX(1);
				yPos = path.getY(1);
				updateDirection();
			}
		}
	}

	public void updateDirection() {
		Direction lastDir = direction;
		if (xPos > prevX)
			direction = Direction.East;
		else if (xPos < prevX)
			direction = Direction.West;
		else if (yPos > prevY)
			direction = Direction.South;
		else
			direction = Direction.North;
		if (lastDir != direction)
			chooseImageSet();
	}

	protected void chooseImageSet() {
		if (direction == Direction.South)
			activeImages = setImages(down);
		else if (direction == Direction.North)
			activeImages = setImages(up);
		else if (direction == Direction.West)
			activeImages = setImages(left);
		else
			activeImages = setImages(right);
	}

	private Bitmap[] setImages(String[] imgStr) {
		Bitmap[] img = new Bitmap[imgStr.length];
		for (int i = 0; i < img.length; i++) {
			img[i] = game.imageRepository.getCreepImage(imgStr[i]);
		}
		activeImage = img[imageCounter];
		return img;
	}

	private int getScore() {
		return (int) (5 * life + movespeed + 30 * goldReward);
	}

	public void takeDamage(int damage) {
		life -= damage;
		if (life <= 0)
			die();
	}

	public void die() {
		dead = true;
		world.addScore(getScore());
	}

	public boolean isDead() {
		return dead;
	}

	private void removeEffect() {
		speedEffect = 1;
		effectTimeLeft = 0;
	}

	@Override
	public void update(float deltaTime) {
		move(deltaTime * speedEffect);
		if (firstMove)  {
			move();
			firstMove = false;
		}
		effectTimeLeft -= deltaTime;
		if (effectTimeLeft <= 0)
			removeEffect();
	}
}
