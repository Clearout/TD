package com.example.towerdefence;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Game extends Activity implements Runnable {
	private WakeLock wakeLock;
	private Thread mainLoopThread;
	private State state = State.Paused;
	private ArrayList<State> stateChanges = new ArrayList<State>();
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	private Screen screen;
	private Canvas canvas = null;
	Rect src = new Rect();
	Rect dst = new Rect();
	private Bitmap offscreenSurface;
	private int offscreenWidth, offscreenHeight;
	private TouchHandler touchHandler;
	private TouchEventPool touchEventPool = new TouchEventPool();
	private ArrayList<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
	private ArrayList<TouchEvent> touchEventBuffer = new ArrayList<TouchEvent>();
	private SoundPool soundPool;
	private int fps = 0;

	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Game");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		surfaceView = new SurfaceView(this);
		setContentView(surfaceView);
		surfaceHolder = surfaceView.getHolder();
		screen = createStartScreen();
		setOffscreenSurfaceOrientation();
		if (Integer.parseInt(VERSION.SDK)< 5)
			touchHandler = new SingleTouchHandler(surfaceView, touchEventBuffer, touchEventPool);
		else
			touchHandler = new MultiTouchHandler(surfaceView, touchEventBuffer, touchEventPool);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
	}
	
	public Screen createStartScreen() {
		return null;
	}
	
	public void setScreen(Screen screen) {
		if (this.screen != null)
			screen.dispose();
		this.screen = screen;
	}

	public void onPause() {
		super.onPause();
		wakeLock.release();

		synchronized (stateChanges) {
			if (isFinishing()) {
				stateChanges.add(stateChanges.size(), State.Disposed);
				soundPool.release();
			} 
			else {
				stateChanges.add(stateChanges.size(), State.Paused);
			}
		}
		try {
			mainLoopThread.join();
		} catch (InterruptedException e) {};
	}

	public void onResume() {
		super.onResume();
		wakeLock.acquire();
	}

	public void run() {
		int frames = 0;
		long startTime = System.nanoTime();	
		long lastTime = System.nanoTime();
		while(true) {
			synchronized (stateChanges) {
				for (int i=0; i<stateChanges.size(); i++) {
					state = stateChanges.get(i);
					if (state == State.Disposed) {
						screen.dispose();
						Log.d("Game", "disposed");
						return;
					}
					if (state == State.Paused) {
						screen.pause();
						Log.d("Game", "paused");
						return;
					}
					if (state == State.Resumed) {
						screen.resume();
						Log.d("Game", "resumed");
					}
				}
				stateChanges.clear();
			}
			if (state == State.Running) {
				if (!surfaceHolder.getSurface().isValid()) 
					continue;
				canvas = surfaceHolder.lockCanvas();
				fillEvents();
				long currentTime = System.nanoTime();
				if (screen != null) 
					screen.update((currentTime - lastTime) / 1000000000.0f);
				lastTime = currentTime;
				freeEvents();
				src.left = 0;
				src.top = 0;
				src.right = offscreenSurface.getWidth() - 1;
				src.bottom = offscreenSurface.getHeight() - 1;
				dst.left = 0;
				dst.top = 0;
				dst.right = offscreenSurface.getWidth();
				dst.bottom = offscreenSurface.getHeight();
				canvas.drawBitmap(offscreenSurface, src, dst, null);
				surfaceHolder.unlockCanvasAndPost(canvas);
			}
			frames++;
			// 1 second
			if (System.nanoTime()-startTime > 1000000000) {
				fps = frames;
				frames = 0;
				startTime = System.nanoTime();
			}
		}
	}
	
	public int getFrameRate() {
		return fps;
	}
	
	public void clearFrameBuffer(int color) {
		if (canvas != null)
			canvas.drawColor(color);
	}
	
	public int getFrameBufferWidth() { return offscreenSurface.getWidth(); }
	public int getFrameBufferHeight() { return offscreenSurface.getHeight(); }
	
	public Bitmap loadBitmap(String fileName) {
		InputStream in = null;
		Bitmap bitmap = null;
		try {
			in = getAssets().open(fileName);
			bitmap = BitmapFactory.decodeStream(in);
			if (bitmap == null)
				throw new RuntimeException ("Couldnt find the file from asset " + fileName);
			return bitmap;
		} catch(IOException e) {
			throw new RuntimeException ("Couldnt find the file from asset " + fileName);
		} finally{
			if (in != null) try { in.close(); } catch(IOException e) {}
		}
	}
	
	public void drawBitmap(Bitmap bitmap, int x, int y) {
		if (canvas != null) 
			canvas.drawBitmap(bitmap, x, y, null);
	}
	
	public void drawBitmap(Bitmap bitmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight) {
		if (canvas == null)
			return;
		src.left = srcX;
		src.top = srcY;
		src.right = srcX + srcWidth;
		src.bottom = srcY + srcHeight;
		
		dst.left = x;
		dst.top = y;
		dst.right = x + srcWidth;
		dst.bottom = y + srcHeight;
		
		canvas.drawBitmap(bitmap, src, dst, null);
	}
	
	public void setOffscreenSurfaceOrientation() {
		if (surfaceView.getWidth() > surfaceView.getHeight()) {
			offscreenWidth = 1280;
			offscreenHeight = 720;
		} else {
			offscreenWidth = 720;
			offscreenHeight = 1280;
		}
		setOffscreenSurface(offscreenWidth, offscreenHeight);
	}
	
	public void setOffscreenSurface(int width, int height) {
		if (offscreenSurface != null) 
			offscreenSurface.recycle();
		offscreenSurface = Bitmap.createBitmap(width, height, Config.RGB_565);
		canvas = new Canvas(offscreenSurface);
	}
	
	public boolean isTouchDown(int pointer) {
		return touchHandler.isTouchDown(pointer);
	}
	
	public int getTouchX(int pointer) {
		return (int)((touchHandler.getTouchX(pointer)/(float)surfaceView.getWidth())*offscreenSurface.getWidth());
	}
	
	public int getTouchY(int pointer) {
		return (int)((touchHandler.getTouchY(pointer)/(float)surfaceView.getHeight())*offscreenSurface.getHeight());
	}
	
	private void fillEvents() {
		synchronized(touchEventBuffer) {
			for (int i=0; i<touchEventBuffer.size(); i++) {
				touchEvents.add(touchEventBuffer.get(i));
			}
			touchEventBuffer.clear();
		}
	}
	
	private void freeEvents() {
		synchronized(touchEventBuffer) {
			for (int i=0; i<touchEvents.size(); i++) {
				touchEventPool.free(touchEvents.get(i));
			}
			touchEvents.clear();
		}
	}
	
	public Sound loadSound(String fileName) {
		try {
			AssetFileDescriptor assetDescriptor = getAssets().openFd(fileName);
			int soundId = soundPool.load(assetDescriptor, 0);
			return new Sound(soundPool, soundId);
		} catch (IOException e) {
			throw new RuntimeException("Couldnt load sound " + fileName);
		}
	}
	
	public Music loadMusic(String fileName) {
		try {
			AssetFileDescriptor assetDescriptor = getAssets().openFd(fileName);
			return new Music(assetDescriptor);
		} catch (IOException e) {
			throw new RuntimeException("Couldnt load music " + fileName);
		}
	}
}

















