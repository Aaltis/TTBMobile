package fi.breakwaterworks.networking.local.DAO;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import fi.breakwaterworks.model.User;

@Dao
public interface UserDAO extends BaseDAO<User> {
    @Update
    void update(User user);

    @Query("select initDone from users where name=:name ")
    boolean isInitialized(String name);
}
