package com.example.towerdefence;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;

public class Game extends Activity implements Runnable {
	private WakeLock wakeLock;
	private Thread mainLoopThread;
	private State state = State.Paused;
	private ArrayList<State> stateChanges = new ArrayList<State>();
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	private Screen screen;

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
	}

	public void onPause() {
		super.onPause();
		wakeLock.release();

		synchronized (stateChanges) {
			if (isFinishing()) {
				stateChanges.add(stateChanges.size(), State.Disposed);
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
		while(true) {
			synchronized (stateChanges) {
				for (int i=0; i<stateChanges.size(); i++) {
					state = stateChanges.get(i);
					if (state == State.Disposed) {
						Log.d("Game", "disposed");
						return;
					}
					if (state == State.Paused) {
						Log.d("Game", "paused");
						return;
					}
					if (state == State.Resumed) {
						Log.d("Game", "resumed");
					}
				}
				stateChanges.clear();
			}
			if (state == State.Running) {
				if (!surfaceHolder.getSurface().isValid()) 
					continue;
				Canvas canvas = surfaceHolder.lockCanvas();
				
				surfaceHolder.unlockCanvasAndPost(canvas);
				
			}
		}
	}

}
