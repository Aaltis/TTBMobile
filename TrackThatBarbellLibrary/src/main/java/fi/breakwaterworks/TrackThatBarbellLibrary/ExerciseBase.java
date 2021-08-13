package fi.breakwaterworks.TrackThatBarbellLibrary;

import java.util.Set;

public class ExerciseBase {
	
	public ExerciseBase(){		
	}
	protected Long Id;
	protected Long orderNumber;
	protected MovementBase movement;
	protected Set<SetRepsWeightBase> setrepsweights;
	protected double onRepMax;
	
	public double calculateOneRepMax(){
		switch(setrepsweights.iterator().next().reps){
		case 1: 	onRepMax= setrepsweights.iterator().next().weight;  
		case 2: 	onRepMax= (setrepsweights.iterator().next().weight/95); 
		case 3: 	onRepMax= (setrepsweights.iterator().next().weight/93); 
		case 4: 	onRepMax= (setrepsweights.iterator().next().weight/90); 
		case 5: 	onRepMax= (setrepsweights.iterator().next().weight/87); 
		case 6: 	onRepMax= (setrepsweights.iterator().next().weight/85); 
		case 7: 	onRepMax= (setrepsweights.iterator().next().weight/83); 
		case 8: 	onRepMax= (setrepsweights.iterator().next().weight/80); 
		case 9: 	onRepMax= (setrepsweights.iterator().next().weight/77); 
		case 10: 	onRepMax= (setrepsweights.iterator().next().weight/75); 
		case 11: 	onRepMax= (setrepsweights.iterator().next().weight/73); 
		case 12:	onRepMax= (setrepsweights.iterator().next().weight/70); 
		}
		return 0;	
	}
}
