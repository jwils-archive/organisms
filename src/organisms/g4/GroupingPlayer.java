package organisms.g4;

import java.util.ArrayList;

import organisms.Move;

public class GroupingPlayer extends KnowledgePlayer {

	@Override
	protected Move reproduce(boolean[] foodpresent, int[] neighbors,
			int foodleft, int energyleft) {
		double foodDist = foodTracker.lastXMovesPercentage(100);
		double otherOrgDist = organismTracker.lastXMovesPercentage(100);

		if (energyleft > MAX_ENERGY/2 +  (MAX_ENERGY/4)*otherOrgDist  + (MAX_ENERGY/4)*foodDist ) {
			int direction = -1;
			for (int i = 1; i < 5; i++) {
				if (foodpresent[i] && neighbors[i] == -1) {
					direction = i;
				}
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

		
		System.out.println("State : (" + foodTracker.getX() + ", " + foodTracker.getY() + ")");
		
		if (turnNumber > 50) {
			for (int move : getValidMoves(neighbors)) {
				if ((foodTracker.getX() + foodTracker.getY()) % 2 != 0) {
					return new Move(move);
				}
			}

			for (int move : getValidMoves(neighbors)) {
				if (foodpresent[move] && neighbors[move]==-1) {
					return new Move(move);
				}
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
				if (moves.contains(EAST)) {
					direction = EAST;
				} else if(moves.contains(WEST)) {
					direction = WEST;
				}
			}
			
			
			for (int move : getValidMoves(neighbors)) {
				if (foodpresent[move] && neighbors[move]==-1) {
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
		// TODO Auto-generated method stub
		return "Grouping Player";
	}
}
