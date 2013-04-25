/*******************************************************************************
 * Copyright 2011 Mario Zechner
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.example.towerdefence;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import touch.MultiTouchHandler;
import touch.Pool;
import touch.TouchEvent;
import touch.TouchEventPool;
import touch.TouchHandler;
import ui.Screen;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

/**
 * <p>
 * The Game class is responsible for handling life-cycle events, provide methods
 * to draw to the screen, receive user input and set up a main loop thread. It
 * manages a {@link Screen}, calling its methods on the main loop thread where
 * appropriate.
 * </p>
 * <p>
 * The Game class is an {@link Activity}, just derrive from it and implement the
 * {@link GameByMe#createStartScreen()} method, which should return an instance
 * of a Screen implementation that represents the first screen in your game
 * </p>
 * <p>
 * The Game class will setup an offscreen rendering surface with dimensions
 * 480x320 pixels (landscape mode) or 320x480 pixels (portrait mode) on which
 * all drawing commands get executed. When the current frame is done, this
 * offscreen surface is blitted to the SurfaceView, and stretched when
 * necessary. This means that you can work in the same coordinate system on all
 * devices, no matter their resolution. The downside is that your game might get
 * stretched a little if the aspect ratio of the target resolution is not equal
 * to the aspect ratio of the device's screen. You can set a new target
 * resolution at any time via {@link GameByMe#setOffscreenSurface(int, int)}.
 * </p>
 * <p>
 * All touch event coordinates will be reported in the coordinate system of the
 * offscreen surface for consistency.
 * </p>
 * .
 * 
 * @author mzechner
 * 
 */
public abstract class Game extends Activity implements Runnable {
	/** the WakeLock used to keep the display lit **/
	private WakeLock wakeLock;
	/** reference to the main loop thread **/
	private Thread mainLoopThread;
	/** the current state of the Game instance **/
	private State state = State.Paused;
	/**
	 * a list of State changes reported on the UI thread, processed on the main
	 * loop thread
	 **/
	private List<State> stateChanges;
	/**
	 * the SurfaceView the Game renders the final image at the end of each frame
	 **/
	private SurfaceView surfaceView;
	/**
	 * the SurfaceHolder of the SurfaceView, used to get a Canvas instance to
	 * draw to the SurfaceView
	 **/
	private SurfaceHolder surfaceHolder;
	/** the currently active Screen, can be null **/
	private Screen screen;
	/** the Canvas used to draw to the offscreen surface **/
	private Canvas canvas = null;
	/**
	 * the offscreen surface on which all drawing commands get executed. Will be
	 * blitted to the SurfaceView at the end of each frame
	 **/
	private Bitmap offscreenSurface;
	/** reference to the TouchHandler **/
	private TouchHandler touchHandler;
	/** a TouchEventPool to create and recycle TouchEvent instances **/
	private TouchEventPool touchEventPool = new TouchEventPool();
	/**
	 * the list of TouchEvents returned by the Game#getTouchEvents() method on
	 * the main loop thread
	 **/
	private List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
	/**
	 * the buffer of TouchEvent instances, filled on the UI thread and copied to
	 * the touchEvents list on the main loop thread
	 **/
	private List<TouchEvent> touchEventBuffer = new ArrayList<TouchEvent>();
	/** the SoundPool instance used for all sound effects **/
	private SoundPool soundPool;
	/** the number of frames per second **/
	public int framesPerSecond = 0;

	public long oneSecond = 1000000000;
	public ImageRepository imageRepository;
	@SuppressWarnings("unused")
	private SoundRepository sr;
	public Score scores;
	public float soundVolume;

