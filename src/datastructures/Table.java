package datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Table {
	HashMap<Seat,Player> seatMap;
	public LinkedList<Player> seatedPlayers;
	int maxSeats;
	ArrayList<Card> board;
	
	
	public Table(int maxSeats){
		this.maxSeats = maxSeats;
		this.seatedPlayers = new LinkedList<>();
		this.board = new ArrayList<>();
		for (int i = 0; i < maxSeats; i++){
			addPlayerToTable(new Player(1500.0), i);
		}
	}
	
	public void placeCardOnBoard(Card card) {
		board.add(card);
	}
	

	public void addPlayerToTable(Player playerToAdd, int seatNumber) {
		seatedPlayers.add(playerToAdd);
		seatMap.put(Seat.getSeatNumber(seatNumber), playerToAdd);
	}
	
	
}
