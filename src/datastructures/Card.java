package datastructures;

import java.util.Stack;


public class Card
{
	private final Rank rank;
	private final Suit suit;
	
	public Rank rank () { return rank; }
	public Suit suit() { return suit; }

	/**
	 * Creates a card of the given Rank and Suit. 
	 * @param rank The rank the card will have.
	 * @param suit The suit the card will have.
	 */
	public Card(Rank rank, Suit suit)
	{
		this.rank = rank;
		this.suit = suit;
	}
	
	/**
	 * This method converts the card to an int for use in the fast 
	 * hand-ranking algorithm. 
	 * @return an int representing the current card.
	 */
	public int getCardIndex() {
		return ((rank.getValue() - 2) << 2) + suit.ordinal() + 1;
	}
	
	/**
	 * Generates order Stack of Cards
	 * @return {@code Stack<Card>} 52 cards
	 */
	public static Stack<Card> getOrderedDeckofCards() {
		Stack<Card> orderedDeck = new Stack<>();

		for (Suit suit : Suit.values())//for each suit...
		{
			for (Rank rank : Rank.values())//for each rank...
			{
				orderedDeck.add(new Card(rank, suit));//2C,3C,...2D,3D...
			}
		}
		return orderedDeck;
	}
	
	public String toString()
	{
		return rank.toString() + " of " + suit.toString();
	}
	

}
