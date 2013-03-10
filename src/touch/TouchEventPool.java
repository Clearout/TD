package touch;

import com.example.towerdefence.Pool;

public class TouchEventPool extends Pool<TouchEvent> {

	protected TouchEvent newItem() {
		return new TouchEvent();
	}

}
