package datastructures;

import java.util.LinkedList;

public class Table {
	Player seatOne;
	Player seatTwo;
	Player seatThree;
	Player seatFour;
	Player seatFive;
	Player seatSix;
	Player seatSeven;
	Player seatEight;
	Player seatNine;
	Player seatTen;
	int maxSeats;
	public LinkedList<Player> seatedPlayers;
	
	public Table(int maxSeats){
		this.maxSeats = maxSeats;
		this.seatedPlayers = new LinkedList<>();
		for (int i = 0; i < maxSeats; i++){
			addPlayerToTable(new Player(1500.0));
		}
	}

	public void addPlayerToTable(Player playerToAdd) {
		seatedPlayers.add(playerToAdd);
		// TODO: Put at appropriate seat, possibly add check to ensure
		// that we don't have too many players.
	}
	
	
}
