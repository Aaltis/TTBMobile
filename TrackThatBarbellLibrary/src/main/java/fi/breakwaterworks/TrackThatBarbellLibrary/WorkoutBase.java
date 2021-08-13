package fi.breakwaterworks.TrackThatBarbellLibrary;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorkoutBase {
	
	protected Set<ExerciseBase> exercises;
	protected List<WorkLogBase> worklogs;
	protected Date date;
	protected String comment;
	protected Long workoutId;
	
	public WorkoutBase() {}
	
	public WorkoutBase(Date date) {
		this.date=date;
		this.exercises=new HashSet<ExerciseBase>();
	}

}

