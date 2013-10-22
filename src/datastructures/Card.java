package datastructures;

import java.util.*;


public class Card
{
	private final Rank rank;
	private final Suit suit;
	
	public Rank rank () { return rank; }
	public Suit suit() { return suit; }

	private Card(Rank rank, Suit suit)
	{
		this.rank = rank;
		this.suit = suit;
	}
	
	/**
	 * Generates order Stack of Cards
	 * @return Stack<Card> 52 cards
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
