package datastructures;

public class Stake {
	private double smallBlind;
	private double bigBlind;
	private double ante;
	
	public Stake(double ante, double smallBlind, double bigBlind){
		this.ante = ante;
		this.smallBlind = smallBlind;
		this.bigBlind = bigBlind;
	}
	
	public Stake(double smallBlind, double bigBlind){
		this.ante = 0.0;
		this.smallBlind = smallBlind;
		this.bigBlind = bigBlind;
	}
	
	public double getSmallBlind() {
		return smallBlind;
	}

	public double getBigBlind() {
		return bigBlind;
	}

	public double getAnte() {
		return ante;
	}
}
