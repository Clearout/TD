package com.example.towerdefence;

import android.graphics.Bitmap;

public class Unit {
	enum Direction { North, East, South, West }
	public int xPos, yPos, life, imageCounter;
	public float movespeed, imageXPos, imageYPos;
	public Bitmap[] images;
	public Bitmap activeImage;
	public Direction direction;
	
	public Unit() {}
	
	public Unit(Direction direction, int xPos, int yPos, int life,
			float movespeed, Bitmap[] images) {
		this.direction = direction;
		this.xPos = xPos;
		this.yPos = yPos;
		this.life = life;
		imageCounter = 0;
		this.movespeed = movespeed;
		this.images = images;
		activeImage = images[0];
	}
	
	public void animate() {
		imageCounter++;
		imageCounter %= images.length;
		activeImage = images[imageCounter];
	}
	
}
