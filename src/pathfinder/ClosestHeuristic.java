package pathfinder;

public class ClosestHeuristic implements AStarHeuristic {

	@Override
	public float getCost(TileBasedMap map, Mover mover, int x, int y, int tx, int ty) {
		return Math.abs(x - tx) + Math.abs(y - ty);
	}

}
