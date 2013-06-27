package organisms.g4.strat;

import organisms.Move;
import organisms.g4.GroupingPlayer;
import organisms.g4.MoveInfo;

public class GridStrat implements Strategy, organisms.Constants {
	private int locationX;
	private int locationY;
	
	private GroupingPlayer player;
	
	public GridStrat(GroupingPlayer p, int lx, int ly) {
		locationX = lx;
		locationY = ly;
		player = p;
	}
	
	@Override
	public Move reproduce(MoveInfo moveInfo) {
		if (shouldReproduce(moveInfo)) {
			int direction = -1;
			if (moveInfo.canMoveToFood()) {
				direction = moveInfo.directionOfFood();
			}
			
			if (moveInfo.canMoveBack()) {
				direction = moveInfo.directionOfMoveBack();
			}
			
			if (direction == -1) {
				for(int move : moveInfo.getValidMoves()) {
					direction = move;
				}
				if (direction == -1) {
					return null;
				}
			}
			return player.reproduce(direction);
		}
		return null;
	}

	@Override
	public Move makeMove(MoveInfo moveInfo) {
		Move m =  moveInfo.moveToFood();
		if (m != null) {
			return m;
		}
		
		for (int move : moveInfo.getValidMoves()) {
			if (!(moveInfo.getFoodleft() > 0 && moveInfo.getEnergyleft() < (18/20*player.MAX_ENERGY)) && 
					(locationX + locationY) % 2 != 0) {
				return new Move(move);
			}
		}
		
		
		
		if (moveInfo.getNeighbor(NORTH) == 89 && moveInfo.getNeighbor(SOUTH) == -1) {
			player.setState(GroupingPlayer.MOVE_STATE);
			return new Move(SOUTH);
		}
		return null;
	}
	
	public boolean shouldReproduce(MoveInfo moveInfo) {
		if (moveInfo.getEnergyleft() > player.MAX_ENERGY / 2 && moveInfo.numberOfFriendlyNeighbors() != 3) {
			return true;
		}
		return false;
	}

}
