package fi.breakwaterworks.networking.local.usecase;

import android.content.Context;

import fi.breakwaterworks.model.Config;
import fi.breakwaterworks.trackthatbarbellmobile.TTBDatabase;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoadConfigUseCase {
    TTBDatabase ttbDatabase;

    public LoadConfigUseCase(Context context) {
        ttbDatabase = TTBDatabase.getInstance(context);
    }

    public Single<Config> Load() {
               return  ttbDatabase.ConfigDAO().loadSingleConfig()
                       .subscribeOn(Schedulers.newThread())
                       .observeOn(AndroidSchedulers.mainThread());
    }
}
