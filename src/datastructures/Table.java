package datastructures;

import java.util.ArrayList;
import java.util.LinkedList;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;


public class Table {
	public BiMap<Seat,Player> seatMap;
	public LinkedList<Player> seatedPlayers;
	int maxSeats;
	ArrayList<Card> board;
	private double pot;
	private Seat dealerSeat;
	
	
	public Table(int maxSeats){
		this.maxSeats = maxSeats;
		this.seatMap = HashBiMap.create();
		this.seatedPlayers = new LinkedList<>();
		this.pot = 0.0;
		this.dealerSeat = Seat.SEAT_1; //TODO: do we want to start with first seat? / make configurable
		this.board = new ArrayList<>();
		for (int i = 0; i < maxSeats; i++){
			addPlayerToTable(new Player(1500.0), i);
		}
	}
	
	public void placeCardOnBoard(Card card) {
		board.add(card);
	}
	
	public void addChipsToPot(double amount) {
		pot += amount;
	}
	public double getChipsInPot() {
		return pot;
	}
	
	public ArrayList<Card> getCardsOnBoard() {
		return board;
	}
	public void addPlayerToTable(Player playerToAdd, int seatNumber) {
		seatedPlayers.add(playerToAdd);
		seatMap.put(Seat.getSeatNumber(seatNumber), playerToAdd);
	}
	
	public Seat makePlayerDealer(Player dealer){
		dealerSeat = seatMap.inverse().get(dealer);
		return dealerSeat;
	}
	
	public Player getCurrentDealer(){
		return seatMap.get(dealerSeat);
	}

	/**
	 * Gets player immediately to the left of the dealer.
	 * @return Player in small blind position
	 */
	public Player getSmallBlindPLayer() {
		return seatMap.get(dealerSeat.getNextSeat());
	}
	/**
	 * Gets player two seats away from dealer (big blind position)
	 * @return Player in big blind position
	 */
	public Player getBigBlindPLayer() {
		return seatMap.get(dealerSeat.getArbitrarySeatToLeft(2));
	}
	
	public Player advanceDealerSeat(){
		// TODO: Handle a dead seat dealer.
		Player newDealer = null;
		while (newDealer == null) {
			dealerSeat = dealerSeat.getNextSeat();
			newDealer = seatMap.get(dealerSeat);
		}
		return newDealer;
	}
	
}
