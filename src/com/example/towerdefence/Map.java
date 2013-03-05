package com.example.towerdefence;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class Map {
	public final int FREE, TAKEN, ENTER, EXIT, X_LENGTH, Y_LENGTH;
	 int[][] area;
	private Node enter, exit;
	private ArrayList<Node> pathNodes;
	private boolean pathFound;

	public Map(int enterX, int enterY, int exitX, int exitY) {
		FREE = 0;
		TAKEN = 1;
		ENTER = 2;
		EXIT = 3;
		X_LENGTH = 10;
		Y_LENGTH = 14;

		area = new int[X_LENGTH][Y_LENGTH];
		for (int i=0; i < X_LENGTH; i++) {
			for (int j=0; j < Y_LENGTH; j++) {
				area[X_LENGTH][Y_LENGTH] = FREE;
			}
		}
		area[enterX][enterY] = ENTER;
		enter = new Node(null , enterX, enterY);

		area[exitX][exitY] = EXIT;
		exit = new Node(null, enterX, enterY);
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
	public Node[] findPath(int fromX, int fromY) {
		Node[] path = null;

		return path;
	}
	//	public void makePath() {
	//		int[][] area = this.area.clone();
	//		Node enter = new Node(0,0,0);
	//		Node exit = new Node(0,0,0);
	//		int value = 1;
	//		for (int i=0; i<X_LENGTH; i++) {
	//			for (int j=0; j<Y_LENGTH; j++) {
	//				if (area[i][j] == ENTER)
	//					enter = new Node(i, j, value);
	//				if (area[i][j] == EXIT)
	//					exit = new Node(i, j, 0);
	//				if (area[i][j] != 0)
	//					area[i][j] = -1;
	//			}
	//		}
	//		boolean shortestFound = false;
	//		while(!shortestFound) {
	//			Node[] nodes = markNeighbours(enter.x, enter.y, value, area);
	//			for (int i=0; i<nodes.length; i++) {
	//
	//			}
	//		}
	//	}
	//	private Node[] markNeighbours(int x, int y, int value, int[][] area) {
	//		Node[] neighbours = new Node[4];
	//		// LEFT
	//		if (x > 0) {
	//			if (area[x-1][y] == 0)
	//				neighbours[0] = new Node(x-1, y, value + 1);
	//			else
	//				neighbours[0] = null;
	//		}	else 
	//			neighbours[0] = null;
	//		// UP
	//		if (y > 0) {
	//			if (area[x][y-1] == 0)
	//				neighbours[1] = new Node(x, y-1, value + 1);
	//			else 
	//				neighbours[1] = null;
	//		} else
	//			neighbours[1] = null;
	//		// RIGHT
	//		if (x < X_LENGTH - 1) {
	//			if (area[x+1][y] == 0)
	//				neighbours[2] = new Node(x+1, y, value + 1);
	//			else 
	//				neighbours[2] = null;
	//		} else 
	//			neighbours[2] = null;
	//		// DOWN
	//		if (y < Y_LENGTH - 1) {
	//			if (area[x][y+1] == 0)
	//				neighbours[3] = new Node(x, y+1, value + 1);
	//			else 
	//				neighbours[3] = null;
	//		} else
	//			neighbours[3] = null;
	//
	//
	//		return neighbours;
	//	}
	//
	//	class Node2 {
	//		Node parent;
	//		int x, y, value;
	//		public Node2(Node parent, int x, int y, int value) {
	//			this.parent = parent;
	//			this.x = x;
	//			this.y = y;
	//			this.value = value;
	//		}
	//	}





	//	private class Tagged {
	//		private List<Node> t;
	//		public Tagged() {
	//			t = new ArrayList<Node>();
	//		}
	//		public void add(Node n) {
	//			t.add(n);
	//		}
	//		public boolean isTagged(Node n) {
	//			return t.contains(n);
	//		}
	//	}




	class Node {
		Node parent;
		int x, y;
		boolean goal, blocked;

		public Node(Node parent, int x, int y) {
			this.parent = parent;
			this.x = x;
			this.y = y;
		}
	}



	//	private void findNeighbour(Node p) {
	//		Node n, e, s, w = null;
	//		// Check north
	//		if (p.y - 1 >= 0) 
	//			if (area[p.x][p.y - 1] == FREE) 
	//				n = new Node(p, p.x, p.y - 1, p.value + 1);
	//
	//		// Check east
	//		if (p.x + 1 < X_LENGTH)
	//			if (area[p.x + 1][p.y] == FREE)
	//				e = new Node(p, p.x + 1, p.y, p.value + 1);
	//
	//		// Check south
	//		if (p.y + 1 < Y_LENGTH)
	//			if (area[p.x][p.y + 1] == FREE)
	//				s = new Node(p, p.x, p.y + 1, p.value + 1);
	//
	//		// Check west
	//		if (p.x - 1 >= 0)
	//			if (area[p.x - 1][p.y] == FREE) {
	//				w = new Node(p, p.x - 1, p.y, p.value + 1);
	//
	//			}
	//
	//	}
	public ArrayList<Node> makePath(int startX, int startY) {
		ArrayList<Node> path = new ArrayList<Node>();
		Node start = new Node(null, startX, startY);
		check(start);
		if (pathFound) {
			for (int i=0; i<pathNodes.size(); i++) {
				if (pathNodes.get(i).x == exit.x && pathNodes.get(i).y == exit.y){
					boolean pathComplete = false;
					Node n = pathNodes.get(i);
					while(!pathComplete) {
						if (n.parent == null)
							pathComplete = true;
						else {
							path.add(n);
							n = n.parent;
						}
					}
				}
			}
			return path;
		} else 
			return null;
	}
	
	private int check(Node n)  {
		if (n.x < 0 || n.x >= X_LENGTH || n.y < 0 || n.y >= Y_LENGTH)
			return -1;
		if ( area[n.x][n.y] == TAKEN )
			return -1;
		if ( area[n.x][n.y] == EXIT ) {
			pathNodes.add(n);
			return 0;
		}
		for (int i=0; i<pathNodes.size(); i++) {
			if( pathNodes.get(i).x == n.x && pathNodes.get(i).y == n.y )
				return -1;
		}
		pathNodes.add(n);
		findNeighbours(n);
		return -1;	
	}

	private void findNeighbours(Node p) {
		Node n, w, e, s;
		n = new Node(p, p.x, p.y);
		if (check(n) == 0)
			pathFound = true;
		w = new Node (p, p.x - 1, p.y);
		if (check(w) == 0)
			pathFound = true;
		e = new Node(p, p.x + 1, p.y);
		if (check(e) == 0)
			pathFound = true;
		s = new Node(p, p.x, p.y + 1);
		if (check(s) == 0)
			pathFound = true;
	}

















}
