package logic;

import datastructures.BetLimit;
import datastructures.Stake;

public class PokerController {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String task = args[0].toLowerCase();
		BetLimit limit = BetLimit.NoLimit;
		switch (task) {
			case "play":
				int handsToPlay = Integer.parseInt(args[1]);
				playHands(handsToPlay, limit);
				break;
			default:
				playHands(5000, limit);
				break;
		}
			
		
	}
	
	public static void playHands(int handsToPlay, BetLimit limit){
		Stake stakes = new Stake(5.0, 10.0);
		CashTable table = new CashTable(stakes, limit);
		table.simulateHands(handsToPlay);
	}

}
