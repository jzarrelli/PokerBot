package datastructures;

import java.util.Collections;
import java.util.Stack;

/**
 * Contains 52 cards, handles shuffling and dealing.
 * 
 * 
 */
public class Deck {
	private Stack<Card> deck = new Stack<>();

	/**
	 * Generates a new ordered Stack of cards, assigns them, and then shuffles
	 * the deck.
	 */
	public Deck() {
		shuffleFullDeck();
	}

	public Stack<Card> getDeck() {
		return deck;
	}

	/**
	 * Gets a new deck, shuffles the entire deck
	 */
	public void shuffleFullDeck() {
		Stack<Card> orderedDeck = Card.getOrderedDeckofCards();
		this.deck = orderedDeck;
		shufflePartialDeck();
	}

	/**
	 * Shuffles current deck (# cards remaining) Linear time op.
	 */
	public void shufflePartialDeck() {
		Collections.shuffle(deck);
	}

	public Card dealCard() {
		return deck.pop();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Current Deck:");

		for (Card card : deck) {
			sb.append(card.toString());
		}

		return sb.toString();
	}

}
