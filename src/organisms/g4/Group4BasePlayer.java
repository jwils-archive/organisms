package organisms.g4;

import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

import organisms.Move;
import organisms.OrganismsGame;
import organisms.Player;

public abstract class Group4BasePlayer implements Player, Constants  {

	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	
	/**
	 * Environment Variables set on create.
	 */
	public int MAX_ENERGY = 0;
	public int ENERGY_PER_FOOD_UNIT = 0;
	public int MAX_FOOD_PER_CELL = 0;
	public int ENERGY_TO_MOVE = 0;
	public int ENERGY_TO_STAY_PUT = 0;
	
	protected int lastMove = -1;
	
	static final String _CNAME = "Group 4 first player";
	Color _CColor = new Color(0.0f, 0.67f, 0.67f);
	private int state;
	private Random rand;
	private OrganismsGame game;

	/*
	 * This method is called when the Organism is created. The key is the value
	 * that is passed to this organism by its parent (not used here)
	 */
	public void register(OrganismsGame game, int key) throws Exception {
		MAX_FOOD_PER_CELL = game.K();
		MAX_ENERGY = game.M();
		ENERGY_TO_STAY_PUT = game.s();
		ENERGY_PER_FOOD_UNIT = game.u();
		ENERGY_TO_MOVE = game.v();
		
		rand = new Random();
		this.game = game;
		
		init();
		register(key);
	}
	
	protected int nextRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	protected void init() {};
	
	protected abstract void register(int key);
	
	protected abstract Move reproduce(boolean[] foodpresent, int[] neighbors, int foodleft, int energyleft);

	protected abstract Move makeMove(boolean[] foodpresent, int[] neighbors, int foodleft, int energyleft);
	
	protected void preMoveTrack(boolean[] foodpresent, int[] neighbors, int foodleft, int energyleft) {};

	protected void postMoveTrack(Move move, boolean[] foodpresent, int[] neighbors, int foodleft, int energyleft) {};

	/*
	 * Return the name to be displayed in the simulator.
	 */
	public abstract String name();

	/*
	 * Return the color to be displayed in the simulator.
	 */
	public Color color() throws Exception {
		return _CColor;
	}

	public void setColor(float r, float g, float b) {
		_CColor = new Color(r, g, b);
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
	
	protected int getState() {
		return state;
	}

	protected void setState(int s) {
		state = s;
	}
	
	public OrganismsGame getGame() {
		return game;
	}
	
	/*
	 * This is called by the simulator to determine how this Organism should
	 * move. foodpresent is a four-element array that indicates whether any food
	 * is in adjacent squares neighbors is a four-element array that holds the
	 * externalState for any organism in an adjacent square foodleft is how much
	 * food is left on the current square energyleft is this organism's
	 * remaining energy
	 */
	public Move move(boolean[] foodpresent, int[] neighbors, int foodleft, int energyleft) {
		preMoveTrack(foodpresent, neighbors, foodleft, energyleft);

		Move move; // placeholder for return value

		move = reproduce(foodpresent, neighbors, foodleft, energyleft);
		
		if (move == null) {
			move = makeMove(foodpresent, neighbors, foodleft, energyleft);
		}
	
		if (move == null) {
			move = new Move(STAYPUT);
		}

		postMoveTrack(move, foodpresent, neighbors, foodleft, energyleft);
		
		lastMove = move.type();
		return move;
	}
	 
	public boolean isValidMove(int move, int[] neighbors) {
		if (neighbors[move] != -1) {
			return false;
		}
		return true;
	}
	
	public int[] getValidMoves(int[] neighbors) {
		int[] validMoves = new int[4];
		int validMoveCount = 0;
		for (int i = 1; i < 5; i++) {
			if (isValidMove(i, neighbors)) {
				validMoves[validMoveCount++] = i;
			}
		}
		return Arrays.copyOf(validMoves, validMoveCount);
	}
}
