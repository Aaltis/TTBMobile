package fi.breakwaterworks.model;

import androidx.room.*;
@Entity(tableName = "exercise_movement_join",
        primaryKeys = {"exercise_id", "movement_id"},
        foreignKeys = {
                @ForeignKey(entity = Exercise.class,
                        parentColumns = "exercise_id",
                        childColumns = "exercise_id"),
                @ForeignKey(entity = Movement.class,
                        parentColumns = "movement_id",
                        childColumns = "movement_id")
        })

public class ExerciseMovementJoin {
        public final long exerciseId;
        public final long movementId;

    public ExerciseMovementJoin(final int exerciseId, final int movementId) {
        this.exerciseId = exerciseId;
        this.movementId = movementId;
    }
}
