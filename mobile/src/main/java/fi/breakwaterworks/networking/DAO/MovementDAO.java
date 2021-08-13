package fi.breakwaterworks.networking.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import fi.breakwaterworks.model.Movement;
import io.reactivex.Single;

@Dao
public interface MovementDAO extends BaseDAO<Movement> {
    @Insert
    void insertAll (Movement... movements);

    @Query("SELECT * FROM Movements where movement_id=:id LIMIT 1")
    Movement getMovementWithId(Long id);

    @Query("SELECT * FROM Movements where name=:name LIMIT 1")
    Movement getMovementWithName(String name);

    @Query("SELECT * FROM Movements where name like :name")
    Single<List<Movement>>  getMovementsWithNameLike(String name);

    @Query("SELECT * FROM Movements")
    Single<List<Movement>> getAllMovements();

}
