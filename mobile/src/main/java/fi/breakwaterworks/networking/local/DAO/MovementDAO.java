package fi.breakwaterworks.networking.local.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fi.breakwaterworks.model.Movement;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface MovementDAO extends BaseDAO<Movement> {
    @Insert
    void insertAll (Movement... movements);

    @Update
    void UpdateMovements(Movement... movements);

    @Query("SELECT * FROM Movements where Id=:id LIMIT 1")
    Movement getMovementWithId(Long id);

    @Query("SELECT * FROM Movements where name=:name LIMIT 1")
    Movement getMovementWithName(String name);

    //room is dumdum.
    @Query("SELECT * FROM Movements where name LIKE '%' || :name || '%'")
    Single<List<Movement>>  getMovementsWithNameLike(String name);

    @Query("SELECT * FROM Movements")
    Single<List<Movement>> getAllMovements();

    @Query("SELECT * FROM Movements")
    Observable<List<Movement>> getAllMovementsAsObservable();
}
