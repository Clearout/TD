package com.example.towerdefence;

import android.media.SoundPool;

public class Sound {
	int soundId;
	SoundPool soundPool;
	
	public Sound(SoundPool soundPool, int soundId) {
		this.soundPool = soundPool;
		this.soundId = soundId;
	}
	
	public void play(float volume) {
		soundPool.play(soundId, volume, volume, 0, 0, 1);
	}
	
	public void loop(float volume) {
		soundPool.play(soundId, volume, volume, 0, -1, 1);
	}
	
	public void setVolume(float volume) {
		soundPool.setVolume(soundId, volume, volume);
	}
	
	public void stop() {
		soundPool.stop(soundId);
	}
	
	public void pause() {
		soundPool.pause(soundId);
	}
	
	public void dispose() {
		soundPool.unload(soundId);
	}
}
