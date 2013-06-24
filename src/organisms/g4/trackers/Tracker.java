package organisms.g4.trackers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import organisms.Move;

public abstract class Tracker {

	protected HashMap<Point,Integer> map = new HashMap<Point,Integer>();
	protected TreeMap<Integer,Point> whenThere = new TreeMap<Integer,Point>();

	protected int x;
	protected int y;
	protected Integer turn = 0;
	// I don't add the origin to the map until we move away from it.
	// This is because we will get a more updated version of the
	// boolean to which it's mapped after it moves
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	public int getTurn(){
		return turn;
	}
	public void incrementTurn(){
		turn++;
	}

	public Tracker(){
		x = 0;
		y = 0;
	}

	public Tracker(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// Return current location in map
	
	public Point currentLocation() {
		return new Point(x,y);
	}
	// Returns percentage of spaces seen that has what we are tracking over the last X moves

	public double lastXMovesPercentage(int x){
		ArrayList<Point> turnsWanted = new ArrayList<Point>();
		turnsWanted.addAll(whenThere.headMap(x).values());
		double end = 0.0;
		int count = 0;
		for(Point thisPoint : turnsWanted){
			if(map.get(thisPoint).equals(0)){}
			else {
				count++;
			}
		}
		if(turnsWanted.size() != 0){
			end = count / turnsWanted.size();
		}
		
		return end;	}
	// Returns percentage of spaces seen that has what we are tracking
	
	public double percentage() {
		Object[] array = map.values().toArray();
		int count = 0;
		double end;
		
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(0)) {}
			else {
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
	
	public void add(Move m, int[] hasBoolean) {

		map.put(new Point(x-1,y), hasBoolean[1]);
		whenThere.put(turn, new Point(x-1,y));
		map.put(new Point(x+1,y), hasBoolean[2]);
		whenThere.put(turn, new Point(x+1,y));

		map.put(new Point(x,y-1), hasBoolean[3]);
		whenThere.put(turn, new Point(x,y-1));

		map.put(new Point(x,y+1), hasBoolean[4]);
		whenThere.put(turn, new Point(x,y+1));


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
