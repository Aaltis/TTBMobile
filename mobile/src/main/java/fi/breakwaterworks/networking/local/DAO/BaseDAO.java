package fi.breakwaterworks.networking.local.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BaseDAO<T> {
    @Insert
    Long insert (T entity);

    @Insert
    void insertList (List<T> entityList);

    @Update
    void update (T entity);

    @Delete
    void delete (T entity);
}
