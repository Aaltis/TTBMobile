package fi.breakwaterworks.networking.local.repository;

import android.content.Context;

import java.util.List;

import fi.breakwaterworks.model.Config;
import fi.breakwaterworks.trackthatbarbellmobile.TTBDatabase;

public class ConfigRepository {
    TTBDatabase database;

    public ConfigRepository(Context context) {
        this.database = TTBDatabase.getInstance(context);
    }

    public List<Config> getTokens() {
        return database.ConfigDAO().loadConfigs();
    }

    public void saveToken(Config config) {
        database.ConfigDAO().insert(config);
    }
}
