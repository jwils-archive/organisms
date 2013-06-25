package organisms.g4;

import organisms.Move;

@SuppressWarnings("serial")
public class SoloPlayer extends TrackingPlayer {
	int curDir = 1;

	@Override
	protected void register(int key) {

	}

	@Override
	protected Move reproduce(boolean[] foodpresent, int[] neighbors,
			int foodleft, int energyleft) {
		double foodDist = foodTracker.lastXMovesPercentage(1000);
		double otherOrgDist = organismTracker.lastXMovesPercentage(10-0);
		
		if (energyleft > MAX_ENERGY*.97 && otherOrgDist >0.995){//MAX_ENERGY/2 +  (MAX_ENERGY/2)*otherOrgDist -(MAX_ENERGY/2)*foodDist){//+  (MAX_ENERGY/2)*otherOrgDist  - (MAX_ENERGY/2)*foodDist ){
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
			return new Move(REPRODUCE, direction, getState());
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
		
		if (foodleft==0 && (energyleft > 200 || !foodNextTo(neighbors, foodpresent))) {
			return null;
		}
		
		int direction = curDir;
		if((neighbors[(curDir) ]==-1)){}
		else{
			for (int i = 1 ; i < 5 ; i++) {
				if(neighbors[i ]==-1){
					direction =i;
					curDir=i;
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
		return "Solo Player";
	}

}
