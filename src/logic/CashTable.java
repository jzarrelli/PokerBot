package logic;

import datastructures.Rake;
import datastructures.Stake;

public class CashTable extends PokerSimulation{
	
	Rake rake;
	
	public CashTable(Stake stakes) {
		super(9);
		this.currentStakes = stakes;
	}



	public void simulateHands(int handsToSimulate) {
		while (handsToSimulate > 0 && shouldKeepPlaying()){
			handsToSimulate--;
			playHand();
		}
	}



	private boolean shouldKeepPlaying() {
		return true;
	}


}
