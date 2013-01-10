package com.example.towerdefence;

import java.util.ArrayList;

public abstract class Pool<T> {
	private ArrayList<T> items = new ArrayList<T>();
	
	protected abstract T newItem();
	
	public T obtain() {
		if (items.size() == 0) 
			return newItem();
		return items.remove(0);
	}
	
	public void free(T item) {
		items.add(item);
	}
}
