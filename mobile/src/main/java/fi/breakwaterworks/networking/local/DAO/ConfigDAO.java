package fi.breakwaterworks.networking.local.DAO;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import fi.breakwaterworks.model.Config;
import io.reactivex.Single;

@Dao
public interface ConfigDAO extends BaseDAO<Config> {
    @Query("SELECT * FROM config")
    List<Config> loadConfigs();

    @Query("SELECT * FROM config Limit 1")
    Single<Config> loadSingleConfig();

    @Query("Update config set token=:token, serverUrl=:url where config_id=:id")
    void updateTokenAndUrl(String token, String url, long id);
}
