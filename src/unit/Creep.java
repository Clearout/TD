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
			goldReward;
	public float movespeed, imageXPos, imageYPos, lastXOffset, lastYOffset;
	protected Bitmap[] images;
	public Bitmap activeImage;
	public Bitmap healthBar;
	public Direction direction;
	private Game game;
	private float lastTime, lastMoveTime;
	private Map map;
	private Path path;
	private boolean dead;
	private World world;

	public Creep() {
	}

	public Creep(Game game, World world, Map map, int life,
			int goldReward, float movespeed) {
		images = new Bitmap[1];
		this.world = world;
		xPos = map.enterNode.x;
		yPos = map.enterNode.y;
		this.life = life;
		this.goldReward = goldReward;
		fullLife = life;
		this.game = game;
		imageCounter = 0;
		lastTime = 0;
		this.movespeed = movespeed;
		lastXOffset = 0;
		lastYOffset = 0;
		this.map = map;
		dead = false;
		path = map.findPath(xPos, yPos, map.exitNode.x, map.exitNode.y);
		stepCounter = 0;
		direction = Direction.South;
	}

	public void setActiveImage() {
		activeImage = images[0];
	}

	public void animate(float deltaTime) {
		lastTime += deltaTime;
		if (lastTime > game.oneSecond / 5) {
			imageCounter++;
			imageCounter %= images.length;
			activeImage = images[imageCounter];
			lastTime = 0;
		}
	}

	public int calcImgXPos(float deltaTime) {
		if (direction == Direction.East)
			lastXOffset += movespeed * deltaTime;
		if (direction == Direction.West)
			lastXOffset -= movespeed * deltaTime;

		imageXPos = xPos * 72 + movespeed * deltaTime + lastXOffset;

		return (int) imageXPos;
	}

	public int calcImgYPos(float deltaTime) {
		if (direction == Direction.South)
			lastYOffset += movespeed * deltaTime;
		if (direction == Direction.North)
			lastYOffset -= movespeed * deltaTime;

		imageYPos = yPos * 72 + 108 + lastYOffset;

		return (int) imageYPos;
	}

	public void drawHealthBar() {
		game.drawBitmap(healthBar, xPos, yPos + activeImage.getHeight() / 2, 0,
				0, healthBar.getWidth() * (life / fullLife),
				healthBar.getHeight() * (life / fullLife));
	}

	@Override
	public void render(float deltaTime) {
		game.drawBitmap(activeImage, calcImgXPos(deltaTime),
				calcImgYPos(deltaTime));
		// drawHealthBar();
	}

	public void move(float deltaTime) {
		lastMoveTime += deltaTime;
		if (lastMoveTime > 72 / movespeed) {
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
				xPos = path.getX(1);
				yPos = path.getY(1);

				if (path.getLength() > 2) {
					int nextX = path.getX(2);
					int nextY = path.getY(2);

					updateDirection(xPos, yPos, nextX, nextY);
					stepCounter++;
				}
			}
		}
	}

	public void updateDirection(int x, int y, int nextX, int nextY) {
		if (nextX > x)
			direction = Direction.East;
		else if (nextX < x)
			direction = Direction.West;
		else if (nextY > y)
			direction = Direction.South;
		else
			direction = Direction.North;
		chooseImageSet();
	}

	public void chooseImageSet() {

	}
	
	private int getScore() {
		return (int)(5*life + movespeed + 3*goldReward);
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

	@Override
	public void update(float deltaTime) {
		move(deltaTime);

	}
}
