package com.example.towerdefence;

import java.util.ArrayList;

import com.example.towerdefence.Map.Node;

public class testmap {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map m = new Map(0,0, 9, 13);
		m.setTaken(1,1);
		m.setTaken(0,1);
		m.setTaken(1,2);
		m.setTaken(4,3);
		m.setTaken(5,5);
		m.setTaken(1,8);
		m.setTaken(1,9);
		m.setTaken(9,4);
		
		int[][] a = m.area;
		ArrayList<Node> p = m.makePath(0,0);
		for (int i=0; i<p.size(); i++)
			a[p.get(i).x][p.get(i).y] = 8;
		
		for (int i=0; i<10; i++) {
			for (int j=0; j<14; j++) {
				System.out.print(a[i][j]);
			}
			System.out.println();
		}
	}

}