	/**
	 * Implementation of the Activity's onCreate() method. Sets up the
	 * {@link WakeLock}, turns on fullscreen rendering, creates the SurfaceView
	 * and offscreen surface {@link Bitmap} and registers the key-, touch- and
	 * accelerometer callback interfaces. Also sets the audio stream controlled
	 * by the volume buttons, creates the SoundPool instances used and finally
	 * calls the {@link GameByMe#createStartScreen()} method to fetch the first
	 * Screen to be active.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle instanceBundle) {
		super.onCreate(instanceBundle);
		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager
				.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Game");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		surfaceView = new SurfaceView(this);
		setContentView(surfaceView);
		surfaceHolder = surfaceView.getHolder();

		if (surfaceView.getWidth() > surfaceView.getHeight()) {
			setOffscreenSurface(1280, 720);
		} else {
			setOffscreenSurface(720, 1280);
		}

		surfaceView.setFocusableInTouchMode(true);
		surfaceView.requestFocus();

		// if(Integer.parseInt(VERSION.SDK) < 5)
		// touchHandler = new SingleTouchHandler(surfaceView, touchEventBuffer,
		// touchEventPool);
		// else
		// touchHandler = new MultiTouchHandler(surfaceView, touchEventBuffer,
		// touchEventPool);
		//
		touchHandler = new MultiTouchHandler(surfaceView, touchEventBuffer,
				touchEventPool);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.soundPool = new SoundPool(100, AudioManager.STREAM_MUSIC, 0);
		imageRepository = new ImageRepository(this);
		scores = new Score(this);
		sr = new SoundRepository(this);
		soundVolume = 1.0f;
		screen = createStartScreen();
	}

	/**
	 * The main loop thread code, implemented as a {@link Runnable}. Reports any
	 * life-cycle events to the currently active Screen, copies the event
	 * buffers to the event lists returned in the
	 * {@link GameByMe#getKeyEvents()} and {@link GameByMe#getTouchEvents()}
	 * methods, updates the current Screen and measures the delta time and frame
	 * rate. Also responsible for blitting the offscreen surface {@link Bitmap}
	 * to the {@link SurfaceView}, performing automatic stretching if necessary
	 * (e.g. offscreen surface dimensions are not equal to SurfaceView
	 * dimensions).
	 */
	@Override
	public void run() {
		int frames = 0;
		long startTime = System.nanoTime();
		long lastTime = System.nanoTime();
		while (true) {
			synchronized (stateChanges) {
				for (int i = 0; i < stateChanges.size(); i++) {
					state = stateChanges.get(i);
					if (state == State.Disposed) {
						if (screen != null)
							screen.dispose();
						Log.d("Game", "disposed");
						return;
					}
					if (state == State.Paused) {
						if (screen != null)
							screen.pause();
						Log.d("Game", "paused");
						return;
					}
					if (state == State.Resumed) {
						state = State.Running;
						if (screen != null)
							screen.resume();
						Log.d("Game", "resumed");
					}
				}
				stateChanges.clear();
			}

			if (state == State.Running) {
				if (!surfaceHolder.getSurface().isValid())
					continue;
				Canvas canvas = surfaceHolder.lockCanvas();
				fillEvents();
				long currTime = System.nanoTime();
				float deltaTime = (currTime - lastTime) / 1000000000.0f;
				if (screen != null)
					screen.update(deltaTime);
				lastTime = currTime;
				freeEvents();
				src.left = 0;
				src.top = 0;
				src.right = offscreenSurface.getWidth() - 1;
				src.bottom = offscreenSurface.getHeight() - 1;
				dst.left = 0;
				dst.top = 0;
				dst.right = surfaceView.getWidth();
				dst.bottom = surfaceView.getHeight();
				canvas.drawBitmap(offscreenSurface, src, dst, null);
				surfaceHolder.unlockCanvasAndPost(canvas);
			}

			frames++;
			if ((System.nanoTime() - startTime) > 1000000000) {
				framesPerSecond = frames;
				frames = 0;
				startTime = System.nanoTime();
			}
		}
	}

	/**
	 * Helper method to copy the events from the event buffers to the event
	 * lists returned in the {@link GameByMe#getKeyEvents()} and
	 * {@link GameByMe#getTouchEvents()} methods. Synchronizes on the buffers so
	 * that the handlers on the UI thread can't concurrently access them.
	 */
	private void fillEvents() {
		synchronized (touchEventBuffer) {
			for (int i = 0; i < touchEventBuffer.size(); i++) {
				touchEvents.add(touchEventBuffer.get(i));
			}
			touchEventBuffer.clear();
		}
	}

	/**
	 * Helper method to recycle the events in the event lists at the end of a
	 * frame by putting them back into their respective {@link Pool}s.
	 * Synchronized on the buffers so that the handlers on the UI thread can't
	 * concurrently access the Pools.
	 */
	private void freeEvents() {
		synchronized (touchEventBuffer) {
			for (int i = 0; i < touchEvents.size(); i++) {
				touchEventPool.free(touchEvents.get(i));
			}
			touchEvents.clear();
		}
	}

