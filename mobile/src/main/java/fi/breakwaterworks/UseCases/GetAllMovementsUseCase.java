package fi.breakwaterworks.UseCases;

import android.content.Context;

import java.util.List;

import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.trackthatbarbellmobile.TTBDatabase;
import fi.breakwaterworks.trackthatbarbellmobile.common.BaseObservable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.functions.Consumer;
public class GetAllMovementsUseCase extends BaseObservable<GetAllMovementsUseCase.Listener> {

    TTBDatabase database;

    public interface Listener {
        void onAllMovementsLoaded(List<Movement> movements);
    }

    public GetAllMovementsUseCase(Context context) {
        this.database = TTBDatabase.getInstance(context);
    }

    public void getMovements() {
        database.movementDAO().getAllMovements()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Movement>>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull List<Movement> movements) {
                for (Listener listener : getListeners()) {
                    listener.onAllMovementsLoaded(movements);
                }
            }
        });
    }
}
