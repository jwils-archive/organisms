package organisms.g4;

import java.nio.ByteBuffer;

import organisms.Move;
import organisms.g4.codec.Decoder;
import organisms.g4.comm.DataForChild;
import organisms.g4.trackers.FoodTracker;
import organisms.g4.trackers.OrganismTracker;
import organisms.g4.trackers.PersonalSettingsTracker;

@SuppressWarnings("serial")
public class KnowledgePlayer extends Group4BasePlayer {
	protected int turnNumber = 0;
	protected FoodTracker foodTracker;
	protected OrganismTracker organismTracker;
	protected PersonalSettingsTracker settingsTracker;
	
	private Decoder decoder = null;
	private int directionOfCommunication;
	
	@Override
	protected void register(int key) {
		setColor(1.0f,0.0f,1.0f);
		if (key == -1) {
			turnNumber = 1;
			directionOfCommunication = -1;
			
			foodTracker = new FoodTracker();
			organismTracker = new OrganismTracker();
			settingsTracker = new PersonalSettingsTracker();
		} else {
			DataForChild data = DataForChild.decode(key);
			directionOfCommunication = data.getParentLocation();

			foodTracker = new FoodTracker(-data.getOriginX(), -data.getOriginY());
			organismTracker = new OrganismTracker(-data.getOriginX(), -data.getOriginY());
			settingsTracker = new PersonalSettingsTracker();
			turnNumber = data.getTurnNumber() + 1;
		}
		
	}
	
	protected Move communicate(int[] neighbors) {
		if (directionOfCommunication != -1) {
		
		}
		return null;
	}
	
	
	@Override
	protected Move reproduce(boolean[] foodpresent, int[] neighbors,
			int foodleft, int energyleft) {
		Move m = communicate(neighbors);
		if (m != null) {
			return m;
		}
		
		return reproduce(NORTH);
	}

	@Override
	protected void preMoveTrack(boolean[] foodpresent, int[] neighbors,
			int foodleft, int energyleft) {
		foodTracker.add(foodleft);
	}

	@Override
	protected void postMoveTrack(Move move, boolean[] foodpresent,
			int[] neighbors, int foodleft, int energyleft) {
		settingsTracker.psTrackerStore(energyleft, move.type()==REPRODUCE);	
		foodTracker.add(move, foodpresent);
		organismTracker.add(move, neighbors);
		turnNumber++;
	}
	
	@Override
	protected Move makeMove(boolean[] foodpresent, int[] neighbors,
			int foodleft, int energyleft) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "KnowledgePlayer";
	}

	
	protected Move reproduce(int direction) {
		return reproduce(direction, false);
	}
	
	protected Move reproduce(int direction, boolean wantsToComm) {
		DataForChild data = null;
		switch(direction) {
		case NORTH:
			data = new DataForChild(reverse(direction),
					-foodTracker.getX(),-(foodTracker.getY() + 1),turnNumber);
			break;
		case SOUTH:
			data = new DataForChild(reverse(direction),
					-foodTracker.getX(),-(foodTracker.getY() - 1),turnNumber);
			break;
		case EAST:
			data = new DataForChild(reverse(direction),
					-(foodTracker.getX() + 1),-foodTracker.getY(),turnNumber);
			break;
		case WEST:
			data = new DataForChild(reverse(direction),
					-(foodTracker.getX() - 1),-foodTracker.getY(),turnNumber);
			break;
		
		}
		
		directionOfCommunication = direction;
		if (data != null) {
			return new Move(REPRODUCE, direction, data.encode());
		}
		return null;
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
