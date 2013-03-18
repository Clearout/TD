package World;

import pathfinder.AStarPathFinder;
import pathfinder.AStarPathFinder.Node;
import pathfinder.Mover;
import pathfinder.Path;
import pathfinder.TileBasedMap;
import unit.Creep;
import android.util.Log;

public class Map implements TileBasedMap {
	public final int FREE, TAKEN, ENTER, EXIT, X_LENGTH, Y_LENGTH;
	int[][] area;
	public Node enterNode;
	public Node exitNode;
	private AStarPathFinder pathfinder;
	private boolean[][] visited;
	private boolean validPathExists;

	public Map(int enterX, int enterY, int exitX, int exitY) {
		FREE = 0;
		TAKEN = 1;
		ENTER = 2;
		EXIT = 3;
		X_LENGTH = 10;
		Y_LENGTH = 14;
		pathfinder = new AStarPathFinder(this, 1000, false);
		area = new int[X_LENGTH][Y_LENGTH];
		visited = new boolean[X_LENGTH][Y_LENGTH];
		validPathExists = false;
		for (int i=0; i < X_LENGTH; i++) {
			for (int j=0; j < Y_LENGTH; j++) {
				area[i][j] = FREE;
			}
		}
		area[enterX][enterY] = ENTER;
		enterNode = pathfinder.makeNode(enterX, enterY);
		area[exitX][exitY] = EXIT;
		exitNode = pathfinder.makeNode(exitX, exitY);
	}

	public void setTaken(int x, int y) {
		if (x >= 0 && x < X_LENGTH && y >= 0 && y < Y_LENGTH) {
			if (area[x][y] != TAKEN || area[x][y] != ENTER || area[x][y] != EXIT) {
				area[x][y] = TAKEN;
			} else {
				Log.d("Area already taken", "Map.setTaken(), area with x=" + x + " y=" + y + " is taken");
			}
		} else {
			Log.d("Out of bounds" , "Map.setTaken() out of bounds with x=" + x + " y=" + y);
		}
	}

	public void setFree(int x, int y) {
		if (x >= 0 && x < X_LENGTH && y >= 0 && y < Y_LENGTH) {
			if (area[x][y] != FREE || area[x][y] != ENTER || area[x][y] != EXIT) {
				area[x][y] = FREE;
			} else {
				Log.d("Area already free", "Map.setFree(), area with x=" + x + " y=" + y + " is free");
			}
		} else {
			Log.d("Out of bounds" , "Map.setFree() out of bounds with x=" + x + " y=" + y);
		}
	}
	
	public boolean isTaken(int x, int y) {
		if (x >= 0 && x < X_LENGTH && y >= 0 && y < Y_LENGTH) {
			if (area[x][y] != TAKEN)
				return false;
		}
		return true;
	}
	public Path findPath(int x, int y, int tx, int ty) {
		return pathfinder.findPath(new Creep(), x, y, tx, ty);
	}
	public Path fromEnterToExit() {
		return pathfinder.findPath(new Creep(), enterNode.x, enterNode.y, exitNode.x, exitNode.y);
	}
	public boolean pathNotValid(int x, int y) {
		if (pathfinder.findPath(new Creep(), x, y, exitNode.x, exitNode.y) == null)
			validPathExists = false;
		else
			validPathExists = true;
		return validPathExists;
	}
	@Override
	public int getWidthInTiles() {
		// TODO Auto-generated method stub
		return X_LENGTH;
	}

	@Override
	public int getHeightInTiles() {
		// TODO Auto-generated method stub
		return Y_LENGTH;
	}

	@Override
	public void pathFinderVisited(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean blocked(Mover mover, int x, int y) {
		// TODO Auto-generated method stub
		return isTaken(x, y);
	}

	@Override
	public float getCost(Mover mover, int sx, int sy, int tx, int ty) {
		// TODO Auto-generated method stub
		return 1;
	}
	public void clearVisited() {
		for (int i=0; i<X_LENGTH; i++)
			for (int j=0; j<Y_LENGTH; j++)
				visited[i][j] = false;
	}
}