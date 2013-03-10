package unit;

import com.example.towerdefence.Game;

import pathfinder.Mover;
import pathfinder.Path;
import pathfinder.Path.Step;
import World.Map;
import World.World;
import android.graphics.Bitmap;

public class Creep implements Mover, Unit {
	enum Direction { North, East, South, West }
	public int xPos, yPos, life, fullLife, imageCounter, stepCounter, goldReward;
	public float movespeed, imageXPos, imageYPos, lastXOffset, lastYOffset;
	public Bitmap[] images;
	public Bitmap activeImage;
	public Bitmap healthBar;
	public Direction direction;
	private Game game;
	private float lastTime, lastMoveTime;
	private Map map;
	private Path path;
	private boolean dead;
	private World world;
	
	public Creep() {}
	
	public Creep(Game game, World world, Map map, int xPos, int yPos, int life, int goldReward,
			float movespeed) {
		this.world = world;
		this.xPos = xPos;
		this.yPos = yPos;
		this.life = life;
		this.goldReward = goldReward;
		fullLife = life;
		this.game = game;
		imageCounter = 0;
		lastTime = 0;
		this.movespeed = movespeed;
		activeImage = images[0];
		lastXOffset = 0;
		lastYOffset = 0;
		this.map = map;
		dead = false;
	}
	
	public void animate(float deltaTime) {
		float startTime = System.nanoTime();
		lastTime += deltaTime;
		if (lastTime > game.oneSecond / 5) {
			imageCounter++;
			imageCounter %= images.length;
			activeImage = images[imageCounter];
			lastTime = System.nanoTime() - startTime;
		}
	}
	public int calcImgXPos(float deltaTime) {
		imageXPos = xPos * 72 + movespeed*deltaTime + lastXOffset;
		lastXOffset = movespeed*deltaTime;
		return (int)imageXPos;
	}
	public int calcImgYPos(float deltaTime) {
		imageYPos = yPos * 72 + 108 + movespeed*deltaTime + lastYOffset;
		lastYOffset = movespeed*deltaTime;
		return (int)imageYPos;
	}
	public void drawHealthBar() {
		game.drawBitmap(healthBar, xPos, yPos + activeImage.getHeight()/2, 0, 0, 
				healthBar.getWidth() * (life / fullLife),  healthBar.getHeight() * (life / fullLife));
	}

	@Override
	public void render(float deltaTime) {
		animate(deltaTime);
		game.drawBitmap(activeImage, calcImgXPos(deltaTime), calcImgYPos(deltaTime), 0, 0, activeImage.getWidth(), activeImage.getHeight());
		drawHealthBar();
	}
	public void move(float deltaTime) {
		float startTime = System.nanoTime();
		lastMoveTime += deltaTime;
		if (lastMoveTime > (72 / movespeed) * game.oneSecond) {
			move();
			lastMoveTime = System.nanoTime() - startTime;
		}
	}
	public void move() {
		if (map.pathNotValid()) {
			path = map.findPath(xPos, yPos, map.exit.x, map.exit.y);
			stepCounter = 0;
		}
		Step step = path.getStep(stepCounter);
		int oldX = xPos;
		int oldY = yPos;
		xPos = step.getX();
		yPos = step.getY();
		updateDirection(oldX, oldY, xPos, yPos);
		stepCounter++;
	}
	public void updateDirection(int oldX, int oldY, int xPos, int yPos) {
		if (xPos > oldX) 
			direction = Direction.East; 
		else if (xPos < oldX)
			direction = Direction.West;
		else if (yPos > oldY)
			direction = Direction.South;
		else 
			direction = Direction.North;
		chooseImageSet();
	}
	public void chooseImageSet() {
		
	}
	public void takeDamage(int damage) {
		life -= damage;
		if (life <= 0)
			die();
	}
	public void die() {
		dead = true;
		world.getGold(goldReward);
	}
	public boolean isDead() {
		return dead;
	}
	@Override
	public void update(float deltaTime) {
		move(deltaTime);
		
	}
}
