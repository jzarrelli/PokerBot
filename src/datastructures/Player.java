package datastructures;

import java.util.ArrayList;
import java.util.Random;

import logic.Strategy;

/**
 * Represents a player playing poker
 * 
 */
public class Player {
	double chips;
	PlayerState playerState;
	Strategy strategy;
	ArrayList<Card> cards;

	/**
	 * Creates a player with a stack of chips at a given State
	 * 
	 * @param chips
	 *            # of chips the player has
	 * @param playerState
	 *            The players current state
	 */
	Player(double chips, PlayerState playerState) {
		this.chips = chips;
		this.playerState = playerState;
		this.cards = new ArrayList<>(2);
	}

	/**
	 * Creates a player with a stack of chips and an initial state
	 * 
	 * @param chips
	 *            # of chips the player has
	 */
	Player(double chips) {
		this(chips, PlayerState.InHand);
	}
	
	/**
	 * Creates a player with a stack of 1500 chips. This method is only meant for testing.
	 */
	public Player() {
		this(1500.0);
	}

	public PlayerState getPlayerState() {
		return playerState;
	}

	public boolean hasPlayerFolded() {
		return playerState.equals(PlayerState.Folded);
	}

	public void setPlayerState(PlayerState playerState) {
		this.playerState = playerState;
	}

	public double getChips() {
		return chips;
	}

	public void setChips(double chips) {
		this.chips = chips;
	}

	public void addCardToHand(Card card) {
		cards.add(card);
	}
	
	public void resetHand() {
		cards.clear();
	}
	
	public ArrayList<Card> getHand() {
		return cards;
	}

	/**
	 * Puts chips in the pot. Returns the amount of chips actually put in. Will
	 * update the player state if the amount puts the player all in.
	 * 
	 * @param amount
	 *            The amount required to be put in the pot.
	 * @return The amount of chips actually put in the pot.
	 */
	public double putChipsInPot(double amount) {
		if (this.playerState == PlayerState.InHand) {
			if (amount < this.chips) {
				this.chips -= amount;
			} else {
				amount = this.chips;
				this.chips = 0.0;
				this.playerState = PlayerState.AllIn;
			}
			return amount;
		} else {
			return 0.0;
		}

	}

	public boolean isSittingOut() {
		return getPlayerState().equals(PlayerState.SittingOut);
	}

	public double playRound(double amountToCall) {
		// TODO a lot
		// determine bet
		// set state based on bet
		// put chips in pot if any
		// if raise, track amount to call
		double betAmount = 0;
		Random rand = new Random();
		if (playerState.equals(PlayerState.InHand)) {
			if(amountToCall > 0 && chips >= amountToCall) {
				betAmount = amountToCall;
			} else if (chips < amountToCall) {
				playerState = PlayerState.Folded;
			}
			else {
				betAmount = this.chips * rand.nextDouble();
			}
		} 
		putChipsInPot(betAmount);
		return betAmount;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Player has " + String.valueOf(this.chips) + " Chips, ");
		sb.append("and a hand of: ");
		for (Card card : cards) {
			sb.append(card.toString());
		}
		return sb.toString();
	}

	/**
	 * An enum of states that a player can be in while playing Each state has a
	 * description of that state, which could be printed in a game.
	 * 
	 * @author aziehl
	 * 
	 */
	enum PlayerState {

		Folded("Player has folded"), InHand("Player is in hand"), AllIn(
				"Player is all in."), SittingOut("Player is sitting out");

		private String description;

		PlayerState(String description) {
			this.description = description;
		}

		@Override
		public String toString() {
			return this.description;
		}
	}
}
