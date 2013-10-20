package datastructures;

public enum Suit {
	CLUBS("Clubs"),
	DIAMONDS("Diamonds"),
	HEARTS("Hearts"),
	SPADES("Spades");
	
	private String name;
	
	Suit(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	}	