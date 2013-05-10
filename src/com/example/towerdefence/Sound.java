package com.example.towerdefence;

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
import android.media.SoundPool;
/**
 * Class abstracing a sound effect stored in a {@link SoundPool}. An instance
 * of this class holds a reference to the SoundPool as well as the ID of the
 * sound effect in the SoundPool for playback. Call the {@link Sound#dispose()}
 * method when you no longer need the sound effect to clean up resources.
 * 
 * @author mzechner
 *
 */
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
	
	public void play(float volume, int priority) {
		soundPool.play(soundId, volume, volume, priority, 0, 1);
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
