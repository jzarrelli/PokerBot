package datastructures;

import java.util.ArrayList;

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
		this(chips, PlayerState.Check);
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

	public boolean isFolded() {
		return playerState.equals(PlayerState.Folded);
	}
	public boolean isSittingOut() {
		return playerState.equals(PlayerState.SittingOut);
	}
	public void setPlayerState(PlayerState playerState) {
		this.playerState = playerState;
	}
	
	public boolean isAllIn() {
		if(chips == 0.0) {
			setPlayerState(PlayerState.AllIn);
			return true;
		}
		return false;
	}

	public double getChips() {
		return chips;
	}

	public void setChips(double chips) {
		this.chips = chips;
	}
	
	public void giveChips(double chips) {
		this.chips += chips;
	}

	public void addCardToHand(Card card) {
		cards.add(card);
	}
	
	public void resetHand() {
		cards.clear();
	}
	public void resetPlayerState() {
		//TODO: sit out if in bad position
		setPlayerState(PlayerState.Check);
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
		if (!isFolded() || !isSittingOut()) {
			if (amount < this.chips) {
				this.chips -= amount;
			} else {
				amount = this.chips;
				this.chips = 0.0;
				isAllIn();
			}
			System.out.println(toString()); //TODO: just for testing
			return amount;
		} else {
			return 0.0;
		}

	}

	public double playRound(double currentBet, double minRaise, double maxRaise) {
		// TODO actual strategy
		// Rudimentary strat, always min raises / call if possible
		// Stays in as long as possible
		double betAmount = 0.0;
		if (!isFolded() || !isSittingOut()) {
			if(currentBet > 0.0 && chips >= (currentBet + minRaise)) {
				betAmount = currentBet + minRaise; //Min Raise
				setPlayerState(PlayerState.Raise);
			} 
			else if(currentBet > 0.0 && chips >= currentBet) {
				betAmount = currentBet; //Call bot
				setPlayerState(PlayerState.Call);
			} 
			else if (chips < currentBet) {
				setPlayerState(PlayerState.Folded);
			}
			else if (currentBet == 0.0) {
				betAmount = minRaise; //Min bet
				setPlayerState(PlayerState.Bet);
			}
			else {
				setPlayerState(PlayerState.Check);
			}
		} 
		return putChipsInPot(betAmount);
	}

	public String toString() {
		//TODO: print seat # and hand value
		StringBuilder sb = new StringBuilder();
		sb.append(playerState.toString());
		sb.append("Player has " + String.valueOf(this.chips) + " Chips, ");
		sb.append("and a hand of: ");
		for (Card card : cards) {
			sb.append(card.toString() + " ");
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

		Folded("Player has folded. "),
		AllIn("Player is all in. "),
		Raise("Player has raised. "),
		Bet("Player has bet. "),
		Call("Player has called. "),
		Check("Player has checked. "),
		SittingOut("Player is sitting out. ");

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
