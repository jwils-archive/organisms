package organisms.g4;

import organisms.Move;
import organisms.OrganismsGame;
import organisms.g4.trackers.FoodTracker;
import organisms.g4.trackers.OtherOrganismTracker;
import organisms.g4.trackers.PersonalSettingsTracker;

public abstract class TrackingPlayer extends Group4BasePlayer {
	private FoodTracker foodTracker;
	private OtherOrganismTracker organismTracker;
	private PersonalSettingsTracker settingsTracker;

	@Override
	protected void init() {
		
	}

	@Override
	protected void preMoveTrack(boolean[] foodpresent, int[] neighbors,
			int foodleft, int energyleft) {
		foodTracker.trackFood(foodpresent, foodleft);
		organismTracker.trackOrganisms(neighbors);
	}

	@Override
	protected void postMoveTrack(Move move, boolean[] foodpresent,
			int[] neighbors, int foodleft, int energyleft) {
		settingsTracker.psTrackerStore(energyleft, move.type()==REPRODUCE);		
	}
}
