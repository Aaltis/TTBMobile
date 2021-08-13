package fi.breakwaterworks.TrackThatBarbellLibrary;

public class SetRepsWeightBase {
	
	protected Long id;	
	protected int orderNumber;
  	protected int set;
  	protected int reps;	
	protected double weight;
	protected String weightUnit;

	public SetRepsWeightBase(){		
	}
	public SetRepsWeightBase(int orderNumber, int set, int reps, double weight, String weightUnit) {
		super();
		this.orderNumber = orderNumber;
		this.set = set;
		this.reps = reps;
		this.weight = weight;
		this.weightUnit = weightUnit;
	}
	

}
