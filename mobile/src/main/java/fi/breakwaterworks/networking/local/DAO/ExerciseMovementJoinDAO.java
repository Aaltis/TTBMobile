package fi.breakwaterworks.networking.local.DAO;

import androidx.room.Insert;

import fi.breakwaterworks.model.ExerciseMovementJoin;

public interface ExerciseMovementJoinDAO {

    @Insert
    void insert(ExerciseMovementJoin exerciseMovementJoin);


}
