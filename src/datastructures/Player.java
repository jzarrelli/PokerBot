package datastructures;

import logic.Strategy;


/**
 * Represents a player playing poker
 * 
 */
public class Player {
    double chips;
    State playerState;
    Strategy strategy;
   

    /**
     * Creates a player with a stack of chips at a given State
     * @param chips # of chips the player has
     * @param playerState The players current state
     */
    Player(double chips, State playerState) {
        this.chips = chips;
        this.playerState = playerState;
    }
    
    /**
     * Creates a player with a stack of chips and an initial state
     * @param chips  # of chips the player has
     */
    Player(double chips) {
        this.chips = chips;
        this.playerState = State.InHand;
    }
    
    public State getPlayerState() {
        return playerState;
    }

    public void setPlayerState(State playerState) {
        this.playerState = playerState;
    }
    public double getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }
    
    /**
     * Puts chips in the pot. Returns the amount of chips actually put in. 
     * Will update the player state if the amount puts the player all in.
     * @param amount The amount required to be put in the pot.
     * @return The amount of chips actually put in the pot.
     */
    public double putChipsInPot(double amount) {
        if (this.playerState == State.InHand) {
        	if (amount < this.chips) {
        		this.chips -= amount;
        	}
        	else {
        		amount = this.chips;
        		this.chips = 0.0;
        		this.playerState = State.AllIn;
        	}
        	return amount;
        }
        else { return 0.0; }
            
    }

	public boolean isSittingOut() {
		return getPlayerState().equals(State.SittingOut);
	}
    
}

/**
 * An enum of states that a player can be in while playing
 * Each state has a description of that state, which could be printed in a game.
 * @author aziehl
 *
 */
enum State {

	Folded("Player has folded"), 
	InHand("Player is in hand"),
	AllIn("Player is all in."),
	SittingOut("Player is sitting out");

	private String description;

	State(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return this.description;
	}
} 


