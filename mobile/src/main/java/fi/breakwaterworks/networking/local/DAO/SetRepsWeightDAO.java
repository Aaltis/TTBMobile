package fi.breakwaterworks.networking.local.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import fi.breakwaterworks.model.SetRepsWeight;

@Dao
public interface SetRepsWeightDAO extends BaseDAO<SetRepsWeight> {
   // @Query("SELECT MAX(weight) FROM set_reps_weights INNER JOIN set_reps WHERE set_reps.reps = :set AND reps= :reps")
   // public List<SetRepsWeight> get(int set,int reps);

    @Insert
    long insert(SetRepsWeight setRepsWeight);

    @Query("SELECT * FROM set_reps_weights WHERE parent_exercise_id = :exerciseId")
    List<SetRepsWeight> loadWithExerciseId(long exerciseId);
}
