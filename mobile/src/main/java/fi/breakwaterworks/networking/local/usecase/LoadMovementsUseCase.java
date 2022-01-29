package fi.breakwaterworks.networking.local.usecase;

import android.content.Context;

import java.util.List;

import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.trackthatbarbellmobile.TTBDatabase;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoadMovementsUseCase {
    TTBDatabase ttbDatabase;

    public LoadMovementsUseCase(Context context) {
        ttbDatabase = TTBDatabase.getInstance(context);
    }

    public Single<List<Movement>> LoadAllMovements() {
        return  ttbDatabase.movementDAO().getAllMovements()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<List<Movement>> LoadMovementsWithName(String query) {
        return  ttbDatabase.movementDAO().getMovementsWithNameLike(query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
