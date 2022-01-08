package fi.breakwaterworks.networking.local.usecase;

import android.content.Context;
import android.util.Log;

import java.util.List;

import fi.breakwaterworks.model.Config;
import fi.breakwaterworks.trackthatbarbellmobile.TTBDatabase;
import fi.breakwaterworks.utility.WorkoutGenerator;
import io.reactivex.Observable;
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
                    ttbDatabase.ConfigDAO().updateTokenAndUrl(token,url, configs.get(0).getConfigId());
                }
                if (configs.size() == 0) {
                    ttbDatabase.ConfigDAO().insert(new Config(token, url));
                }
                return "Token and url saved.";
            } catch (Exception ex) {
                Log.e(WorkoutGenerator.class.getName(), "generateWorkLog: ", ex);
            }
            return null;
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
