package fi.breakwaterworks.TrackThatBarbellLibrary;

import java.sql.Date;
import java.util.Set;

public class WorkLogBase {
	
	protected Long worklogid;	
	protected String name;
	protected String description;
	protected Set<WorkoutBase> workouts;
	protected Set<UserBase> Users;
	protected String comment;
	protected Date Date;

}
