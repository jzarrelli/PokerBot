package logic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import logic.lookuptablegenerator.LookupTableGenerator;
import datastructures.Card;
import datastructures.Player;
import datastructures.Table;

public class HandEvaluator {
	

	private static int lookupTable[] = init_rank_table();
	private int[] boardIndexes = new int[5];
	private int[] playerIndexes = new int[2];
	private int tableRank = 0;
	
	/**
	 * This method readies this instance to evaluate a hand at the given table. Only call this method
	 * after all 5 board cards have been dealt on the given table.
	 * @param table The table to evaluate.
	 */
	public void initializeTable(Table table) {
		List<Card> board = table.getCardsOnBoard();
		int boardLength = board.size();
		if (boardLength != 5)
			throw new IllegalArgumentException("Must have all 5 board cards to evaluate a hand");
		for (int i = 0; i < board.size(); i++) {
			boardIndexes[i] = board.get(i).getCardIndex();
		}
		rankTable();
	}
	
	/**
	 * Evaluates a player at a given table. Note, this changes the state of this instance 
	 * such that future calls to {@link #evaluatePlayerAtCurrentTable(Player)} will be 
	 * evaluated at the given table. Only call this method after all 5 board cards have 
	 * been dealt on the given table.
	 * @param playerToEvaluate The player whose hand you want to evaluate.
	 * @param table The table on which to evaluate their hand.
	 * @return An int representing their hand strength. Higher is better.
	 */
	public int evaluatePlayerHandAtTable(Player playerToEvaluate, Table table) {
		initializeTable(table);
		return evaluatePlayerAtCurrentTable(playerToEvaluate);
	}
	
	/**
	 * Evaluates a player at the current table. Only call this method after this instance 
	 * has been initialized with a table, either via {@link #initializeTable(Table)} or 
	 * {@link #evaluatePlayerHandAtTable(Player, Table)}.
	 * @param playerToEvaluate The player whose hand you want to evaluate.
	 * @return An int representing their hand strength. Higher is better.
	 */
	public int evaluatePlayerAtCurrentTable(Player playerToEvaluate) {
		List<Card> playerCards = playerToEvaluate.getHand();
		playerIndexes[0] = playerCards.get(0).getCardIndex();
		playerIndexes[1] = playerCards.get(1).getCardIndex();
		return rank();
	}

	private void rankTable() {
		int p = 53;
		for (int i=0;i<boardIndexes.length;i++) 
			p = lookupTable[p + boardIndexes[i]];
		tableRank = p;
	}
	

	private int rank() {
		int p = tableRank;
		for (int i=0;i<playerIndexes.length;i++) 
			p = lookupTable[p + playerIndexes[i]];
		return p;
	}
	

	private static int[] init_rank_table() {
		try (ObjectInputStream inStream = new ObjectInputStream(new FileInputStream("resources\\handRanks.dat"));){
			return (int[]) inStream.readObject();
		} catch (Exception e) {
			System.err.println("Hand Ranks file not found. Generating new one. " + e.getMessage());
			try (ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream("resources\\handRanks.dat"));){
				outStream.writeObject(LookupTableGenerator.generateTables());
			} catch (Exception e1) {
				System.err.println("Hand Ranks file not saved to disk." + e.getMessage());
			} 
			return LookupTableGenerator.generateTables();
		}
	}
}
