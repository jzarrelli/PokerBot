package logic;

import datastructures.BetLimit;
import datastructures.Rake;
import datastructures.Stake;

public class CashTable extends PokerSimulation{
	
	Rake rake;
	
	public CashTable(Stake stakes, BetLimit limit) {
		super(9, limit);
		this.currentStakes = stakes;
	}



	public void simulateHands(int handsToSimulate) {
		while (handsToSimulate > 0 && shouldKeepPlaying()){
			handsToSimulate--;
			deck.shuffleFullDeck();
			playHand();
		}
	}



	private boolean shouldKeepPlaying() {
		return true;
	}


}
