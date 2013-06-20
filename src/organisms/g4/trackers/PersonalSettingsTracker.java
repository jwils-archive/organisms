package organisms.g4.trackers;

import java.util.ArrayList;

public class PersonalSettingsTracker {
	ArrayList<Integer> energyAtTurn;
	int moveCount;
	ArrayList<Integer> turnsReproducedOn;
	//past energy
	//when reproduced(if reproduced much 
	//
	
	public PersonalSettingsTracker(){
		energyAtTurn = new ArrayList<Integer>();
		turnsReproducedOn = new ArrayList<Integer>();
		moveCount = 0;
	}
	
	public void psTrackerStore(int energyAtThisTurn, boolean reproducedThisTurn){
		moveCount++;
		energyAtTurn.add(energyAtThisTurn);
		if(reproducedThisTurn){
			turnsReproducedOn.add(moveCount);
		}
	}
	//return record of energy 
	public ArrayList<Integer> psTrackerGetEnergyHistory(){
		return energyAtTurn;
	}
	//return how much energy there was on a given turn
	public int psTrackerGetEnergyAtTurn(int turn){
		return energyAtTurn.get(turn);
	}
	//return how many moves moved
	public int psTrackerGetMoveCount(){
		return moveCount;
	}
	//return all turns reproduced on
	public ArrayList<Integer> psTrackerGetReproductionHistory(){
		return turnsReproducedOn;
	}
	//did the organism reproduce on a given turn
	public boolean psTrackerReproducedOnThisTurn(int turnInquiredAbout){
		if(turnsReproducedOn.contains(turnInquiredAbout)){
			return true;
		}
		return false;
	}
}
