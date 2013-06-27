package organisms.g4;

import java.util.Arrays;

import organisms.Move;

public class MoveInfo implements organisms.Constants {
	private boolean[] foodpresent; 
	private int[] neighbors; 
	private int foodleft; 
	private int energyleft;
	private int lastmove;
	
	MoveInfo(boolean[] fp, int[] n, int fl, int el, int lm) {
		foodpresent = fp;
		neighbors = n;
		foodleft = fl;
		energyleft = el;
		lastmove = lm;
	}
	
	public boolean[] getFoodpresent() {
		return foodpresent;
	}

	public int[] getNeighbors() {
		return neighbors;
	}
	
	public int getNeighbor(int direction) {
		return neighbors[direction];
	}

	public int getFoodleft() {
		return foodleft;
	}

	public int getEnergyleft() {
		return energyleft;
	}
	
	public int getLastmove() {
		return lastmove;
	}
	
	public boolean canMove() {
		return getValidMoves().length > 0;
	}
	
	public int[] getValidMoves() {
		int[] validMoves = new int[4];
		int validMoveCount = 0;
		for (int move = 1; move < 5; move++) {
			if (isValidMove(move)) {
				validMoves[validMoveCount++] = move;
			}
		}
		return Arrays.copyOf(validMoves, validMoveCount);
	}
	
	public boolean isValidMove(int move) {
		if (neighbors[move] != -1) {
			return false;
		}
		return true;
	}
	
	public Move moveToFood() {
		for (int move : getValidMoves()) {
			if (foodpresent[move] && neighbors[move] == -1) {
				return new Move(move);
			}
		}
		return null;
	}
	
	public int numberOfFriendlyNeighbors() {
		int sum = 0;
		for(int i = 1; i < 5; i++) {
			if (neighbors[i] == 88) {
				sum++;
			}
		}
		return sum;
	}
	
	public int directionOfFood() {
		for (int i = 1; i < 5; i++) {
			if (foodpresent[i] && neighbors[i] == -1) {
				return i;
			}
		}
		return -1;
	}
	
	
	public boolean canMoveToFood() {
		for (int i = 1; i < 5; i++) {
			if (foodpresent[i] && neighbors[i] == -1) {
				return true;
			}
		}
		return false;
	}
	
	
	public boolean canMoveBack() {
		if (lastmove > 0 && lastmove < 5 && neighbors[reverse(lastmove)] == -1) {
			return true;
		}
		return false;
	}
	
	public int directionOfMoveBack() {
		if (lastmove > 0 && lastmove < 5 && neighbors[reverse(lastmove)] == -1) {
			return reverse(lastmove);
		}
		return -1;
	}
	
	protected int reverse(int direction) {
		switch(direction) {
		case NORTH:
			return SOUTH;
		case SOUTH:
			return NORTH;
		case EAST:
			return WEST;
		case WEST:
			return EAST;
		}
		return -1;
	}
}