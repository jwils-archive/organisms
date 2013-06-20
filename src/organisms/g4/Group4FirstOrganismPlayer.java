package organisms.g4;

import java.util.*;
import java.io.*;
import java.awt.Color;

import organisms.*;
import organisms.g4.trackers.FoodTracker;
import organisms.g4.trackers.OtherOrganismTracker;
import organisms.g4.trackers.PersonalSettingsTracker;

public final class Group4FirstOrganismPlayer implements Player {

	static final String _CNAME = "Group 4 first player";
	static final Color _CColor = new Color(0.0f, 0.67f, 0.67f);
	private int state;
	private Random rand;
	private OrganismsGame game;

	private FoodTracker foodTracker;
	private OtherOrganismTracker organismTracker;
	private PersonalSettingsTracker settingsTracker;

	/*
	 * This method is called when the Organism is created. The key is the value
	 * that is passed to this organism by its parent (not used here)
	 */
	public void register(OrganismsGame game, int key) throws Exception {
		rand = new Random();
		state = rand.nextInt(256);
		this.game = game;

		foodTracker = new FoodTracker();
		organismTracker = new OtherOrganismTracker();
		settingsTracker = new PersonalSettingsTracker();
	}

	/*
	 * Return the name to be displayed in the simulator.
	 */
	public String name() throws Exception {
		return _CNAME;
	}

	/*
	 * Return the color to be displayed in the simulator.
	 */
	public Color color() throws Exception {
		return _CColor;
	}

	/*
	 * Not, uh, really sure what this is...
	 */
	public boolean interactive() throws Exception {
		return false;
	}

	/*
	 * This is the state to be displayed to other nearby organisms
	 */
	public int externalState() throws Exception {
		return state;
	}

	private boolean shouldMove(boolean[] foodpresent, int[] neighbors,
			int foodleft, int energyleft) {
		// if the organism has more than half the max possible energy reproduce
		// the direction is arbitrary so far
		if (energyleft <= 10 || foodleft == 0) {
			return false;
		}
		return false;
	}

	private boolean shouldReproduce(boolean[] foodpresent, int[] neighbors,
			int foodleft, int energyleft) {
		if (energyleft > 250) {
			return true;
		}

		return false;
	}

	private void preMoveTrack(boolean[] foodpresent, int[] neighbors, int foodleft, int energyleft) {
		foodTracker.trackFood(foodpresent, foodleft);
		organismTracker.trackOrganisms(neighbors);
	}

	private void postMoveTrack(Move move, boolean[] foodpresent, int[] neighbors, int foodleft, int energyleft) throws Exception {
		settingsTracker.psTrackerStore(energyleft, move.type()==REPRODUCE);
	}

	private Move reproduce(boolean[] foodpresent, int[] neighbors, int foodleft, int energyleft) throws Exception {
		return new Move(REPRODUCE, WEST, state);
	}

	private Move makeMove(boolean[] foodpresent, int[] neighbors, int foodleft, int energyleft) throws Exception {
		Move m = null;
		
				
		int direction = rand.nextInt(3);
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

	/*
	 * This is called by the simulator to determine how this Organism should
	 * move. foodpresent is a four-element array that indicates whether any food
	 * is in adjacent squares neighbors is a four-element array that holds the
	 * externalState for any organism in an adjacent square foodleft is how much
	 * food is left on the current square energyleft is this organism's
	 * remaining energy
	 */
	public Move move(boolean[] foodpresent, int[] neighbors, int foodleft, int energyleft)
			throws Exception {
		preMoveTrack(foodpresent, neighbors, foodleft, energyleft);
		System.out.println(foodpresent.length);

		Move move; // placeholder for return value

		if (shouldReproduce(foodpresent, neighbors, foodleft, energyleft)) {
			move = reproduce(foodpresent, neighbors, foodleft, energyleft);
		} else if (shouldMove(foodpresent, neighbors, foodleft, energyleft)) {
			move = makeMove(foodpresent, neighbors, foodleft, energyleft);
		} else {
			move = new Move(STAYPUT);
		}

		postMoveTrack(move, foodpresent, neighbors, foodleft, energyleft);

		return move;
	}

}
