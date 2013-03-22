package ui;

import unit.Tower;

import com.example.towerdefence.Game;

import android.graphics.Bitmap;
import android.util.Log;

public class Icon {
	private Bitmap image;
	private int x, y;
	private Game game;
	private Tower tower;
	
	public Icon(Game game, String path, int x, int y) {
		image = game.loadBitmap(path);
		this.game = game;
		this.x = x;
		this.y = y;
	}
	
	
	public void setTower(Tower t) { tower = t; }
	public Tower tower() {
		if (tower == null)
			return null;
		return tower;
	}
	public void sx(int sx) { x = sx; }
	public void sy(int sy) { y = sy; }
	public int x() { return x; }
	public int y() { return y; }
	public int w() { return image.getWidth(); }
	public int h() { return image.getHeight(); }
	
	public void draw() {
		game.drawBitmap(image, x(), y());
	}
	
	public boolean touched() {
		if (game.getTouchX(0) >= x() && game.getTouchX(0) <= x() + w() && 
				game.getTouchY(0) >= y() && game.getTouchY(0) <= y() + h() && game.isTouchDown(0)) 
			return true;
		return false;
	}
}
