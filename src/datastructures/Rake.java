package datastructures;

public class Rake {
	boolean hasRakeWithoutFlop;
	double rakePercentage;
	double maxRake;
	
	public Rake(double rakePercentage, double maxRake){
		this(rakePercentage, maxRake, false);
	}

	public Rake(double rakePercentage, double maxRake, boolean hasRakeWithoutFlop) {
		this.rakePercentage = rakePercentage;
		this.maxRake = maxRake;
		this.hasRakeWithoutFlop = hasRakeWithoutFlop;
	}
	
	public double rakeHand(double pot, boolean hadFlop){
		double rake = 0.0;
		if (hadFlop || hasRakeWithoutFlop){
			rake = this.rakePercentage * pot;
			rake = rake > this.maxRake ? this.maxRake : rake;
		}
		return rake;
	}
}
