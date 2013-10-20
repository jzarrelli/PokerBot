package logic;

import java.util.ArrayList;

import datastructures.Deck;
import datastructures.Player;
import datastructures.Stake;
import datastructures.Table;

public class PokerSimulation {	
	
	Table table;
	Deck deck;
	ArrayList<Player> players;
	Stake currentStakes;

	protected PokerSimulation(int numberOfPlayers) {
		table = new Table(numberOfPlayers);
		deck = new Deck();
		players = new ArrayList<>();
		players.addAll(table.seatedPlayers);
	}
	
	protected void playHand(){
		postAnteAndBlinds();
		dealHoleCards();
		performRoundOfBetting();
		if (handShouldTerminate()) {
			doHandEndRoutine();
			return;
		}
		dealFlop();
		performRoundOfBetting();
		if (handShouldTerminate()) {
			doHandEndRoutine();
			return;
		}
		dealTurn();
		performRoundOfBetting();
		if (handShouldTerminate()) {
			doHandEndRoutine();
			return;
		}
		dealRiver();
		performRoundOfBetting();
		if (handShouldTerminate()) {
			doHandEndRoutine();
			return;
		}
		
	}

	private void doHandEndRoutine() {
		determineWinnersAndAssignChips();
		updatePlayerStates();
		moveDealerSeat();
	}

	private void updatePlayerStates() {
		// TODO Auto-generated method stub
		
	}

	private void moveDealerSeat() {
		// TODO Auto-generated method stub
		
	}

	private void determineWinnersAndAssignChips() {
		// TODO Auto-generated method stub
		
	}

	private boolean handShouldTerminate() {
		// TODO Auto-generated method stub
		return false;
	}

	private void dealRiver() {
		burnACard();
		dealACommunityCard();
	}

	private void dealACommunityCard() {
		table.placeCardOnBoard(deck.dealCard());
		
	}

	/**
	 * Deal a card into thin air
	 */
	private void burnACard() {
		deck.dealCard();
		
	}

	private void dealTurn() {
		burnACard();
		dealACommunityCard();
	}

	private void dealFlop() {
		burnACard();
		dealACommunityCard();
		dealACommunityCard();
		dealACommunityCard();
	}

	private void performRoundOfBetting() {
		// TODO Auto-generated method stub
	}

	private void postAnteAndBlinds() {
		if (currentStakes.getAnte() > 0.0) {
			ante();
		}
		// TODO: Keep track of dealer seat and post blinds accordingly.
	}

	private void ante() {
		for (Player player : players) {
			if (!player.isSittingOut()) {
				player.putChipsInPot(currentStakes.getAnte());
			}
		}
		
	}

	private void dealHoleCards() {
		dealHoleCard();
		dealHoleCard();
	}

	private void dealHoleCard() {
		for (Player player : players){
			if (!player.isSittingOut()){
				dealCardToPlayer(player);
			}
		}
	}

	private void dealCardToPlayer(Player player) {
		player.addCardToHand(deck.dealCard());
		
		
	}
}
