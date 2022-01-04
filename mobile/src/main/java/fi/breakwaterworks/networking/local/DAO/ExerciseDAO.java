package fi.breakwaterworks.networking.local.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;

import java.util.List;

import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.utility.SetTypeEnum;

@Dao
public interface ExerciseDAO extends BaseDAO<Exercise> {

    @Query("SELECT * FROM exercises WHERE exercise_id = :exerciseId")
    List<Exercise> fetchOnExercisebyExerciseId (int exerciseId);

    @Query("SELECT * FROM exercises WHERE workout_id = :id")
    List<Exercise> loadExercisesWithWorkoutId (long id);

    /*@Query("SELECT * FROM exercises INNER JOIN exercise_set_reps_weight_join " +
            " ON  exercise_set_reps_weight_join.exerciseId = exercise_id INNER JOIN set_reps_weights " +
            " ON set_reps_weight_id = exercise_set_reps_weight_join.setRepsWeightId " +
            " where exercises.setTypeEnum=:setTypeEnum and set_reps_weights.reps=:reps and set_reps_weights.sets=:sets and success=:success" +
            " order by set_reps_weights.weight")
    Exercise loadSuccessfullExerciseWithSetRepsWeight (SetTypeEnum setTypeEnum, long reps, long sets, boolean success);*/

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM exercises INNER JOIN set_reps_weights " +
            " ON  set_reps_weights.parent_exercise_id = exercise_id " +
            " where exercises.setTypeEnum=:setTypeEnum and set_reps_weights.reps=:reps and set_reps_weights.sets=:sets and success=:success" +
            " order by set_reps_weights.weight")
    Exercise loadSuccessfullExerciseWithSetRepsWeight (SetTypeEnum setTypeEnum, long reps, long sets, boolean success);
    @Insert
    long insert(Exercise exercise);
}
