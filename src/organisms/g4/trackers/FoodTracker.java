package organisms.g4.trackers;

import java.awt.Point;
import organisms.Move;

public class FoodTracker extends Tracker {
	
	public FoodTracker(int x, int y) {
		super(x,y);
	}
	
	public FoodTracker() {
		super();
	}
	
	
	
	public int[] toIntegerArray(boolean[] foodPresent) {
		int[] array = new int[foodPresent.length];
		for (int i = 0; i < foodPresent.length; i++) {
			if (foodPresent[i] == false) {
				array[i] = 0;
			}
			else {
				array[i] = -1;
			}
		}
		return array;
	}
	
	public void add(Move m, boolean[] food) {
		add(m, toIntegerArray(food));
	}
	
	public void add(int foodValue) {
		map.put(new Point(x,y), foodValue);
		whenThere.put(turn, new Point(x,y));
	}

}
