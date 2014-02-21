package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import datastructures.BetLimit;
import datastructures.Deck;
import datastructures.Player;
import datastructures.Seat;
import datastructures.Stake;
import datastructures.Table;

public class PokerSimulation {

	Table table;
	Deck deck;
	BetLimit betLimit;
	ArrayList<Player> players;
	Stake currentStakes;
	HandEvaluator handEvaluator;

	protected PokerSimulation(int numberOfPlayers, BetLimit limit) {
		table = new Table(numberOfPlayers);
		deck = new Deck();
		betLimit = limit;
		players = new ArrayList<>();
		handEvaluator = new HandEvaluator();
		players.addAll(table.seatedPlayers);
		highCardForDeal();
	}

	protected void playHand() {
		postAnteAndBlinds();
		dealHoleCards();
		handEvaluator = new HandEvaluator();
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

	protected BetLimit getBetLimit() {
		return betLimit;
	}

	protected int getCurrentRound() {
		int cardsOnTable = table.getCardsOnBoard().size();

		switch (cardsOnTable) {

		case 0:
			return 1;
		case 3:
			return 2;
		case 4:
			return 3;
		case 5:
			return 4;
		default: // Shouldn't get here
			return 1;
		}

	}

	private void doHandEndRoutine() {
		determineWinnersAndAssignChips();
		updatePlayerStates();
		moveDealerSeat();
	}

	private void updatePlayerStates() {
		for ( Player player : table.seatedPlayers) {
			player.resetHand();
			player.resetPlayerState();
		}

	}

	private void highCardForDeal() {
		// We could deal cards and pick the highest one...
		// but for now we are lazy and will just randomly pick a player
		Random rand = new Random();
		rand.nextInt(players.size());
	}

	private void moveDealerSeat() {
		table.advanceDealerSeat();
	}
	
	private void assignChips(ArrayList<Player> winners) {
		// TODO: side pots
		// TODO: minus rake
		double chipsPerWinner = table.getChipsInPot() / (double) winners.size();
		for ( Player player : winners) {
			player.giveChips(chipsPerWinner);
			printWinnerAndHand(player); // TODO: testing only
		}
	}
	
	private void printWinnerAndHand(Player player) {
		String seatNumber = table.seatMap.inverse().get(player).name();
		int playerHandStrength = handEvaluator.evaluatePlayerHandAtTable(player, table);
		System.out.println("Player at: " + seatNumber + " wins. " +  "Hand Value: " + String.valueOf(playerHandStrength));
		player.toString();
	}
	private ArrayList<Player> determineWinner() {
		HashMap<Integer, ArrayList<Player>> valuesToPlayers = new HashMap<>();
		for (Player player : table.seatedPlayers) {
			int playerHandStrength = handEvaluator.evaluatePlayerHandAtTable(player, table);
			if (!valuesToPlayers.containsKey(playerHandStrength)) {
				ArrayList<Player> players = new ArrayList<>();
				players.add(player);
				valuesToPlayers.put(playerHandStrength, players);
			}
			else {
				ArrayList<Player> players = valuesToPlayers.get(playerHandStrength);
				players.add(player);
				valuesToPlayers.put(playerHandStrength, players);
			}
			
		}
		int maxStrength = 0;
		for (int handStrength : valuesToPlayers.keySet()) {
			if (handStrength > maxStrength) {
				maxStrength = handStrength;
			}
		}
		return valuesToPlayers.get(maxStrength);
	}
	
	private void determineWinnersAndAssignChips() {
		
		if (playersInHand().size() == 1) {
			// Assign chips to player still in hand
			assignChips(playersInHand());
		}
		//Otherwise determine winner/tie based on hand strength
		ArrayList<Player> winners = determineWinner();
		assignChips(winners);

	}

	private boolean handShouldTerminate() {
		// Always called after round of betting
		// All but one players have folded or
		// We already dealt the river aka 5 cards on the board
		return playersInHand().size() == 1 || allCardsOnBoard();
	}

	private ArrayList<Player> playersInHand() {
		ArrayList<Player> playersInHand = new ArrayList<>();
		for (Player player : table.seatedPlayers) {
			if( !player.isFolded() ) {
				playersInHand.add(player);
			}
		}
		return playersInHand;
	}

	private boolean allCardsOnBoard() {
		return table.getCardsOnBoard().size() == 5;
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
		switch (betLimit) {
		case FixedLimit:
			performRoundOfFixedLimitBetting();
			break;
		case PotLimit:
			performRoundOfPotLimitBetting();
			break;
		case NoLimit:
			performRoundOfNoLimitBetting();
			break;
		default:
			break;
		}
	}

	// See BetLimit enum for Pot Limit rules inferred
	// TODO: tournament pot limit
	private void performRoundOfPotLimitBetting() {
		double bigBlind = currentStakes.getBigBlind();
		double currentBet = 0.0;
		double trail = 0.0;
		double minRaise = bigBlind;
		double startingPot = table.getChipsInPot();
		double maxRaise = startingPot + bigBlind; // initial max raise

		for (Player player : players) {
			if (!player.isSittingOut()) {
				currentBet = player.playRound(currentBet, minRaise, maxRaise);
				table.addChipsToPot(currentBet);
				maxRaise = (3.0 * currentBet) + trail + startingPot;
				trail += currentBet;
			}
		}
	}

	// See BetLimit enum for Fixed Limit rules inferred
	private void performRoundOfFixedLimitBetting() {
		double currentBet = 0.0;
		double bigBlind = currentStakes.getBigBlind();
		// TODO: assumes fixed bet is big blind for now
		// Big bet is 2x fixed bet
		double maxBet = getCurrentRound() > 2 ?  bigBlind * 2.0 : bigBlind; 
		double raise = bigBlind;
		double fixedBetCap = maxBet * 4.0;
		for (Player player : players) {
			if (!player.isSittingOut()) {
				currentBet = player.playRound(currentBet, raise, raise);
				table.addChipsToPot(currentBet);
				if (currentBet == fixedBetCap) {
					raise = 0.0; //No more raising
				}
			}
		}
	}

	// See BetLimit enum for No Limit rules inferred
	private void performRoundOfNoLimitBetting() {
		double currentBet = 0.0;
		double maxRaise = Double.MAX_VALUE; // Any bet
		double minRaise = currentStakes.getBigBlind();
		for (Player player : players) {
			if (!player.isSittingOut()) {
				currentBet = player.playRound(currentBet, minRaise,  maxRaise);
				table.addChipsToPot(currentBet);
				minRaise = currentBet;
			}
		}

	}

	private void postAnteAndBlinds() {
		if (currentStakes.getAnte() > 0.0) {
			ante();
		}

	}

	private void ante() {
		for (Player player : players) {
			if (!player.isSittingOut()) {
				player.putChipsInPot(currentStakes.getAnte());
			}
		}
		smallBlind();
		bigBlind();

	}

	private void smallBlind() {
		// Put small blind in pot
		Player smallBlind = table.getSmallBlindPLayer();
		table.addChipsToPot(smallBlind.putChipsInPot(currentStakes
				.getSmallBlind()));
	}

	private void bigBlind() {
		// Put big blind in pot
		Player bigBlind = table.getBigBlindPLayer();
		table.addChipsToPot(bigBlind.putChipsInPot(currentStakes.getBigBlind()));
	}

	private void dealHoleCards() {
		dealHoleCard();
		dealHoleCard();
	}

	private void dealHoleCard() {
		for (Player player : players) {
			if (!player.isSittingOut()) {
				dealCardToPlayer(player);
			}
		}
	}

	private void dealCardToPlayer(Player player) {
		player.addCardToHand(deck.dealCard());

	}
}
