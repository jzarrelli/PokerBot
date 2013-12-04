package datastructures;

public enum BetLimit {
    /**
     * Fixed $X/$Y betting where $X may be bet in rounds 1-2, and X/Y maybe be bet in 3-4
     * Betting capped at 4 raises.  Does not use Head's up exception
     */
    FixedLimit("Betting set to Fixed Limit."),
    /**
     * No limit on betting (up to full stack).  Minimum bet is big blind, and minimum raise is previous bet/raise.
     */
    NoLimit("Betting set to No Limit."),
    /**
     * Cannot raise more than the total size of the pot, including:
     * 1. Chips in the pot from previous betting rounds (Starting pot)
     * 2. Previous action in the current betting round (Trail)
     * 3. A call from the player making the raise.
     */
    PotLimit("Betting set to Pot Limit");
    
    //TODO tournament pot limit
    
    private String tableLimit;
    
    BetLimit(String tableLimit) {
    	this.tableLimit = tableLimit;
    }
    
    @Override
    public String toString() {
        return this.tableLimit;
    }
}
