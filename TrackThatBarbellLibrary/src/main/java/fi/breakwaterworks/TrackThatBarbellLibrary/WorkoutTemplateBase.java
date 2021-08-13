package fi.breakwaterworks.TrackThatBarbellLibrary;

import java.util.List;
import java.util.Set;

public class WorkoutTemplateBase {
	protected List<ExerciseTemplateBase> exerciseList;
	protected Set<ExerciseBase> oneRepMaxList;
	protected Set<ExerciseBase> trainingMax;
	public WorkoutTemplateBase(){}

	public void calculateOneRepMax(List<ExerciseBase> exList){
		for (ExerciseBase ex : exList){
			ex.calculateOneRepMax();
		}
	}
	public void calculateTrainingMaxFromOneRm(List<ExerciseBase> exList,double trainingmaxpercent){
		for (ExerciseBase ex : exList){
			ex.calculateOneRepMax();
		}
	}
}
