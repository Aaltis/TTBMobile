package fi.breakwaterworks.networking.local.usecase;

import android.content.Context;
import android.util.Log;

import androidx.core.util.Consumer;

import java.util.ArrayList;
import java.util.List;

import fi.breakwaterworks.model.Config;
import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.model.WorkLog;
import fi.breakwaterworks.model.Workout;
import fi.breakwaterworks.trackthatbarbellmobile.TTBDatabase;
import fi.breakwaterworks.utility.WorkoutGenerator;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SaveToken {
    TTBDatabase ttbDatabase;

    public SaveToken(Context context) {
        ttbDatabase = TTBDatabase.getInstance(context);
    }

    public Observable<String> saveToken(String token, String url) {
        return Observable.fromCallable(() -> {
            try {
                List<Config> configs = ttbDatabase.ConfigDAO().loadConfigs();
                //We want to use only one config, delete all others.
                if (configs.size() == 1) {
                    ttbDatabase.ConfigDAO().updateToken(token, configs.get(0).getConfigId());
                    return "Token Saved.";
                }
                if (configs.size() == 0) {
                    ttbDatabase.ConfigDAO().insert(new Config(token, url));
                    return "Token Saved.";
                }
                return "fug";
            } catch (Exception ex) {
                Log.e(WorkoutGenerator.class.getName(), "generateWorkLog: ", ex);
            }
            return null;
        }).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());

    }
}
