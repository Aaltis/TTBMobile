package fi.breakwaterworks.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "exercise_set_reps_weight_join",
        primaryKeys = {"exercise_id", "repoId"},
        foreignKeys = {
                @ForeignKey(entity = Exercise.class,
                        parentColumns = "exercise_id",
                        childColumns = "exercise_id"),
                @ForeignKey(entity = SetRepsWeight.class,
                        parentColumns = "set_reps_weight_id",
                        childColumns = "set_reps_weight_id")
        })
public class ExerciseSetRepsWeightJoin {
    @ColumnInfo(name = "exercise_id")
    public final long exerciseId;
    @ColumnInfo(name = "set_reps_weight_id")
    public final long setRepsWeightId;

    public ExerciseSetRepsWeightJoin(final int exerciseId, final int setRepsWeightId) {
        this.exerciseId = exerciseId;
        this.setRepsWeightId = setRepsWeightId;
    }
}
