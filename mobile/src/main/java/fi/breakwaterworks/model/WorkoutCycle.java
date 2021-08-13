package fi.breakwaterworks.model;

import androidx.annotation.NonNull;
import androidx.room.*;
import static androidx.room.ForeignKey.CASCADE;
import java.util.List;


@Entity(tableName = "workout_cycle",
        foreignKeys = @ForeignKey(entity = WorkoutCycle.class,
                parentColumns = "worklog_id",
                childColumns = "worklog_id",
                onDelete = CASCADE),
        indices = {@Index("workout_cycle_id"),
                @Index("workout_cycle_id")})
public class WorkoutCycle {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "workout_cycleId_id")
    private Long workoutCycleId;

    @Ignore
    private List<Workout> workouts;

    @Ignore
    private List<WorkoutCycle> subCycles;

    @NonNull
    public Long getWorkoutCycleId() {
        return workoutCycleId;
    }

    public void setWorkoutCycleId(@NonNull Long workoutCycleId) {
        this.workoutCycleId = workoutCycleId;
    }


    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }

    public List<WorkoutCycle> getSubCycles() {
        return subCycles;
    }

    public void setSubCycles(List<WorkoutCycle> subCycles) {
        this.subCycles = subCycles;
    }
}
