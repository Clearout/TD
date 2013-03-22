package touch;


public class TouchEventPool extends Pool<TouchEvent> {

	protected TouchEvent newItem() {
		return new TouchEvent();
	}

}
