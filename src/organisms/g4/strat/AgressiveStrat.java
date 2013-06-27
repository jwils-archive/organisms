package organisms.g4.strat;

import organisms.Move;
import organisms.g4.KnowledgePlayer;
import organisms.g4.MoveInfo;

public class AgressiveStrat implements Strategy, organisms.Constants {
	private KnowledgePlayer player;
	private int locationX;
	private int locationY;
	private double reproductionThreshold;

	public AgressiveStrat(KnowledgePlayer p, int x, int y, double rt) {
		player = p;
		locationX = x;
		locationY = y;
		reproductionThreshold = rt;
	}
	
	
	@Override
	public Move reproduce(MoveInfo moveInfo) {
		
		if (moveInfo.canMoveToFood() && moveInfo.getFoodleft() > 1) {
			return player.reproduce(moveInfo.directionOfFood());
		}
		
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
		int direction = 0;
		
		if (moveInfo.canMoveToFood()) {
			return moveInfo.moveToFood();
		}
		
		if (player.nextRandomInt(player.getTurnNumber() % 20 + 1) > 15) {
			return new Move(STAYPUT);
		}
		
		if (locationY % 2 != 0 && locationY < 50) {
			if (moveInfo.isValidMove(EAST) && moveInfo.isValidMove(WEST)) {
				return new Move(EAST);
			}
		}

		if (moveInfo.isValidMove(SOUTH)) {
			return new Move(SOUTH);
		}
		
		return null;
	}
	
	public boolean shouldReproduce(MoveInfo moveInfo) {
		if (moveInfo.getEnergyleft() > player.MAX_ENERGY * reproductionThreshold 
				&& moveInfo.numberOfFriendlyNeighbors() != 3) {
			return true;
		}
		return false;
	}

}
