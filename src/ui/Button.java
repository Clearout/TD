package ui;

import com.example.towerdefence.Game;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Button {
	private Bitmap image;
	private int x, y;
	private Rect bounds;
	private Game game;
	
	public Button(Game game, String path, int x, int y) {
		this.game = game;
		this.x = x;
		this.y = y;
		image = game.loadBitmap(path);
		bounds = new Rect(x, y, x + w(), y + h());
	}
	
	public void sx(int x) { this.x = x; }
	
	public void sy(int y) { this.y = y; }
	
	public int gx() { return x; }
	
	public int gy() { return y; }
	
	public int w() { return image.getWidth(); }
	
	public int h() { return image.getHeight(); }
	
	public void draw() { game.drawBitmap(image, x, y); }
	
	public Bitmap getImg() { return image; }
	
	public void setImg(Bitmap image) { 
		this.image = image;
		bounds = new Rect(x, y, x + w(), y + h());
	}
	
	public boolean touched() {
		int tx = game.getTouchX(0);
		int ty = game.getTouchY(0);
		Rect r = new Rect(tx, ty, tx+1, ty+1);
		return r.intersect(bounds) && game.isTouchDown(0);
	}
}
