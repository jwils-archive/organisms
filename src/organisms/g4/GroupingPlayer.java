package organisms.g4;

import organisms.Move;
import organisms.g4.strat.*;

@SuppressWarnings("serial")
public class GroupingPlayer extends KnowledgePlayer {
	boolean alone = false;
	
	public static int NORMAL_STATE = 88;
	public static int MOVE_STATE = 89;
	
	public boolean isAgressive = false;
	
	private Strategy strat;
	
	private boolean[] friendlySquares = new boolean[5];
	
	double reproductionThreshold;
	
	boolean westWas89LastTurn = false;
	
	
	int direction;
	
	@Override
	protected void init() {
		super.init();
		reproductionThreshold = (double)(nextRandomInt(4) + 5)/10;
	}
	
	@Override
	protected void preMoveTrack(MoveInfo moveInfo) {
		super.preMoveTrack(moveInfo);
		for (int n = 1; n < 5; n++) {
			if (moveInfo.getNeighbors()[n] == MOVE_STATE) {
				friendlySquares[n] = true;
			}
			if ((friendlySquares[n] == true) &&
					(moveInfo.getNeighbors()[n] != NORMAL_STATE) &&
					(moveInfo.getNeighbors()[n] != MOVE_STATE) &&
					(moveInfo.getNeighbors()[n] != -1)) {
				friendlySquares[n] = false;
			}
		}
	}
	

	private void setStrategy(MoveInfo moveInfo) {
		if (getTurnNumber() < 8) {
			reproductionThreshold = 0.5;
		}
		int moveNumber = 150 - turnsSinceLastMove;
		if(moveNumber <= 20) {
			moveNumber = 20;
		}
		
		if (nextRandomInt(100) < 20) {
			isAgressive = false;
		}

		
		if (nextRandomInt(moveNumber) < 9 && moveInfo.getValidMoves().length == 4) {
			isAgressive = true;
		}
		
		
		
		if (getTurnNumber() < 50 || isAgressive) {
			strat = new AgressiveStrat(this, foodTracker.getX(), foodTracker.getY(), reproductionThreshold);
		} else {
			strat = new GridStrat(this, foodTracker.getX(), foodTracker.getY());
		}
	}
	
	@Override
	public Move reproduce(MoveInfo moveInfo) {
		setStrategy(moveInfo);
		setState(NORMAL_STATE); //nextRandomInt(255);

		if (moveInfo.numberOfFriendlyNeighbors() == 4) {
			setState(MOVE_STATE);
		}
		
		
		return strat.reproduce(moveInfo);
	}

	@Override
	public Move makeMove(MoveInfo moveInfo) {
		if (westWas89LastTurn && moveInfo.getNeighbor(EAST) == -1) {
			westWas89LastTurn = false;
			setState(GroupingPlayer.MOVE_STATE);
			return new Move(EAST);
		}
		
		if (moveInfo.getFoodleft() > 0 && moveInfo.numberOfFriendlyNeighbors() < 3) {
			return null;
		}
		
		if (moveInfo.getNeighbor(WEST) == 89) {
			westWas89LastTurn = true;
		} else {
			westWas89LastTurn = false;
		}
		
		if (isAgressive && moveInfo.numberOfFriendlyNeighbors() > 2) {
			isAgressive = false;
			if( moveInfo.canMove()) {
				return new Move(moveInfo.getValidMoves()[0]);
			}
		}
		if(reproductionThreshold > .69 
				&& turnsSinceLastMove < 10 
				&& isAgressive && !moveInfo.canMoveToFood()
				&& moveInfo.numberOfFriendlyNeighbors() < 2) {
			return new Move(STAYPUT);
		}

		return strat.makeMove(moveInfo);
	}

	@Override
	public String name() {
		return "Grouping Player";
	}
}
