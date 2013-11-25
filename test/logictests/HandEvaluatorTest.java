package logictests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import logic.HandEvaluator;

import org.junit.Before;
import org.junit.Test;

import datastructures.Card;
import datastructures.Player;
import datastructures.Rank;
import datastructures.Suit;
import datastructures.Table;

public class HandEvaluatorTest {
	
	private HandEvaluator handEvaluator;
	
	private Table table1;
	private Table table2;
	
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;
	private Player player5;
	private Player player6;

	@Before
	public void setUp() throws Exception {
		
		handEvaluator = new HandEvaluator();
		
		setupTable1();
		setupTable2();
		
		setupPlayer1();
		setupPlayer2();
		setupPlayer3();
		setupPlayer4();
		setupPlayer5();
		setupPlayer6();
		
	}

	private void setupTable1() {
		table1 = new Table(0);
		table1.placeCardOnBoard(new Card(Rank.ACE, Suit.CLUBS));
		table1.placeCardOnBoard(new Card(Rank.ACE, Suit.DIAMONDS));
		table1.placeCardOnBoard(new Card(Rank.ACE, Suit.SPADES));
		table1.placeCardOnBoard(new Card(Rank.ACE, Suit.HEARTS));
		table1.placeCardOnBoard(new Card(Rank.QUEEN, Suit.CLUBS));
	}
	
	private void setupTable2() {
		table2 = new Table(0);
		table2.placeCardOnBoard(new Card(Rank.DUECE, Suit.HEARTS));
		table2.placeCardOnBoard(new Card(Rank.SIX, Suit.HEARTS));
		table2.placeCardOnBoard(new Card(Rank.THREE, Suit.CLUBS));
		table2.placeCardOnBoard(new Card(Rank.NINE, Suit.CLUBS));
		table2.placeCardOnBoard(new Card(Rank.TEN, Suit.CLUBS));
	}
	
	private void setupPlayer1(){
		player1 = new Player();
		player1.addCardToHand(new Card(Rank.DUECE, Suit.CLUBS));
		player1.addCardToHand(new Card(Rank.DUECE, Suit.SPADES));
	}
	
	private void setupPlayer2(){
		player2 = new Player();
		player2.addCardToHand(new Card(Rank.ACE, Suit.CLUBS));
		player2.addCardToHand(new Card(Rank.ACE, Suit.SPADES));
	}
	
	private void setupPlayer3(){
		player3 = new Player();
		player3.addCardToHand(new Card(Rank.JACK, Suit.CLUBS));
		player3.addCardToHand(new Card(Rank.KING, Suit.CLUBS));
	}
	
	private void setupPlayer4(){
		player4 = new Player();
		player4.addCardToHand(new Card(Rank.SIX, Suit.DIAMONDS));
		player4.addCardToHand(new Card(Rank.EIGHT, Suit.SPADES));
	}
	
	private void setupPlayer5(){
		player5 = new Player();
		player5.addCardToHand(new Card(Rank.FOUR, Suit.DIAMONDS));
		player5.addCardToHand(new Card(Rank.FIVE, Suit.SPADES));
	}
	
	private void setupPlayer6(){
		player6 = new Player();
		player6.addCardToHand(new Card(Rank.FOUR, Suit.HEARTS));
		player6.addCardToHand(new Card(Rank.SEVEN, Suit.SPADES));
	}

	@Test
	public void testEvaluateTable1() {
		handEvaluator.initializeTable(table1);
		// Quad Aces, Queen kicker
		int player1HandScore = handEvaluator.evaluatePlayerAtCurrentTable(player1);
		// Quad Aces, King kicker
		int player3HandScore = handEvaluator.evaluatePlayerAtCurrentTable(player3);
		// Quad Aces, Queen kicker
		int player4HandScore = handEvaluator.evaluatePlayerAtCurrentTable(player4);
		assertEquals(player1HandScore, player4HandScore);
		assertTrue(player1HandScore < player3HandScore);
	}
	
	@Test
	public void testEvaluateTable2() {
		handEvaluator.initializeTable(table2);
		// Set
		int player1HandScore = handEvaluator.evaluatePlayerAtCurrentTable(player1);
		// Pair of Aces
		int player2HandScore = handEvaluator.evaluatePlayerAtCurrentTable(player2);
		// Flush
		int player3HandScore = handEvaluator.evaluatePlayerAtCurrentTable(player3);
		// Pair of sixes
		int player4HandScore = handEvaluator.evaluatePlayerAtCurrentTable(player4);
		// Straight 2-6
		int player5HandScore = handEvaluator.evaluatePlayerAtCurrentTable(player5);
		// High card
		int player6HandScore = handEvaluator.evaluatePlayerAtCurrentTable(player6);
		
		assertTrue(player5HandScore < player3HandScore);
		assertTrue(player1HandScore < player5HandScore);
		assertTrue(player2HandScore < player1HandScore);
		assertTrue(player4HandScore < player2HandScore);
		assertTrue(player6HandScore < player4HandScore);
	}

}
