package datastructures;

public enum Seat {
	SEAT_1, SEAT_2, SEAT_3, SEAT_4, SEAT_5, SEAT_6, SEAT_7, SEAT_8, SEAT_9, SEAT_10;
	private final static Seat[] seats  = Seat.values();

	public static Seat getSeatNumber(int zeroBasedIndex){
		return seats[zeroBasedIndex];
	}
	
	public static Seat getFirstSeat(){
		return SEAT_1;
	}
	
	public Seat getNextSeat(){
		return getArbitrarySeatToLeft(1);
	}
	public Seat getArbitrarySeatToLeft(int seatsAway) {
		return seats[(this.ordinal()+seatsAway) % seats.length];
	}
}