	/**
	 * Implementation of the Activity onResume() method. Acquires the
	 * {@link WakeLock} and starts the main loop thread.
	 */
	@Override
	public void onResume() {
		super.onResume();
		wakeLock.acquire();

		stateChanges = new ArrayList<State>();
		mainLoopThread = new Thread(this);
		mainLoopThread.start();
		synchronized (stateChanges) {
			stateChanges.add(stateChanges.size(), State.Resumed);
		}
	}

	/**
	 * Implementation of the Activitiy onPause() method. Releases the
	 * {@link WakeLock} and stops the main loop thread. Also releases the
	 * {@link SoundPool} and unregisters the Game from the {@link SensorManager}
	 * in case the application is destroyed (isFinishing()).
	 */
	@Override
	public void onPause() {
		super.onPause();
		wakeLock.release();

		synchronized (stateChanges) {
			if (isFinishing()) {
				stateChanges.add(stateChanges.size(), State.Disposed);
			} else {
				stateChanges.add(stateChanges.size(), State.Paused);
			}
		}
		try {
			mainLoopThread.join();
		} catch (InterruptedException e) {
		}
		;

		if (isFinishing())
			soundPool.release();
	}

	/**
	 * Sets the currently active {@link Screen}. The old Screen's dispose()
	 * method will be called.
	 * 
	 * @param screen
	 *            the new active Screen.
	 */
	public void setScreen(Screen screen) {
		if (this.screen != null)
			screen.dispose();
		this.screen = screen;
	}

	/**
	 * To be implemented by any Game. Should return the first active
	 * {@link Screen} of the {@link GameByMe}.
	 * 
	 * @return the first active Screen.
	 */
	public abstract Screen createStartScreen();

	/**
	 * Sets the size of the offscreen surface Bitmap. All drawing will be
	 * performed in this new coordinate system, touch event coordinates will be
	 * adjusted appropriately.
	 * 
	 * @param width
	 *            the width of the offscreen surface in pixels.
	 * @param height
	 *            the height of the offscreen surface in pixels.
	 */
	public void setOffscreenSurface(int width, int height) {
		if (offscreenSurface != null)
			offscreenSurface.recycle();
		offscreenSurface = Bitmap.createBitmap(width, height, Config.RGB_565);
		canvas = new Canvas(offscreenSurface);
	}

