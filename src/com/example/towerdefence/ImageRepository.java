package com.example.towerdefence;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.util.Log;

public class ImageRepository {
	private ArrayList<Bitmap> creepImages, towerImages;
	private ArrayList<String> creepNames, towerNames;
	private Game game;

	public ImageRepository(Game game) {
		this.game = game;
		creepImages = new ArrayList<Bitmap>();
		creepNames = new ArrayList<String>();
		towerImages = new ArrayList<Bitmap>();
		towerNames = new ArrayList<String>();
		loadImages();
	}

	private void loadImages() {
		loadImp();
		loadHound();
		loadZealot();
		loadNormalTower();
		loadFrostTower();
	}

	private void loadFrostTower() {
		towerNames.add("towers/frost/frostTower.png");
		towerImages.add(game.loadBitmap("towers/frost/frostTower.png"));
		towerNames.add("towers/frost/frostTower2.png");
		towerImages.add(game.loadBitmap("towers/frost/frostTower2.png"));
		towerNames.add("towers/frost/frostTower3.png");
		towerImages.add(game.loadBitmap("towers/frost/frostTower3.png"));
		towerNames.add("towers/frost/frostTowerProjectile.png");
		towerImages.add(game
				.loadBitmap("towers/frost/frostTowerProjectile.png"));
		towerNames.add("towers/frost/frostTowerProjectile2.png");
		towerImages.add(game
				.loadBitmap("towers/frost/frostTowerProjectile2.png"));
		towerNames.add("towers/frost/frostTowerProjectile3.png");
		towerImages.add(game
				.loadBitmap("towers/frost/frostTowerProjectile3.png"));
		towerNames.add("towers/frost/frostTowerProjectile4.png");
		towerImages.add(game
				.loadBitmap("towers/frost/frostTowerProjectile4.png"));
		towerNames.add("towers/frost/frostTowerProjectile5.png");
		towerImages.add(game
				.loadBitmap("towers/frost/frostTowerProjectile5.png"));
		towerNames.add("towers/frost/frostTowerProjectile6.png");
		towerImages.add(game
				.loadBitmap("towers/frost/frostTowerProjectile6.png"));
		towerNames.add("towers/frost/frostTowerProjectile7.png");
		towerImages.add(game
				.loadBitmap("towers/frost/frostTowerProjectile7.png"));
	}

	private void loadNormalTower() {
		towerNames.add("towers/normal/normalTower.png");
		towerImages.add(game.loadBitmap("towers/normal/normalTower.png"));
		towerNames.add("towers/normal/normalTowerLevel2.png");
		towerImages.add(game.loadBitmap("towers/normal/normalTowerLevel2.png"));
		towerNames.add("towers/normal/normalTowerLevel3.png");
		towerImages.add(game.loadBitmap("towers/normal/normalTowerLevel3.png"));
		towerNames.add("towers/normal/normalTowerProjectile.png");
		towerImages.add(game
				.loadBitmap("towers/normal/normalTowerProjectile.png"));
	}

	private void loadHound() {
		creepNames.add("creeps/hound/houndDownL.png");
		creepImages.add(game.loadBitmap("creeps/hound/houndDownL.png"));
		creepNames.add("creeps/hound/houndDownR.png");
		creepImages.add(game.loadBitmap("creeps/hound/houndDownR.png"));

		creepNames.add("creeps/hound/houndUpL.png");
		creepImages.add(game.loadBitmap("creeps/hound/houndUpL.png"));
		creepNames.add("creeps/hound/houndUpR.png");
		creepImages.add(game.loadBitmap("creeps/hound/houndUpR.png"));

		creepNames.add("creeps/hound/houndLeftL.png");
		creepImages.add(game.loadBitmap("creeps/hound/houndLeftL.png"));
		creepNames.add("creeps/hound/houndLeftR.png");
		creepImages.add(game.loadBitmap("creeps/hound/houndLeftR.png"));

		creepNames.add("creeps/hound/houndRightL.png");
		creepImages.add(game.loadBitmap("creeps/hound/houndRightL.png"));
		creepNames.add("creeps/hound/houndRightR.png");
		creepImages.add(game.loadBitmap("creeps/hound/houndRightR.png"));
	}

