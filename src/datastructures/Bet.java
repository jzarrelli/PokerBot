package datastructures;

import java.util.Random;


public class Bet {
    public BetState bet;
    public double amount;
    
    /**
     * Create a Bet that has no corresponding amount: Check/Fold
     * @param bet
     */
    public Bet(BetState bet) {
        this.bet = bet;
        this.amount = 0;
    }
    
    /**
     * Create a Bet with a corresponding amount of chips
     * @param bet
     * @param amount
     */
    public Bet(BetState bet, double amount) {
        this.bet = bet;
        this.amount = amount;
    }
    
    public String toString() {
    	return bet.toString() + "Chips: " + String.valueOf(amount);
    }
    
}

/**
 * An enum of possible bets and relevant descriptions
 * @author aziehl
 *
 */
enum BetState {
    Call("Player has called"),
    Raise("Player has raised"),
    Check("Player has checked"),
    Fold("Player has folded");
    
    private String description;
    
    BetState(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return this.description;
    }
}
