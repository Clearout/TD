package world;

import java.util.ArrayList;
import unit.Creep;

public class Wave {
	public ArrayList<Creep> creeps;
	public int size;
	public float time;

	public Wave(ArrayList<Creep> creeps, float time) {
		this.creeps = creeps;
		this.time = time;
		size = creeps.size();
	}

	public Creep getCreep() {
		if (creeps.size() > 0) {
			return creeps.remove(0);
		}
		return null;
	}
}