	private void loadZealot() {
		creepNames.add("creeps/zealot/zealotDown.png");
		creepImages.add(game.loadBitmap("creeps/zealot/zealotDown.png"));
		creepNames.add("creeps/zealot/zealotDownL.png");
		creepImages.add(game.loadBitmap("creeps/zealot/zealotDownL.png"));
		creepNames.add("creeps/zealot/zealotDownR.png");
		creepImages.add(game.loadBitmap("creeps/zealot/zealotDownR.png"));

		creepNames.add("creeps/zealot/zealotUp.png");
		creepImages.add(game.loadBitmap("creeps/zealot/zealotUp.png"));
		creepNames.add("creeps/zealot/zealotUpL.png");
		creepImages.add(game.loadBitmap("creeps/zealot/zealotUpL.png"));
		creepNames.add("creeps/zealot/zealotUpR.png");
		creepImages.add(game.loadBitmap("creeps/zealot/zealotUpR.png"));

		creepNames.add("creeps/zealot/zealotLeft.png");
		creepImages.add(game.loadBitmap("creeps/zealot/zealotLeft.png"));
		creepNames.add("creeps/zealot/zealotLeftL.png");
		creepImages.add(game.loadBitmap("creeps/zealot/zealotLeftL.png"));
		creepNames.add("creeps/zealot/zealotLeftR.png");
		creepImages.add(game.loadBitmap("creeps/zealot/zealotLeftR.png"));

		creepNames.add("creeps/zealot/zealotRight.png");
		creepImages.add(game.loadBitmap("creeps/zealot/zealotRight.png"));
		creepNames.add("creeps/zealot/zealotRightL.png");
		creepImages.add(game.loadBitmap("creeps/zealot/zealotRightL.png"));
		creepNames.add("creeps/zealot/zealotRightR.png");
		creepImages.add(game.loadBitmap("creeps/zealot/zealotRightR.png"));
	}

	private void loadImp() {
		// imp down
		creepNames.add("creeps/imp/impDown.png");
		creepImages.add(game.loadBitmap("creeps/imp/impDown.png"));
		creepNames.add("creeps/imp/impDownL.png");
		creepImages.add(game.loadBitmap("creeps/imp/impDownL.png"));
		creepNames.add("creeps/imp/impDownR.png");
		creepImages.add(game.loadBitmap("creeps/imp/impDownR.png"));
		// imp up
		creepNames.add("creeps/imp/impUp.png");
		creepImages.add(game.loadBitmap("creeps/imp/impUp.png"));
		creepNames.add("creeps/imp/impUpL.png");
		creepImages.add(game.loadBitmap("creeps/imp/impUpL.png"));
		creepNames.add("creeps/imp/impUpR.png");
		creepImages.add(game.loadBitmap("creeps/imp/impUpR.png"));
		// imp left
		creepNames.add("creeps/imp/impRight.png");
		creepImages.add(game.loadBitmap("creeps/imp/impRight.png"));
		creepNames.add("creeps/imp/impRightL.png");
		creepImages.add(game.loadBitmap("creeps/imp/impRightL.png"));
		creepNames.add("creeps/imp/impRightR.png");
		creepImages.add(game.loadBitmap("creeps/imp/impRightR.png"));
		// imp right
		creepNames.add("creeps/imp/impLeft.png");
		creepImages.add(game.loadBitmap("creeps/imp/impLeft.png"));
		creepNames.add("creeps/imp/impLeftL.png");
		creepImages.add(game.loadBitmap("creeps/imp/impLeftL.png"));
		creepNames.add("creeps/imp/impLeftR.png");
		creepImages.add(game.loadBitmap("creeps/imp/impLeftR.png"));
	}

	public Bitmap getCreepImage(String name) {
		for (int i = 0; i < creepNames.size(); i++) {
			if (creepNames.get(i).equals(name)) {
				return creepImages.get(i);
			}
		}
		return null;
	}

	public Bitmap getTowerImage(String name) {
		for (int i = 0; i < towerNames.size(); i++) {
			if (towerNames.get(i).equals(name)) {
				return towerImages.get(i);
			}
		}
		throw new NullPointerException("Couldn't find image with name " + name);
	}
}
