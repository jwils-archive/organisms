package organisms.g4.trackers;

import java.awt.Point;
import java.util.HashMap;

import organisms.Move;

public class Tracker {

	private HashMap<Point,Boolean> map = new HashMap<Point,Boolean>();
	private int x;
	private int y;

	// I don't add the origin to the map until we move away from it.
	// This is because we will get a more updated version of the
	// boolean to which it's mapped after it moves
	
	public Tracker(){
		x = 0;
		y = 0;
	}

	// Return current location in map
	
	public Point currentLocation() {
		return new Point(x,y);
	}
	
	// Returns percentage of spaces seen that has what we are tracking
	
	public double percentage() {
		Object[] array = map.values().toArray();
		int count = 0;
		double end;
		
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(true)) {
				count++;
			}
		}
		
		end = count / array.length;
		
		return end;
		
	}

	// This adds the adjacent orthogonal spaces to the map.
	// The hasBoolean int[] will be the the foodpresent int[]
	// for the FoodTacker version and the neighbors int[] for
	// the OrganismTracker version.
	// At the end of the method it updates the x and y coordinates
	
	public void add(Move m, int[] hasBoolean) throws Exception {

		boolean[] array = new boolean[hasBoolean.length];
		for (int i = 0; i < hasBoolean.length; i++) {
			if (hasBoolean[i] != 0) {
				array[i] = true;
			}
			else {
				array[i] = false;
			}
		}

		map.put(new Point(x,y-1), array[0]);
		map.put(new Point(x+1,y), array[1]);
		map.put(new Point(x,y+1), array[2]);
		map.put(new Point(x-1,y), array[3]);

		if (m.type() == 1) {
			x--;
		}
		else if (m.type() == 2) {
			x++;
		}
		else if (m.type() == 3) {
			y--;
		}
		else if (m.type() == 4) {
			y++;
		}
	}

}