	/**
	 * Loads a new {@link Bitmap} from the asset given by the file name. The
	 * file name must be relative to the assets folder of the Android project.
	 * Throws a RuntimeException in case the Bitmap could not be loaded.
	 * 
	 * @param fileName
	 *            the file name of the Bitmap.
	 * @return the Bitmap.
	 */
	public Bitmap loadBitmap(String fileName) {
		InputStream in = null;
		Bitmap bitmap = null;
		try {
			in = getAssets().open(fileName);
			bitmap = BitmapFactory.decodeStream(in);
			if (bitmap == null)
				throw new RuntimeException("Couldn't load bitmap from asset '"
						+ fileName + "'");
			return bitmap;
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load bitmap from asset '"
					+ fileName + "'");
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
				}
		}
	}

	/**
	 * Loads a {@link Music} instance from the asset given by the file name. The
	 * file name must be relative to the assets folder of the Android project.
	 * Throws a RuntimeException in case the Music could not be loaded.
	 * 
	 * @param fileName
	 *            the file name.
	 * @return the Music instance.
	 */
	public Music loadMusic(String fileName) {
		try {
			AssetFileDescriptor assetDescriptor = getAssets().openFd(fileName);
			return new Music(assetDescriptor);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load music '" + fileName + "'");
		}
	}

	/**
	 * Lodas a {@link Sound} instance from the asset given by the file name. The
	 * file name must be relative to the assets folder of the Android project.
	 * Throws a RuntimeException in case the Sound could not be loaded.
	 * 
	 * @param fileName
	 *            the file name.
	 * @return the Sound instance.
	 */
	public Sound loadSound(String fileName) {
		try {
			AssetFileDescriptor assetDescriptor = getAssets().openFd(fileName);
			int soundId = soundPool.load(assetDescriptor, 0);
			return new Sound(soundPool, soundId);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load sound '" + fileName + "'");
		}
	}

	/**
	 * Clears the framebuffer with the given color. You can use predefined
	 * colors via the {@link Color} class. The color must be encoded as a 32-bit
	 * ARGB color.
	 * 
	 * @param color
	 *            the color.
	 */
	public void clearFramebuffer(int color) {
		if (canvas != null)
			canvas.drawColor(color);
	}

	/**
	 * @return the framebuffer's width in pixels.
	 */
	public int getFramebufferWidth() {
		return offscreenSurface.getWidth();
	}

	/**
	 * @return the framebuffer's height in pixels.
	 */
	public int getFramebufferHeight() {
		return offscreenSurface.getHeight();
	}

	/**
	 * @return the framerate.
	 */
	public int getFramerate() {
		return framesPerSecond;
	}

	/**
	 * Draws the given {@link Bitmap} to the framebuffer, with x and y
	 * specifying where in the framebuffer the upper left corner of the Bitmap
	 * should be.
	 * 
	 * @param bitmap
	 *            the Bitmap.
	 * @param x
	 *            the x-coordinate.
	 * @param y
	 *            the y-coordinate.
	 */
	public void drawBitmap(Bitmap bitmap, int x, int y) {
		if (canvas != null)
			canvas.drawBitmap(bitmap, x, y, null);
	}

	Rect src = new Rect();
	Rect dst = new Rect();

	/**
	 * Draws a region defined by srcX/srcY/srcWidth/srcHeight of the given
	 * {@link Bitmap} to the framebuffer. The x and y parameters specify where
	 * in the framebuffer the upper left corner of the region should be.
	 * 
	 * @param bitmap
	 *            the Bitmap
	 * @param x
	 *            the x-coordinate.
	 * @param y
	 *            the y-coordinate.
	 * @param srcX
	 *            the x-coordinate of the upper left corner of the region in the
	 *            Bitmap.
	 * @param srcY
	 *            the y-coordinate of the upper left corner of the region in the
	 *            Bitmap.
	 * @param srcWidth
	 *            the width of the region in pixels.
	 * @param srcHeight
	 *            the height of the region in pixels.
	 */
	public void drawBitmap(Bitmap bitmap, int x, int y, int srcX, int srcY,
			int srcWidth, int srcHeight) {
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

	Paint paint = new Paint();

	/**
	 * Draws the given text, using the given {@link Typeface}, color and size.
	 * The x- and y-coordinate specify the upper left corner in the framebuffer
	 * of the left most character in the string.
	 * 
	 * @param font
	 *            the Typeface.
	 * @param text
	 *            the text to draw.
	 * @param x
	 *            the x-coordinate of the upper left corner of the text.
	 * @param y
	 *            the y-coordinate of the upper left corner of the text
	 * @param col
	 *            the color, encoded as a 32-bit ARGB color (see {@link Color}).
	 * @param size
	 *            the size in pixel of the text.
	 */
	public void drawText(Typeface font, String text, int x, int y, int col,
			int size) {
		paint.setTypeface(font);
		paint.setTextSize(size);
		paint.setColor(col);
		canvas.drawText(text, x, y + size, paint);
	}

	/**
	 * @param pointer
	 *            the pointer ID of the finger in question.
	 * @return whether the pointer is down.
	 */
	public boolean isTouchDown(int pointer) {
		return touchHandler.isTouchDown(pointer);
	}

	/**
	 * @param pointer
	 *            the pointer ID of the finger in question.
	 * @return the x-coordinate of the pointer.
	 */
	public int getTouchX(int pointer) {
		return (int) (touchHandler.getTouchX(pointer)
				/ (float) surfaceView.getWidth() * offscreenSurface.getWidth());
	}

	/**
	 * @param pointer
	 *            the pointer ID of the finger in question.
	 * @return the y-coordinate of the pointer.
	 */
	public int getTouchY(int pointer) {
		return (int) (touchHandler.getTouchY(pointer)
				/ (float) surfaceView.getHeight() * offscreenSurface
					.getHeight());
	}

	/**
	 * @return the list of {@link TouchEvent}s since the last frame.
	 */
	public List<TouchEvent> getTouchEvents() {
		return touchEvents;
	}

	@Override
	public void onStop() {
		super.onStop();
		scores.saveScores();
	}

}
