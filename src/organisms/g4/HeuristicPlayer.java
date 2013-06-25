package organisms.g4;

import organisms.Move;

@SuppressWarnings("serial")
public class HeuristicPlayer extends TrackingPlayer {
	int curDir = 1;
	int ctr = 0;
	boolean first = true;

	@Override
	protected void register(int key) {
		if(key!=-1){
			ctr = getState();
		}else{
			ctr = 0;
		}
	}

	@Override
	protected Move reproduce(boolean[] foodpresent, int[] neighbors,
			int foodleft, int energyleft) {
		
		ctr++;

		double foodDist = foodTracker.lastXMovesPercentage(100);
		double otherOrgDist = organismTracker.lastXMovesPercentage(100);
		
		if (energyleft > MAX_ENERGY/2 -   (MAX_ENERGY/2)*foodDist ){
			int direction = -1;
			for (int i =1; i < 5; i++) {
				if (foodpresent[i] && neighbors[i] == -1) {
					direction = i;
				}
			}
			if (direction == -1) {
				for (int i =1; i < 5; i++) {
					if ( neighbors[i] == -1) {
						direction = i;
					}
				}
			}
			return new Move(REPRODUCE, direction, ctr);
		}

		return null;
	}
	protected boolean foodNextTo(int[] nbors, boolean[] foodHere){
		boolean foodAdjacent = false;
		for (int move : getValidMoves(nbors)) {
			if (foodHere[move] && nbors[move]==-1) {
				foodAdjacent = true;
			}
		}
		return foodAdjacent;
	}
	@Override
	protected Move makeMove(boolean[] foodpresent, int[] neighbors,
			int foodleft, int energyleft) {
		Move m = null;


		double foodDist = foodTracker.lastXMovesPercentage(100);
		double otherOrgDist = organismTracker.lastXMovesPercentage(100);
		
		if (foodleft==0 && ctr>5 && (energyleft <= (MAX_ENERGY/2) + (MAX_ENERGY/2)*(otherOrgDist) + (MAX_ENERGY/2)*(foodDist) )  && !foodNextTo(neighbors, foodpresent)) {
			return null;
		}
		
		int direction = curDir;
		if((neighbors[(curDir) ]==-1)){}
		else{
			for (int i = 1 ; i < 5 ; i++) {
				boolean br = false;
				if(neighbors[i ]==-1){
					direction =i;
					curDir=i;
					br=true;
				}
				if (br){
					break;
				}
			}
		}
		for (int move : getValidMoves(neighbors)) {
			if (foodpresent[move] && neighbors[move]==-1) {
				direction = move;
			}
		}
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

	@Override
	public String name() {
		return "Heuristic Player";
	}

}
