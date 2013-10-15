package logic;

import datastructures.Stake;

public class PokerController {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String task = args[0].toLowerCase();
		switch (task) {
			case "play":
				int handsToPlay = Integer.parseInt(args[1]);
				playHands(handsToPlay);
				break;
			default:
				playHands(5000);
				break;
		}
			
		
	}
	
	public static void playHands(int handsToPlay){
		Stake stakes = new Stake(5.0, 10.0);
		CashTable table = new CashTable(stakes);
		table.simulateHands(5000);
	}

}
