package datastructures;

import java.util.*;//  "*" imports all of java.util


public class Card
{
	public enum Rank {DUECE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE}
	public enum Suit {CLUBS, DIAMONDS, HEARTS, SPADES}	
	
	private final Rank rank;
	private final Suit suit;
	
	public Rank rank () { return rank; }
	public Suit suit() { return suit; }
	//this is a function which builds the new card objects called a constructor 	
	private Card(Rank myrank, Suit mysuit)
	{
		this.rank=myrank;//Setting the Objects Properties to an initial value 
		this.suit=mysuit;
	}
	
	public String toString()
	{
		return rank + "of" + suit;
	}
	
	private static final List<Card> protoDeck = new ArrayList<Card>(); //"= new ArrayList<Card>" is the syntax
	
	static
	{
		//populate entire deck 
		for (Suit suit : Suit.values())//for each suit...
		{
			//nested loop
			for (Rank rank : Rank.values())//for each rank...
			{
				protoDeck.add(new Card(rank, suit));//C2,C3,...D2,D3...
			}
		}
	}
	
	public static ArrayList<Card> newDeck() //function input blank 
	{
		return new ArrayList<Card>(protoDeck);//function return is the populated protoDeck List 
	}

	public static void main(String[] args) 
	{	
		ArrayList<Card> sillyDeck = newDeck();//variable type,new variable name, what it equals
		for (Card sillyCard : sillyDeck)
		{
			System.out.println(sillyCard.rank +" of "+ sillyCard.suit);
			
		}
		
	}
}
