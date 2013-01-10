package com.example.towerdefence;

public class TouchEventPool extends Pool<TouchEvent> {

	@Override
	protected TouchEvent newItem() {
		// TODO Auto-generated method stub
		return new TouchEvent();
	}

}
