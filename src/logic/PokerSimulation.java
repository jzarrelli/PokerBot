package logic;

import java.util.ArrayList;
import java.util.Random;

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
		highCardForDeal();
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
	
	private void highCardForDeal(){
		// We could deal cards and pick the highest one...
		// but for now we are lazy and will just randomly pick a player
		Random rand = new Random();
		rand.nextInt(players.size());
	}

	private void moveDealerSeat() {
		table.advanceDealerSeat();
	}

	private void determineWinnersAndAssignChips() {
		// TODO Auto-generated method stub
		
	}

	private boolean handShouldTerminate() {	
		// Always called after round of betting
		int foldedCount = 0;
		for (Player player : table.seatedPlayers) {
			foldedCount += player.hasPlayerFolded() ? 1 : 0;
		}
		// All but one players have folded or
		// We already dealt the river aka 5 cards on the board
		
		if (foldedCount >= (table.seatedPlayers.size() - 1) ||
			table.getCardsOnBoard().size() == 5) {
			return true;
		}
		
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
		double maxBet = 0.0;
		for (Player player : players){
			if (!player.isSittingOut()){
				maxBet = player.playRound(maxBet);
			}
		}
	}

	private void postAnteAndBlinds() {
		if (currentStakes.getAnte() > 0.0) {
			ante();
		}
		//Put small blind in pot
		Player smallBlind = table.getSmallBlindPLayer();
		smallBlind.putChipsInPot(currentStakes.getSmallBlind());
		
		//Put big blind in pot
		Player bigBlind = table.getBigBlindPLayer();
		bigBlind.putChipsInPot(currentStakes.getBigBlind());
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
