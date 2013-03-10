//package com.example.towerdefence;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.PriorityQueue;
//
//public class Pathfinder {
//	private PriorityQueue<Node> open = new PriorityQueue<Node>();
//	private ArrayList<Node> closed;
//	private Map map;
//
//	public Pathfinder(Map map) {
//		this.map = map;
//	}
//
//	private int cost(Node n) {
//		return Math.abs(n.x - map.exit.x) + Math.abs(n.y - map.exit.y);
//	}
//
//	private Node[] findNeighbours(Node n) {
//		Node[] nodes = new Node[4];
//		nodes[0] = new Node(n.x, n.y - 1, null);
//		nodes[1] = new Node(n.x, n.y + 1, null);
//		nodes[2] = new Node(n.x - 1, n.y, null);
//		nodes[3] = new Node(n.x + 1, n.y, null);
//		return nodes;
//	}
//
//	public ArrayList<Node> findPath(Node start) {
//		ArrayList<Node> path = new ArrayList<Node>();
//
//		open.add(start);
//		closed = new ArrayList<Node>();
//
//		while(open.peek().x != map.exit.x && open.peek().y != map.exit.y) {
//
//			Node current = open.poll();
//			closed.add(current);
//			Node[] neighbour = findNeighbours(current);
//			int cost = cost(current) + 1;
//
//			for (int i=0; i<neighbour.length; i++) {
//				
//				if (!map.isTaken(neighbour[i].x, neighbour[i].y) ) {
//
//					boolean inOpen = false;
//					boolean inClosed = false;
//					Iterator<Node> it = open.iterator();
//
//					while (it.hasNext()) {
//						Node n = (Node)it.next();
//						if (n.x == neighbour[i].x && n.y == neighbour[i].y) {
//							if (cost < n.cost)
//								open.remove(n);
//							inOpen = true;
//							break;
//						}
//					}
//
//					it = closed.iterator();
//					while (it.hasNext()) {
//						Node n = (Node)it.next();
//						if (n.x == neighbour[i].x && n.y == neighbour[i].y) {
//							if (cost < n.cost)
//								closed.remove(n);
//							inClosed = true;
//							break;
//						}
//					}
//
//					if (inClosed == false && inOpen == false) {
//						neighbour[i].cost = cost;
//						open.add(neighbour[i]);
//						neighbour[i].parent = current;
//					}
//				}
//			}
//		}
//
//		Node n = open.peek();
//		while(n.parent != null) {
//			path.add(n);
//			n = n.parent;
//		}
//
//		return path;
//	}
//	public Node makeNode(int x, int y) {
//		return new Node(x, y, null);
//	}
//	class Node implements Comparable<Node> {
//		int x, y, cost;
//		Node parent;
//
//		public Node(int x, int y, Node parent) {
//			this.x = x;
//			this.y = y;
//			this.parent = parent;
//		}
//
//		@Override
//		public int compareTo(Node n) {
//			if(cost < n.cost)
//				return -1;
//			if (cost > n.cost)
//				return 1;
//			return 0;
//		}
//	}
//}
