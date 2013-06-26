package organisms.g4;

import java.util.ArrayList;

import organisms.Move;

@SuppressWarnings("serial")
public class GroupingPlayer extends KnowledgePlayer {
	boolean alone = false;
	
	static int NORMAL_STATE = 88;
	static int MOVE_STATE = 89;
	
	private boolean[] friendlySquares = new boolean[5];
	
	@Override
	protected void preMoveTrack(boolean[] foodpresent, int[] neighbors,
			int foodleft, int energyleft) {
		super.preMoveTrack(foodpresent, neighbors, foodleft, energyleft);
		for (int n = 1; n < 5; n++) {
			if (neighbors[n] == MOVE_STATE) {
				friendlySquares[n] = true;
			}
			if ((friendlySquares[n] == true) &&
					(neighbors[n] != NORMAL_STATE) &&
					(neighbors[n] != MOVE_STATE) &&
					(neighbors[n] != -1)) {
				friendlySquares[n] = false;
			}
		}
	}
	
	@Override
	protected Move reproduce(boolean[] foodpresent, int[] neighbors,
			int foodleft, int energyleft) {
		setState(NORMAL_STATE); //nextRandomInt(255);

		if (numberOfFriendlyNeighbors(neighbors) == 4) {
			setState(MOVE_STATE);
		}
		
		if (energyleft > MAX_ENERGY / 2 && numberOfFriendlyNeighbors(neighbors) != 3) {
			int direction = -1;
			for (int i = 1; i < 5; i++) {
				if (foodpresent[i] && neighbors[i] == -1) {
					direction = i;
				}
			}
			
			if (lastMove > 0 && lastMove < 5 && neighbors[reverse(lastMove)] == -1) {
				direction = reverse(lastMove);
			}
			
			if (direction == -1) {
				for (int i = 1; i < 5; i++) {
					if (neighbors[i] == -1) {
						direction = i;
					}
				}
			}
			return reproduce(direction);
		}

		return null;
	}

	
	protected int numberOfFriendlyNeighbors(int[] neighbors) {
		int sum = 0;
		for(int i = 1; i < 5; i++) {
			if (neighbors[i] == 88) {
				sum++;
			}
		}
		return sum;
	}
	
	protected boolean foodNextTo(int[] nbors, boolean[] foodHere) {
		boolean foodAdjacent = false;
		for (int move : getValidMoves(nbors)) {
			if (foodHere[move] && nbors[move] == -1) {
				foodAdjacent = true;
			}
		}
		return foodAdjacent;
	}

	@Override
	protected Move makeMove(boolean[] foodpresent, int[] neighbors,
			int foodleft, int energyleft) {

//		if (lastMove > 0 && lastMove < 5 && friendlySquares[lastMove]) {
//			return new Move(reverse(lastMove));
//		}
//		
//		if (numberOfFriendlyNeighbors(neighbors) == 3 && neighbors[reverse(lastMove)] == -1) {
//			friendlySquares[lastMove] = true;
//			return new Move(reverse(lastMove));
//		}
		
		if (turnNumber > 50) {
			
//			boolean hasFriendly = false;
//			for (boolean friendly : friendlySquares) {
//				if (friendly) {
//					hasFriendly = true;
//				}
//			}
//			
//			if (hasFriendly) {
//				if (energyleft < ENERGY_TO_MOVE*1.5) {
//					for (int i = 1; i < 5; i++) {
//						if (friendlySquares[i] && foodpresent[i]) {
//							return new Move(i);
//						}
//					}
//				} 
//			}
			
			
			for (int move : getValidMoves(neighbors)) {
				
				if (!(foodleft > 0 && energyleft < (2/3*MAX_ENERGY)) && 
						(foodTracker.getX() + foodTracker.getY()) % 2 != 0) {
					return new Move(move);
				}
			}
			if (!alone || energyleft < ENERGY_TO_MOVE * 2) {
				for (int move : getValidMoves(neighbors)) {
					if (foodpresent[move] && neighbors[move] == -1) {
						return new Move(move);
					}
				}
			}
			
			if (neighbors[NORTH] == 89 && neighbors[SOUTH] == -1) {
				setState(MOVE_STATE);
				return new Move(SOUTH);
			}

		} else {
			ArrayList<Integer> moves = new ArrayList<Integer>();
			for (int move : getValidMoves(neighbors)) {
				moves.add(move);
			}

			int direction = 0;

			if (moves.contains(SOUTH)) {
				direction = SOUTH;
			}
			if (foodTracker.getY() % 2 != 0) {
				if (moves.contains(EAST) && moves.contains(WEST)) {
					direction = EAST;
				}
			}

			for (int move : getValidMoves(neighbors)) {
				if (foodpresent[move] && neighbors[move] == -1) {
					direction = move;
				}
			}
			Move m = null;
			switch (direction) {
			case 0:
				m = new Move(STAYPUT);
				break;
			case 1:
				m = new Move(WEST);
				break;
			case 2:
				m = new Move(EAST);
				break;
			case 3:
				m = new Move(NORTH);
				break;
			case 4:
				m = new Move(SOUTH);
				break;
			}
			return m;
		}
		return null;

	}

	@Override
	public String name() {
		return "Grouping Player";
	}
}
